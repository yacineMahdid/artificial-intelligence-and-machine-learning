import timeit
import numpy as np
from numpy.random import permutation

from gradient_descent import gd
from gradient_descent_cython import gd_cython


class Line():
    """
        Linear Model with two weights w0 (intercept) and w1 (slope)
    """

    def __init__(self):
        self.weights = [np.random.uniform(0, 1, 1) for _ in range(2)]
        self.derivative_funcs = [self.dx_w0, self.dx_w1]

    def evaluate(self, x):
        """
            evaluate: will evaluate the line yhate given x
            x: a point in the plane

            return the result of the function evalutation
        """
        return self.weights[0] + self.weights[1] * x

    def derivate(self, x, y):
        """
            derivate: will calculate all partial derivatives and return them
            input:
            x: a point in the plane
            y: the response of the point x

            output:
            partial_derivatives: an array of partial derivatives
        """
        partial_derivatives = []

        yhat = self.evaluate(x)
        partial_derivatives.append(self.dx_w0(x, y, yhat))
        partial_derivatives.append(self.dx_w1(x, y, yhat))

        return partial_derivatives

    def dx_w0(self, x, y, yhat):
        """
            dx_w0: partial derivative of the weight w0
            x: a point in the plane
            y: the response of the point x
            yhat: the current approximation of y given x and the weights

            return the gradient at that point for this x and y for w0
        """
        return 2 * (yhat - y)

    def dx_w1(self, x, y, yhat):
        """
            dx_w1: partial derivative of the weight w1 for a linear function
            x: a point in the plane
            y: the response of the point x
            yhat: the current approximation of y given x and the weights

            return the gradient at that point for this x and y for w1
        """
        return 2 * x * (yhat - y)

    def __str__(self):
        return f"y = {self.weights[0]} + {self.weights[1]}*x"


xs = [1, 2, 3, 4, 5, 6, 7]
ys = [1, 2, 3, 4, 5, 6, 7]

model = Line()
gd_py = timeit.Timer(lambda: gd(model, xs, ys))
gd_cy = timeit.Timer(lambda: gd_cython(model, xs, ys))

cy = gd_cy.timeit(10)
py = gd_py.timeit(10)


print(cy, py)
print('Cython is {}x faster'.format(py/cy))