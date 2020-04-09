% Yacine Mahdid April 08 2020
% This script was made to take the .mat location files and put them into
% a simpler structure for the algorithm.

%% Experiment Variable
in_reference_headset = "data/egi_location.mat";
in_query_headset = "data/bp_location.mat";

out_reference_headset = "data/egi_location.csv";
out_query_headset = "data/bp_location.csv";

% Load our data
data = load(in_reference_headset);
reference_table = data.temp;

data = load(in_query_headset);
query_table = data.temp;

% Iterate throughout the reference table and write it to a csv file
fid = fopen(out_reference_headset, 'w');
fprintf(fid, 'label, x, y, z\n');
for i = 1:length(reference_table)
    row = reference_table(i);
    fprintf(fid, '%s, %f, %f, %f\n', row.labels, row.X, row.Y, row.Z);
end
fprintf(fid,'\n');
fclose(fid);

fid = fopen(out_query_headset, 'w');
fprintf(fid, 'label, x, y, z\n');
for i = 1:length(query_table)
    row = query_table(i);
    fprintf(fid, '%s, %f, %f, %f\n', row.labels, row.X, row.Y, row.Z);
end
fclose(fid);