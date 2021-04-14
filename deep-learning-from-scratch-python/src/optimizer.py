"""File containing Gradient Descent based optimizer

The files contains most of the optimizer that I've implemented from scratch without
a linear algebra library.

Typical example:

optimizer = SGD(model.weights, model.gradients, optimizer_parameters...)
[...]
optimizer.zero_grad() # this will remove all the gradients accumulated
model.backward() # since it already has access to the graph and to the gradients.
optimizer.step() # do one gradient descent step
"""

class _Optimizer():
    """Private Optimizer class with boiler plate functionality.

    Attributes:
        weights (array): The weights of the model.
        grads (array): The gradients for each weights attached to the model.

    """
    def __init__(self, weights, grads):
        self.weights = weights # holds reference to model weights
        self.grads = grads # holds reference to model gradients
    
    def zero_grad(self):

        for grad in self.grads:
            grad = 0

    def step(self):
        pass

class GD(_Optimizer):
    """Gradient Descent optimizer (this can be SGD or Batch GD depending on how gradient are calculated).

    Attributes:
        weights (array): The weights of the model.
        grads (array): The gradients for each weights attached to the model.
        learning_rate (float): This define how big the steps are.

    """
    def __init__(self, model_weights, model_gradients, learning_rate = 0.01)
        super.__init__(self, model_weights, model_gradient)
        self.learning_rate = learning_rate

    def step(self):
    """stepper function to update the weights based on the gradients and the learning rate."""
        for weight, grad in zip(self.weights, self.grads):
            weight -= self.learning_rate*grad

class GDMomentum(_Optimizer):

    # TODO
    def __init__(self):
        super.__init__(self)

    # TODO
    def step(self):
        pass

class Adagrad(_Optimizer):

    # TODO
    def __init__(self):
        super.__init__(self)

    # TODO
    def step(self):
        pass

class RMSProp(_Optimizer):

    # TODO
    def __init__(self):
        super.__init__(self)

    # TODO
    def step(self):
        pass

class Adadelta(_Optimizer):

    # TODO
    def __init__(self):
        super.__init__(self)

    # TODO
    def step(self):
        pass

class Adam(_Optimizer):

    # TODO
    def __init__(self):
        super.__init__(self)

    # TODO
    def step(self):
        pass

class Nesterov(_Optimizer):

    # TODO
    def __init__(self):
        super.__init__(self)

    # TODO
    def step(self):
        pass

class Adamax(_Optimizer):

    # TODO
    def __init__(self):
        super.__init__(self)

    # TODO
    def step(self):
        pass

class Nadam(_Optimizer):

    # TODO
    def __init__(self):
        super.__init__(self)

    # TODO
    def step(self):
        pass