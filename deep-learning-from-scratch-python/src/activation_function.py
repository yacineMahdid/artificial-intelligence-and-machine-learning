"""File containing most activation functions used in DNN.

This file contains most of the popular activation functions used for DNN
which can be used on their own by giving a 'z' value and have each function
return a transformed input.

  Typical usage example:

  activated_z = identity(z) 
"""

import random
import math

def identity(z):
    """
    return the exact input, this is the simplest activation function.

    Args:
        z: output of the neuron before activation.

    Returns:
        the input without any transformation.
    """

    return z

def linear(slope, z):
    """
    Same as identity except we have a scaling by slope and there is no bias.

    Args:
        z: output of the neuron before activation.
        slope: the multiplier with which we want to modify the output.

    Returns:
        the input times the slope.
    """    

    return slope*z

def step(z):
    """
    step function which returns either 0 or 1 depending if the z is positive.

    Args:
        z: output of the neuron before activation.

    Returns:
        0 if z is negative and 1 otherwise.
    """

    if z < 0:
        return 0
    return 1

def piecewise_linear(xmin, xmax, z):
    """
    Same as linear function in the range xmin and xmax, however above and 
    below these threshold it saturate at 0 and 1.

    Args:
        z: output of the neuron before activation.
        xmin: the lower boundary of the saturation zone.
        xmax: the upper boundary of the saturation zone.

    Returns:
        return either a result of a line within the range or either 0 or 1 if at saturation.
    """

    if z < xmin:
        return 0
    
    if z > xmax:
        return 1
    
    slope = 1/(xmax - xmin)
    bias = 1 - slope*xmax
    
    return slope*z + bias

def sigmoid(z):
    """
    This is one of the most widely used activation function. It reshape the values between 0 and 1.

    Args:
        z: output of the neuron before activation.

    Returns:
        return the result of passing z through a sigmoid (between 0 and 1).
    """
    
    return 1 / (1 + math.exp(-z))

def complementary_log_log(z):
    """
    Similarly to the Simoid it reshape the values between 0 and 1.

    Args:
        z: output of the neuron before activation.

    Returns:
        return the result of passing z through a log log (between 0 and 1).
    """

    return 1 - math.exp(-math.exp(z))

def bipolar(z):
    """
    This function is similar than the step except it goes from -1 to 1.

    Args:
        z: output of the neuron before activation.

    Returns:
        -1 if z is negative and 1 otherwise.
    """
    
    if z < 0:
        return -1
    return 1

def bipolar_sigmoid(z):
    """
    This function is the sigmoid, but with a mapping from -1 to 1.

    Args:
        z: output of the neuron before activation.

    Returns:
        return the result of passing z through a remapped sigmoid (between -1 and 1).
    """

    return (1 - math.exp(-z)) / (1 + math.exp(-z))

def tanh(z):
    """
    Classical function that is doing a similar mapping than the bipolar sigmoid.

    Args:
        z: output of the neuron before activation.

    Returns:
        return the result of passing z through a tanh function (between -1 and 1).
    """

    return math.tanh(z)

def lecun_tanh(z):
    """
    This is a modified Tanh function taken from this paper: http://yann.lecun.com/exdb/publis/pdf/lecun-98b.pdf. It is scaled differently than tanh.

    Args:
        z: output of the neuron before activation.

    Returns:
        return the result of passing z through a modified tanh function (slightly above 1 and below -1).
    """

    return (1.7159*math.tanh((2/3)*z))

def hard_tanh(z):
    """
    This is like a piecewise linear with a slope similar to the tanh function.

    Args:
        z: output of the neuron before activation.

    Returns:
        return z passed through a hard piecewise linear with the same slope as tanh.
    """

    return max([-1, min([1, z])])

def absolute(z):
    """
    This takes the absolute value of the input, so there is no negative.

    Args:
        z: output of the neuron before activation.

    Returns:
        return the absolute value of z.
    """

    return abs(z)

def ReLU(z):
    """
    Rectifier Linear Unit is a linear function with a hard threshold at 0, this lead to dead neurons.

    Args:
        z: output of the neuron before activation.

    Returns:
        return 0 or z.
    """

    return max([0, z])

def leaky_ReLU(z):
    """
    This is the same as ReLU, but without a hard threshold at 0. This prevent dead neurons.

    Args:
        z: output of the neuron before activation.

    Returns:
        return a scaled by 0.01 z or exactly z depending if z is positive or negative.
    """

    return max([0.01*z, z])

def parametrised_ReLU(slope, z):
    """
    This is similar to Leaky ReLU, except that the slope at [-inf 0] is not hard coded to 0.01.

    Args:
        z: output of the neuron before activation.
        slope: in leaky relu this is set at 0.01, but this can be changed here.

    Returns:
        return a scaled by slope z or exactly z depending if z is positive or negative.
    """
    
    # Here we need a slope smaller than 1
    assert( slope < 1)
    return max([slope*z, z])

def ELU(slope, z):
    """
    This is a variant of leaky ReLU with a exponential slope except of a linear slope.

    Args:
        z: output of the neuron before activation.
        slope: in leaky relu this is set at 0.01, but this can be changed here.

    Returns:
        return a scaled by exponential slope z or exactly z depending if z is positive or negative.
    """

    if z < 0:
        return slope*(math.exp(z) - 1)
    return z

def smooth_ReLU(z):
    """
    Also called Smooth Max or Soft Plus its yet another variant of ReLU, which doesn't have a linear slope like ReLU.

    Args:
        z: output of the neuron before activation.

    Returns:
        return either 0 for negative z or a smoothed out z from 0 to z.
    """

    return math.log(1 + math.exp(z))

def logit(z):
    """
    This one is very different compared to the other ones and map from -inf to inf only in the range ]0,1[.
    I never seen it being used though.

    Args:
        z: output of the neuron before activation.

    Returns:
        return either anything from -inf to inf in the bound 0 to 1.
    """

    return math.log(z / (1 - z))

def cosine(z):
    """
    Yes this is actually an activation function see this paper: https://people.eecs.berkeley.edu/~brecht/papers/08.rah.rec.nips.pdf.

    Args:
        z: output of the neuron before activation.

    Returns:
        return z passed through a cosine.
    """

    return math.cos(z)

def swish(z):
    """
    This is similar than ReLU but show better performance on deeper models.

    Args:
        z: output of the neuron before activation.

    Returns:
        return z passed through swish.
    """

    return z / (1 - math.exp(-z))