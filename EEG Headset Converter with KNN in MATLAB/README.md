# EEG Headset Converter with KNN
In this notebook we will define and build a solution to convert one headset with its own label nomenclature into another headset. This is a useful transformation to be able to use to normalize heterogeneous brain data.

## Table of Content
- [Code Structure](#code-structure)
- [Headsets](#headsets)
- [Brain Product Headset](#brain-product-headset)
- [EGI 129 Headset](#egi-129-headset)
- [Plan](#plan)
- [KNN Algorithm](#knn-algorithm)

## Code Structure
Here we have two main script:
- mat_to_csv.m : this one is converting the original data-structure to a csv file
- knn_eeg_headset_converter.m : this is the core experiment code and will be discussed below, it will start with the csv file.

## Headsets
The two headset we will be using to test and assess our solution will be the Brain Product headset with 66 channels and the EGI (Philips health) headset with 129 channels. Below we will look at bird eye view of each of the headset.

### Brain Product Headset
![Brain Product Headset Map](https://github.com/yacineMahdid/artificial-intelligence-from-scratch/blob/master/EEG%20Headset%20Converter%20with%20KNN/.figure/bp_headset.jpg)

### EGI 129 Headset
![EGI 129 Headset Map](https://github.com/yacineMahdid/artificial-intelligence-from-scratch/blob/master/EEG%20Headset%20Converter%20with%20KNN/.figure/egi_headset.jpeg)

The density of electrode is obviously way higher in the EGI-129 headset than in the brain product one. A mapping from one to the other would be possible simply based on the channels labels, if it weren't of the mismatch in the two channels nomenclature.

## Plan
The idea is to take each of the channels location of one of the headset and run the KNN awritetable(result_table,conversion_table_path,'Delimiter',',');
lgorithm on it to find the K nearest neighbor as defined by euclidean distance. This will allow us to find a one-to-one mapping between the two headset. 

To develop the solution we will make use of these two channel location coordinate:
- bp_location.mat
- egi_location.mat

Both are mat files and you guessed right we will develop this whole thing in `MATLAB`.

## KNN Algorithm
![KNN Algorithm](https://github.com/yacineMahdid/artificial-intelligence-from-scratch/blob/master/EEG%20Headset%20Converter%20with%20KNN/.figure/knn_example.png)

The K-Nearest-Neighbors algorithm is an non-parametric machine learning algorithm. It is super simple and in this particular case a tool well suited to do the job. The algorithm goes roughly as follow:

```
Load the data 
init number of neighbors to K

query = an instance
for example in data: 
  calculate distance (euclidean in this case) between example and query
  add the distance and the index to a list (distance, index)

sort the list of tupe (distance, index) from smallest to biggest using the distance
pick the first K element

... do something with this information
```

Here the last step `do something with this information` will depend on if we are doing a regression, a classification or something else. In our case we do not want to do either a regresion or classification, we simply want to get the channel label that match a given query instance. This process will be repeated for as many channels in Brain Product headset there is.

There is a slight twise to our algorithm, we want to be able to say if we want a purely euclidean solution or one that match the label first. It happens that the nearest channel in one headset isn't the one with the same label in the other headset. At that point there is a decision to be taken to choose the euclidean solution or the partially label matching and euclidean distance for the rest

One last caveat is that there could be two query channel that are closest to one of the reference channel. In that case we don't want to map one channel to 2 channel otherwise it will cause lots of trouble in the computation of many brain connectivity metrics. We thereferore always want to double check that the channel we are adding isn't already there.
