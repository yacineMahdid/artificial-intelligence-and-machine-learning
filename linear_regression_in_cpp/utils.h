#ifndef UTILS_H    
#define UTILS_H

#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <cstdlib>
#include <cstring>
#include <ctime>

class Dataset{
    public:
        float **X;
        float *y;
        int number_predictor;
        int length;

        Dataset();
        Dataset(float **X_train,float *y_train, int length_train, int number_predictor_train);
        void copy(const Dataset &data);
        ~Dataset();
};

class Weights{
    private:
        int MAX_WEIGHTS;

    public:
        float* values;
        int number_weights;

        Weights();
        void init(int number_predictor, int random_init);
        ~Weights();
        void update(Dataset data, float *y_pred, float learning_rate);
};

Dataset read_csv(const char* filename);
float mean(float *y, int length);
float sum_of_square(float *y, int length);
float sum_residual(Dataset data, float *y_pred, int current_predictor);
float residual_sum_of_square(float *y_pred, float *y_true, int length);
int calculate_r2(float *y_pred, float *y_true, int length);
float mean_squared_error(float *y_pred, float *y_true, int length);
float intercept_sum(float *y_pred, float *y_true, int length);
float slope_sum(float *x, float *y_pred, float *y_true, int length);

#endif