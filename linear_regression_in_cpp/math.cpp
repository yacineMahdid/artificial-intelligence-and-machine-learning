#include "math.h"

// Return the arithmetic mean of an array of variable
float mean(float *data, int length){
    float total = 0;
    for(int i = 0; i < length; i++){
        total = total + data[i];
    }
    return (total/length);
}


// sum up the square of the residual 
float total_sum_of_square(float *y, int length){
     
    float total = 0;
    float residual;
    float y_mean = mean(y,length);

    for(int i = 0 ; i < length; i++){
        residual = (y[i] - y_mean);
        total = total + (residual*residual);
    }
    return total;
}


// sum up the residual of the squared errors
float residual_sum_of_square(float *y_pred, float *y_true, int length){
    float total = 0;
    float residual;

    for(int i = 0 ; i < length; i++){
        residual = (y_true[i] - y_pred[i]);
        total = total + (residual*residual);
    }
    return total;
}

// Coefficient of determination for goodness of fit of the regression
int r2(float *y_pred, float *y_true, int length){
    float sum_squared_residual = residual_sum_of_square(y_pred,y_true,length);
    float sum_squared_total = total_sum_of_square(y_true,length);
    return (1 - ((sum_squared_residual/sum_squared_total)));
}

// wrapper function around residual sum of square in order to have a nicer
// interface to calculate MSE
float mean_squared_error(float *y_pred, float *y_true, int length){
    return residual_sum_of_square(y_pred,y_true,length)/length;
}
