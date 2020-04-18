#include "utils.h"

Weights::Weights(){};

void Weights::init(int number_predictor, int random_init){
    // Random Init Variables
    MAX_WEIGHTS = 100;
    srand(time(0));  // random number generator

    number_weights = number_predictor ;
    values = (float *) std::malloc(sizeof(float)*number_weights);
    for(int i=0; i<number_weights; i++){
        if(random_init == 1){
            values[i] = (rand() % MAX_WEIGHTS);
        }else{
            values[i] = 0;
        }
    }
}

Weights::~Weights(){
}

void Weights::update(Dataset data, float *y_pred, float learning_rate){
    float multiplier = learning_rate/data.length;
    // Update each weights
    for(int i = 0; i < number_weights; i++){
        float sum = (sum_residual(data,y_pred,i));
        printf("Sum = %f\n",sum);
        values[i] = values[i] - multiplier*sum;
    }
}
