public class Neuron {
   
	// Static variables
	static float minWeightValue;
	static float maxWeightValue;
	
	// Non-Static Variables
    float[] weights;
    float[] cache_weights;
    float gradient;
    float bias;
    float value = 0;
    
    
   // Constructor for the hidden / output neurons
    public Neuron(float[] weights, float bias){
        this.weights = weights;
        this.bias = bias;
        this.cache_weights = this.weights;
        this.gradient = 0;
    }
    
    // Constructor for the input neurons
    public Neuron(float value){
        this.weights = null;
        this.bias = -1;
        this.cache_weights = this.weights;
        this.gradient = -1;
        this.value = value;
    }
    
    // Static function to set min and max weight for all variables
    public static void setRangeWeight(float min,float max) {
    	minWeightValue = min;
    	maxWeightValue = max;
    }
    
    // Function used at the end of the backprop to switch the calculated value in the
    // cache weight in the weights
    public void update_weight() {
    	this.weights = this.cache_weights;
    }
    
 
}