import pickle
import gzip
from Network import FILE_NAME, Network


def get_data():
    f = gzip.open('/Users/adriangotca/Faculty/Masters/1st year/ACNN/mnist.pkl.gz', 'rb')
    return pickle.load(f, encoding='latin1')


def deserialize():
    f = open(FILE_NAME, 'rb')
    return pickle.load(f)


if __name__ == '__main__':
    training_set, validation_set, testing_set = get_data()
    # network = Network()
    # network.mini_batch_training(training_set)
    # network.serialize()
    network = deserialize()
    print(network.compute_accuracy(testing_set))
