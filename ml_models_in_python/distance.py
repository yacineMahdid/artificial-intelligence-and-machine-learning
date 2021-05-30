"""Collection of distance metric used to calculate how far a point in multidimentional space is from another.

This collection contains multiple distance metric that are used to calculate how far a given point is from another.
Each of these metric accept two vector (array) along with sometime parameters that will dictate the behavior of the metric.
Each metric will return a scalar that 

Here is a complete list of the metric:
- Euclidean
- Manhattan
- Chebyshev
- Minkowski
- Mahalanobis

More exist, but these work on realed value vectors.

  Typical usage example:

  dist = distance(X_1, X_2, params)
"""

import math 
import numpy as np

def euclidean_distance(X_1, X_2):
    """Calculate the euclidean distance between two vector.

    Args:
        X_1: A vector of length n.
        X_2: Another vector of length n.

    Returns:
        A distance scalar.
    """

    diff = [math.pow((x_1 - x_2), 2) for x_1, x_2 in zip(X_1, X_2)]
    return math.sqrt(sum(diff))
    
def manhattan_distance(X_1, X_2):
    """Calculate the manhattan distance between two vector.

    Args:
        X_1: A vector of length n.
        X_2: Another vector of length n.

    Returns:
        A distance scalar.
    """

    diff = [abs(x_1 - x_2) for x_1, x_2 in zip(X_1, X_2)]
    return sum(diff)

def chebyshev_distance(X_1, X_2):
    """Calculate the Chebyshev distance between two vector.

    Args:
        X_1: A vector of length n.
        X_2: Another vector of length n.

    Returns:
        A distance scalar.
    """

    diff = [abs(x_1 - x_2) for x_1, x_2 in zip(X_1, X_2)]
    return max(diff)

def minkowski_distance(X_1, X_2, p):
    """Calculate the Minkowski distance between two vector.

    The Minkowski distance can be seen as a generalization of the 
    euclidean and the manhattan distance.

    Args:
        X_1: A vector of length n.
        X_2: Another vector of length n.
        p: order of the distance to be calculated

    Returns:
        A distance scalar.
    """

    diff = [math.pow(abs(x_1 - x_2), p) for x_1, x_2 in zip(X_1, X_2)]
    return sum(diff)*(1/p)

def mahalanobis_distance(X_1, X_2, S):
    """Calculate the Mahalanobis distance between two vector.

    The Mahalanobis distance is best understood using linear algebra notation.
    Here we will be using numpy to not reinvent the dot product.

    Args:
        X_1: A vector of length n coming from a given distribution.
        X_2: Another vector of length n coming from the same distribution as X_1.
        S: Covariance matrix.

    Returns:
        A distance scalar.
    """
    
    delta = X_1 - X_2
    return np.sqrt(np.dot(np.dot(delta, S), delta))