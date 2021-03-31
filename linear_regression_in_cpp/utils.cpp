#include "utils.h"


// Misc Helper function 
Dataset read_csv(const char* filename){
    // Variable Initialization
    float **X;
    float *y;
    int index = 0;
    int length = 0;
    int number_predictor = 0;

    // Reading File to get the number of x and y data points
    std::ifstream infile(filename);
    std::string line;
    while (std::getline(infile, line)){
        length++;
        // Calculate the number of predictors
        if(length == 1){
            int i = 0;
            while(line[i] != '\0'){
                if(line[i] == ','){
                    number_predictor++;
                }
                i++;
            }
        }
    }
    infile.close();

    // Mallocating space for X and y
    X = (float **) malloc(sizeof(float*)*length);
    for(int i = 0; i < length; i++){
        X[i] = (float *) malloc(sizeof(float)*number_predictor);
    }
    y = (float *) malloc(sizeof(float)*length);

    // Rereading the file to extract x and y values
    char comma;
    std::ifstream samefile(filename);
    int current_index = 0;
    while(std::getline(samefile,line)){

        std::stringstream line_stream(line);
        int current_predictor = 0;
        float number;
        while (line_stream >> number)
        {
            if(current_predictor == number_predictor){
                y[current_index] = number;
            }
            else{
                X[current_index][current_predictor] = number;
                current_predictor++;
            }

            if(line_stream.peek() == ','){
                line_stream.ignore();
            }

        }
        current_index++;
    }
    samefile.close();

    Dataset data = Dataset(X,y,length,number_predictor);
    return data;
}

int make_csv(const char* filename, float* weights, int number_weights, int number_simulation){

}

// Stats Helper function
float mean(float *y, int length){
    float total = 0;
    for(int i = 0; i < length; i++){
        total = total + y[i];
    }
    return (total/length);
}

float sum_residual(Dataset data, float *y_pred, int current_predictor){
    float total = 0;
    float residual;
    for(int i = 0 ; i < data.length; i++){
        residual = (y_pred[i] - data.y[i]);
        total = total + residual*data.X[i][current_predictor];
    }
    return total;
}

float sum_of_square(float *y, int length){
    // Not the most efficient way of calculating variance, see : https://www.sciencebuddies.org/science-fair-projects/science-fair/variance-and-standard-deviation 
    float total = 0;
    float residual;
    float y_mean = mean(y,length);
    for(int i = 0 ; i < length; i++){
        residual = (y[i] - y_mean);
        total = total + (residual*residual);
    }
    return total;
}

float residual_sum_of_square(float *y_pred, float *y_true, int length){
    float total = 0;
    float residual;
    for(int i = 0 ; i < length; i++){
        residual = (y_true[i] - y_pred[i]);
        total = total + (residual*residual);
    }
    return total;
}

int calculate_r2(float *y_pred, float *y_true, int length){
    // Taken from: https://en.wikipedia.org/wiki/Coefficient_of_determination
    float sum_squared_residual = residual_sum_of_square(y_pred,y_true,length);
    float sum_squared_total = sum_of_square(y_true,length);
    return (1 - ((sum_squared_residual/sum_squared_total)));
}

float mean_squared_error(float *y_pred, float *y_true, int length){
    return residual_sum_of_square(y_pred,y_true,length)/length;
}

float intercept_sum(float *y_pred, float *y_true, int length){
    float total = 0;
    float residual;
    for(int i = 0 ; i < length; i++){
        residual = (y_pred[i] - y_true[i]);
        total = total + residual;
    }
    return total;
}

float slope_sum(float *x, float *y_pred, float *y_true, int length){
    float total = 0;
    float residual;
    for(int i = 0 ; i < length; i++){
        residual = (y_pred[i] - y_true[i]);
        total = total + residual*x[i];
    }
    return total;
}
