#ifndef MATH_H    
#define MATH_H

#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <cstdlib>
#include <cstring>
#include <ctime>

float mean(float *y, int length);
float total_sum_of_square(float *y, int length);
float residual_sum_of_square(float *y_pred, float *y_true, int length);
int r2(float *y_pred, float *y_true, int length);
float mean_squared_error(float *y_pred, float *y_true, int length);

#endif