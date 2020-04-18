% Yacine Mahdid April 08 2020

%% Global Variable
EUCLIDEAN_TYPE = "EUCLIDEAN_TYPE";
LABEL_FIRST_TYPE = "LABEL_FIRST_TYPE";

%% Experiment Variables
% K = 10;
reference_headset = "data/egi_location.csv";
query_headset = "data/bp_location.csv";
conversion_table_path = "bp_to_egi_mapping.csv";
conversion_type = LABEL_FIRST_TYPE;

% load our dataset
original_reference_headset = readtable(reference_headset);
original_query_headset = readtable(query_headset);

% Normalize both headset to have [0,1] range in x,y,z coordinate
% this needs to be done since the query headset has coordinate that are
% not necessarly in the same range as the reference
reference_headset = normalize_headset(original_reference_headset);
reference_coordinate = [reference_headset.x reference_headset.y reference_headset.z];

query_headset = normalize_headset(original_query_headset);
query_coordinate = [query_headset.x query_headset.y query_headset.z];

% Find the K Nearest Neighbors
[nearest_index, distance] = knnsearch(reference_coordinate, query_coordinate,'K',20);

[label_table, distance_table] = create_result_table(reference_headset, query_headset, nearest_index, distance); 

switch conversion_type
    case EUCLIDEAN_TYPE
        conv_query_headset = convert_query_headset_euclidean(query_headset, label_table, distance_table);        
    case LABEL_FIRST_TYPE
        conv_query_headset = convert_query_headset_label_first(query_headset, label_table, distance_table);     
    otherwise
        disp("Conversion Type not supported");
        return;
end

%% Save the conversion in a bp_location, egi_location format
bp_location = query_headset{:,1};
egi_location = conv_query_headset(:,1);
convertion_table = table(bp_location, egi_location);
writetable(convertion_table,conversion_table_path,'Delimiter',',');

%% Plot the Nearest Channel that was Chosen
figure;
subplot(2,1,1)
x = categorical({conv_query_headset{:,1}});
y = [conv_query_headset{:,2}];
bar(x,y)
title('Kth Rank in Distance for Selected Channel');
ylim([0 max(y)+1])
subplot(2,1,2)
x = categorical(unique(y));
y = sum(y(:) == unique(y));
bar(x,y);
title('Number of Nth Rank Channel Choosen for Brain Product Headset');
ylim([0 max(y)+1])

%% Plot EGI electrode positions versus corresponding Brain Products electrodes
figure;
scatter3(original_reference_headset.x,original_reference_headset.y,original_reference_headset.z,'filled','MarkerFaceColor','b')
text(original_reference_headset.x+0.5,original_reference_headset.y+0.5,original_reference_headset.z+0.5,original_reference_headset.label,'Color','b','FontSize',8)
hold on

% Ratio is messed up, 10 is not right for all
original_query_headset.x = original_query_headset.x/10;
original_query_headset.y = original_query_headset.y/10;
original_query_headset.z = original_query_headset.z/10;
scatter3(original_query_headset.x,original_query_headset.y,original_query_headset.z,'filled','MarkerFaceColor','r')
text(original_query_headset.x-0.5,original_query_headset.y-0.5,original_query_headset.z-0.5,original_query_headset.label,'Color','r','FontSize',8)
title('Division by 10');
hold off
legend('EGI','Brain Products')

figure;
scatter3(reference_headset.x, reference_headset.y, reference_headset.z,'filled','MarkerFaceColor','b')
text(reference_headset.x+0.01, reference_headset.y+0.01, reference_headset.z+0.01, reference_headset.label,'Color','b','FontSize',8)
hold on
scatter3(query_headset.x, query_headset.y, query_headset.z,'filled','MarkerFaceColor','r')
text(query_headset.x-0.01, query_headset.y-0.01, query_headset.z-0.01, query_headset.label,'Color','r','FontSize',8)
hold off
legend('EGI','Brain Products')
title('min-max normalization');


function [cleaned_string] = clean_label(label)
% CLEAN LABEL is a simple helper function to make sure we can make proper
% comparison between the label irrespective of the space and capitalization
% they have
    cleaned_string = lower(strtrim(label));
end

function [conv_query_headset] = convert_query_headset_euclidean(query_headset, label_table, distance_table)
% CONVERT QUERY HEADSET EUCLIDEAN will convert the query headset to the
% reference headset in a fifo manner. If the nearest label for a particular
% channel wasn't already choosen it will set it for this channel. If it
% was it will look at the second & third nearest.

    num_row = height(query_headset);
    conv_query_headset = cell(num_row,2);
    
    index = 1;
    while index <= num_row
        [min_row, min_label, min_rank] = find_channel_nearest_distance(label_table, distance_table);
        conv_query_headset{min_row,1} = min_label;
        conv_query_headset{min_row,2} = min_rank;
        
        % Remove this row for consideration
        distance_table{min_row, 2:end} = inf;
        % Remove this label from consideration
        distance_table = find_replace(label_table, distance_table, min_label);
        
        index = index + 1;
        
    end
    
    
    
end

function [conv_query_headset] = convert_query_headset_label_first(query_headset, label_table, distance_table)
% CONVERT QUERY HEADSET LABEL FIRST similarly than the pure euclidean
% version this converter will first assign labels that exactly match and
% then will run the euclidean convertion algorithm
    num_row = height(query_headset);
    num_col = width(label_table)-1;
    
    conv_query_headset = cell(num_row,2);
    % Need first to go through each of the row and check if there is
    % a match in the label name
    for r = 1:num_row
        query_label = clean_label(query_headset.label{r});
        
        conv_query_headset{r,1} = "NaN";
        conv_query_headset{r,2} = -1;
        for c = 1:num_col
           ref_label = clean_label(label_table{r,1+c});
           
           % Only if they match that we set it up
           if(strcmp(query_label, ref_label))
               % We put the unclean string there to preserve the casing
               conv_query_headset(r,1) = label_table{r,1+c};
               conv_query_headset{r,2} = c;
               distance_table = find_replace(label_table, distance_table, ref_label);
               break; 
           end
        end
        
    end

    index = 1;
    while index <= num_row
        [min_row, min_label, min_rank] = find_channel_nearest_distance(label_table, distance_table);
        if strcmp(conv_query_headset{min_row,1},"NaN")
            conv_query_headset{min_row,1} = min_label;
            conv_query_headset{min_row,2} = min_rank; 
        end
        
        % Remove this row for consideration
        distance_table{min_row, 2:end} = inf;
        
        % Remove this label from consideration
        distance_table = find_replace(label_table, distance_table, min_label);
        index = index + 1;
    end
    
    
end

function [norm_headset] = normalize_headset(headset)
% NORMALIZE HEADSET helper function to put the data in each column in the 
% [0,1] range.
    % Normalize the headset using min max normalization fo each coordinate
    norm_headset = headset;
    norm_headset.x = (headset.x - min(headset.x)) / (max(headset.x) - min(headset.x));
    norm_headset.y = (headset.y - min(headset.y)) / (max(headset.y) - min(headset.y));
    norm_headset.z = (headset.z - min(headset.z)) / (max(headset.z) - min(headset.z));
end

function [label_table, distance_table] = create_result_table(reference_headset, query_headset, nearest_index, distance)
% CREATE RESULT TABLE this function take the two headset and the nearest
% index as given by the KNN analysis and will create a table out of it by
% fetching the right index out of the headset

    % Get the right label from each of the Kth index
    [num_row, num_k] = size(nearest_index);
    nearest_label = cell(num_row, num_k);
    nearest_distance = cell(num_row, num_k);
    
    for r = 1:num_row
        for k = 1:num_k
            label = reference_headset{nearest_index(r,k),1};
            label_distance = distance(r,k);
            nearest_label(r,k) = label;
            nearest_distance{r,k} = label_distance;
        end
    end

    % Create the variableNames for each of the columns
    bp_label = query_headset.label;
    var_names = ['bp_label'];
    for c = 1:num_k
        var_names = [var_names strcat('egi_nearest_',string(c))];
    end
    
    % Converting the cell array into a table
    label_table = cell2table([bp_label nearest_label], 'VariableNames',var_names);
    distance_table = cell2table([bp_label nearest_distance], 'VariableNames',var_names);
end


function [min_index, min_label, min_rank] = find_channel_nearest_distance(label_table, distance_table)

    min_distance = inf;
    min_index = inf;
    min_rank = inf;
    for r = 1:height(distance_table)
        [current_distance, current_rank] = min(distance_table{r,2:end});
        if(current_distance < min_distance)
           min_distance = current_distance;
           min_index = r;
           min_rank = current_rank;
           % need an offset by one when fetching in the label table
           % otherwise we will always fetch the original label
           min_label = label_table{r, current_rank + 1}{1};
        end
    end

end

function [distance_table] = find_replace(label_table, distance_table, target_label)
   num_row = height(distance_table);
   num_col = width(distance_table);
   
   disp(strcat("Find and replace: ", target_label));
   target_label = clean_label(target_label);
   for r = 1:num_row
      for c = 2:num_col
          cur_label = clean_label(label_table{r,c}{1});
          
          if strcmp(cur_label, target_label)
            distance_table{r,c} = inf;
          end
          
      end
   end
    

end