from Perceptron import Perceptron
import numpy as np
import pickle

ITERATIONS = 5
MINI_BATCH_SIZE = 1000
LEARNING_RATE = 0.1
FILE_NAME = '/Users/adriangotca/Faculty/Masters/1st year/ACNN/network'


def split_into_mini_batches(training_set):
    mini_batches = list()
    for index in range(0, len(training_set[0]), MINI_BATCH_SIZE):
        mini_batches.append(tuple(zip(training_set[0][index: index + MINI_BATCH_SIZE],
                                      training_set[1][index: index + MINI_BATCH_SIZE])))
    return mini_batches


def shuffle(training_set):
    indexes = list(range(784))
    np.random.shuffle(indexes)
    shuffled_images = np.empty((50000, 784))
    shuffled_labels = np.empty(50000)
    for num, index in enumerate(indexes):
        shuffled_images[num] = training_set[0][index]
        shuffled_labels[num] = training_set[1][index]
    return shuffled_images, shuffled_labels


def deserialize():
    f = open(FILE_NAME, 'rb')
    return pickle.load(f)


class Network:
    def __init__(self):
        self.perceptrons = list()
        for digit in range(10):
            self.perceptrons.append(Perceptron(digit))

    def mini_batch_training(self, training_set):
        for perceptron in self.perceptrons:
            for _ in range(ITERATIONS):
                mini_batches = split_into_mini_batches(shuffle(training_set))
                for mini_batch in mini_batches:
                    weights_delta = np.zeros(784)
                    bias_delta = 0
                    for instance in mini_batch:
                        x = instance[0]
                        z = perceptron.compute_output(x)
                        y = perceptron.activation(z)
                        t = 1 if instance[1] == perceptron.digit else 0
                        weights_delta += (t - y) * x * LEARNING_RATE
                        bias_delta += (t - y) * LEARNING_RATE
                    perceptron.update_weights(weights_delta)
                    perceptron.update_bias(bias_delta)

    def compute_accuracy(self, test_set):
        correctly_classified = 0
        for index in range(len(test_set[0])):
            max_output = float("-inf")
            digit = -1
            for perceptron in self.perceptrons:
                output = perceptron.compute_output(test_set[0][index])
                if output > max_output:
                    max_output = output
                    digit = perceptron.digit
            correctly_classified += 1 if digit == test_set[1][index] else 0
        return correctly_classified / len(test_set[0])

    def serialize(self):
        f = open(FILE_NAME, 'wb')
        pickle.dump(self, f)

