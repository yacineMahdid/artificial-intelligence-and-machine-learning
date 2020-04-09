% Yacine Mahdid April 08 2020
% This script was adapted fom the work of Danielle Nadin at the BIAPT Lab

%% Experiment Variables
K = 3;
reference_headset = "data/egi_location.csv";
query_headset = "data/bp_location.csv";

% load our dataset
reference_headset = readtable(reference_headset);
query_headset = readtable(query_headset);

% Normalize both headset to have [0,1] range in x,y,z coordinate
% this needs to be done since the query headset has coordinate that are
% not necessarly in the same range as the reference
reference_headset = normalize_headset(reference_headset);
reference_coordinate = [reference_headset.x reference_headset.y reference_headset.z];

query_headset = normalize_headset(query_headset);
query_coordinate = [query_headset.x query_headset.y query_headset.z];

% Find the K Nearest Neighbors
[nearest_index, distance] = knnsearch(reference_coordinate, query_coordinate,'K',K);

% TODO: Need to create the table to be able to assess how well the mapping
% is
% TODO: Need to modify the labels to match the best mapping
% TODO: Need to add the option to ignore euclidean distance mapping for
% exact match channel


%{
%% Print table of results
table(BP_labels,EGI_labels(Idx(:,1),:),EGI_labels(Idx(:,2),:),EGI_labels(Idx(:,3),:),...
    'VariableNames',{'Brain_Products','EGI_1st','EGI_2nd','EGI_3rd'})
 
%% Plot EGI electrode positions versus corresponding Brain Products electrodes
figure(1)
clf
scatter3(EGI_positions(:,1),EGI_positions(:,2),EGI_positions(:,3),'filled','MarkerFaceColor','b')
text(EGI_positions(:,1)+0.5,EGI_positions(:,2)+0.5,EGI_positions(:,3)+0.5,EGI_labels,'Color','b','FontSize',8)
hold on
scatter3(BP_positions(:,1),BP_positions(:,2),BP_positions(:,3),'filled','MarkerFaceColor','r')
text(BP_positions(:,1)-0.5,BP_positions(:,2)-0.5,BP_positions(:,3)-0.5,BP_labels,'Color','r','FontSize',8)
hold off
legend('EGI','Brain Products')
%}

function [norm_headset] = normalize_headset(headset)
% NORMALIZE HEADSET helper function to put the data in each column in the 
% [0,1] range.
    % Normalize the headset using min max normalization
    norm_headset = headset;
    norm_headset.x = (headset.x - min(headset.x)) / (max(headset.x) - min(headset.x));
    norm_headset.y = (headset.y - min(headset.y)) / (max(headset.y) - min(headset.y));
    norm_headset.z = (headset.z - min(headset.z)) / (max(headset.z) - min(headset.z));
end