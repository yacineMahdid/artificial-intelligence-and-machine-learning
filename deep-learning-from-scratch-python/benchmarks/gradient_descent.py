
def gd(model, xs, ys, learning_rate=0.01, max_num_iteration=10000):
    """
        gd: will estimate the parameters w1 and w2 (here it uses least square cost function)
        model: the model we are trying to optimize using gradient descent
        xs: all point on the plane
        ys: all response on the plane
        learning_rate: the learning rate for the step that weights update will take
        max_num_iteration: the number of iteration before we stop updating
    """

    def gradient(dx, evaluate, xs, ys):
        """
            gradient: estimate mean gradient over all point for w1
            evaluate: the evaulation function from the model
            dx: partial derivative function used to evaluate the gradient
            xs: all point on the plane
            ys: all response on the plane

            return the mean gradient all x and y for w1
        """
        N = len(ys)

        total = 0
        for x, y in zip(xs, ys):
            yhat = evaluate(x)
            total = total + dx(x, y, yhat)

        gradient = total / N
        return gradient



    for i in range(max_num_iteration):
        # Updating the model parameters
        model.weights[0] = model.weights[0] - learning_rate*gradient(model.derivative_funcs[0], model.evaluate, xs, ys)
        model.weights[1] = model.weights[1] - learning_rate*gradient(model.derivative_funcs[1], model.evaluate, xs, ys)


