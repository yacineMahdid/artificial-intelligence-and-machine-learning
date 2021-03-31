# Linear Regression in C++
The formula we want to optimize is the following one: `y = a_0 + a_1*x` where a_0 is the bias and a_1 the slope of the line.

We want to find the best a_0 and a_1 for the dataset we have.

## Mean Squared Error
In order to find this a_0 and a_1 we need to have a cost function (or [loss function](https://en.wikipedia.org/wiki/Loss_function)) that we are trying to minimize.

The one we will be using is the [mean squared error](https://en.wikipedia.org/wiki/Mean_squared_error), which is defined as follow:
`MSE = 1/n * (SUM(y_predi - y1)^2 from i = 1 to n)`

MSE is just one of the many loss function we can choose.


## Gradient Descent
In order to optimize the loss function (in this case minimize it) we need to use an algorithm to change the parameters iteratively until we find a global (or local) minima.

[Gradient descent](https://ruder.io/optimizing-gradient-descent/) is well suited for this.

We will iterateively change the a_0 and a_1 values in order to move in the loss/cost plane toward a global minima. Check out my [video on gradient descent](https://youtu.be/IH9kqpMORLM) for a step by step walkthrough.

In order to use gradient descent we need to have gradient in the multidimensional plane, which are simply partial derivative with respect to each of the variable. In our case it's a_0 and a_1. 

These can be easily found via straight calculus, but there are some other more advanced technique to figure out how to calculate them in arbitrarily complex functions.

A learning rate need to be choosen as an hyper parameter. Big learning rate means by jumps, but might diverge. A small learning rate means small jumps, but it will take longer to converge. 

The update goes like this:
```
a = learning_rate

# calculate the new weights value
new_a0 = a0 - (2a/n) * (Sum(y_predi - yi) from i = 1 to n)
new_a1 = a1 - (2a/n) (Sum(y_predi - yi)*xi) from i = 1 to n)

# do the update
a0 = new_a0
a1 = new_a1
```

We iterate until we hit the maximum amount of steps or if the performance isn't improving.

## R^2 Score
In order to figure out if our line is fitting properly we need some measure of fitness. In the regression case a [R2 score](https://en.wikipedia.org/wiki/Coefficient_of_determination)

The definition is 
> the proportion of the variance in the dependent variable that is predictable from the independent variable(s)

## Ressources
- [Main tutorial I've followed](https://towardsdatascience.com/introduction-to-machine-learning-algorithms-linear-regression-14c4e325882a)