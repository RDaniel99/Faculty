import numpy as np


class Perceptron:
    def __init__(self, digit):
        self.weights = np.random.uniform(0, 1, 784)
        self.bias = np.random.uniform(0, 1)
        self.digit = digit

    def compute_output(self, inputs):
        return np.dot(self.weights, inputs) + self.bias

    def activation(self, result):
        if result > 0:
            return 1
        return 0

    def update_weights(self, delta_array):
        self.weights += delta_array

    def update_bias(self, delta):
        self.bias += delta
