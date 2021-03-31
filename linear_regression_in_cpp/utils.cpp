#include "utils.h"

/****************** DATASET ******************/

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


/****************** WEIGHTS ******************/

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

/****************** MISC ******************/

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
