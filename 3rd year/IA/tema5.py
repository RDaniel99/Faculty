import numpy as np

class NeuronLayer():
    def __init__(self, number_of_neurons, number_of_inputs_per_neuron):
        self.synaptic_weights = np.random.random((number_of_inputs_per_neuron, number_of_neurons))


class NeuralNetwork():
    def __init__(self, layer1, layer2):
        self.layer1 = layer1
        self.layer2 = layer2

    # The Sigmoid function, which describes an S shaped curve.
    # We pass the weighted sum of the inputs through this function to
    # normalise them between 0 and 1.
    def __sigmoid(self, x):
        return 1 / (1 + np.exp(-x))

    # The derivative of the Sigmoid function.
    # This is the gradient of the Sigmoid curve.
    # It indicates how confident we are about the existing weight.
    def __sigmoid_derivative(self, x):
        return x * (1 - x)

    # We train the neural network through a process of trial and error.
    # Adjusting the synaptic weights each time.
    def train(self, training_set_inputs, training_set_outputs, number_of_training_iterations):
        print(training_set_inputs)
        print(training_set_outputs)
        for iteration in range(number_of_training_iterations):
            # Pass the training set through our neural network
            output_from_layer_1, output_from_layer_2 = self.think(training_set_inputs)

            # Calculate the error for layer 2 (The difference between the desired output
            # and the predicted output).
            layer2_error = training_set_outputs - output_from_layer_2
            layer2_delta = layer2_error * self.__sigmoid_derivative(output_from_layer_2)

            # Calculate the error for layer 1 (By looking at the weights in layer 1,
            # we can determine by how much layer 1 contributed to the error in layer 2).
            layer1_error = layer2_delta.dot(self.layer2.synaptic_weights.T)
            layer1_delta = layer1_error * self.__sigmoid_derivative(output_from_layer_1)

            # Calculate how much to adjust the weights by
            layer1_adjustment = training_set_inputs.T.dot(layer1_delta)
            layer2_adjustment = output_from_layer_1.T.dot(layer2_delta)

            # Adjust the weights.
            self.layer1.synaptic_weights += layer1_adjustment
            self.layer2.synaptic_weights += layer2_adjustment

    # The neural network thinks.
    def think(self, inputs):
        output_from_layer1 = self.__sigmoid(np.dot(inputs, self.layer1.synaptic_weights))
        output_from_layer2 = self.__sigmoid(np.dot(output_from_layer1, self.layer2.synaptic_weights))
        return output_from_layer1, output_from_layer2

    # The neural network prints its weights
    def print_weights(self):
        print("    Layer 1 (4 neurons, each with 3 inputs): ")
        print(self.layer1.synaptic_weights)
        print("    Layer 2 (1 neuron, with 4 inputs):")
        print(self.layer2.synaptic_weights)

if __name__ == "__main__":

    with open("input.txt", "r") as input_file:
        input_text = input_file.read().splitlines()
        inputs = []
        for line in input_text:
            for number in line.split(" "):
                inputs.append(number)
        nr_inputs = int(inputs[0])
        input_size = int(inputs[1])
        output_size = int(inputs[2])
        hidden_layer_size = int(inputs[3])
        index = 4
        inputs_list = []
        outputs_list = []
        for i in range(nr_inputs):
            input = []
            output = []
            for j in range(input_size):
                input.append(int(inputs[index]))
                index += 1
            inputs_list.append(input)
            for j in range(output_size):
                output.append(int(inputs[index]))
                index += 1
            outputs_list.append(output)

    #Seed the random number generator
    np.random.seed(1)

    # Create layer 1 (variable number of neurons, each with 7 inputs)
    layer1 = NeuronLayer(hidden_layer_size, 7)

    # Create layer 2 (10 neurons with 2 inputs)
    layer2 = NeuronLayer(10, hidden_layer_size)

    # Combine the layers to create a neural network
    neural_network = NeuralNetwork(layer1, layer2)

    print("Stage 1) Random starting synaptic weights: ")
    neural_network.print_weights()

    # The training set. We have 7 examples, each consisting of 3 input values
    # and 1 output value.
    training_set_inputs = np.array(inputs_list)
    training_set_outputs = np.array(outputs_list)

    # Train the neural network using the training set.
    # Do it 60,000 times and make small adjustments each time.
    neural_network.train(training_set_inputs, training_set_outputs, 60000)

    print("Stage 2) New synaptic weights after training: ")
    neural_network.print_weights()

    # Test the neural network with a new situation.
    print("Stage 3) Considering a new situation [1, 1, 1, 0, 1, 1, 1] -> ?: ")
    hidden_state, output = neural_network.think(np.array([0, 0, 1, 0, 1, 1, 1]))
    for i in output:
        print("%.10f" % i)