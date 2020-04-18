#include "utils.h"


Dataset::Dataset(){

}

Dataset::Dataset(float **X_train,float *y_train, int length_train, int number_predictor_train){
    X = (float **) malloc(sizeof(float*)*length_train);
    for(int i = 0; i < length_train; i++){
        X[i] = (float *) malloc(sizeof(float)*number_predictor_train);
        std::memcpy(X[i], X_train[i], sizeof(float)*number_predictor_train);
    }

    y = (float *) malloc(sizeof(float)*length_train);
    std::memcpy(y, y_train, sizeof(float)*length_train);
    
    length = length_train;
    number_predictor = number_predictor_train;
}

void Dataset::copy(const Dataset &data){
    
    X = (float **) malloc(sizeof(float*)*data.length);
    for(int i = 0; i < data.length; i++){
        X[i] = (float *) malloc(sizeof(float)*data.number_predictor);
        std::memcpy(X[i], data.X[i], sizeof(float)*data.number_predictor);
    }

    y = (float *) malloc(sizeof(float)*data.length);
    std::memcpy(y, data.y, sizeof(float)*data.length);
    
    length = data.length;
    number_predictor = data.number_predictor;
}

Dataset::~Dataset(){
    
}

void Dataset::print_dataset(){
    
    for(int i = 0; i < length; i++){
        printf("row = %d: \n",i);
        for(int j = 0; j < number_predictor; j++){
            printf("X%d = %f\n",j,X[i][j]);
        }
        printf("Y = %f\n",y[i]);
    }

    
}
