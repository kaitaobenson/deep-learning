package Neuron;

import Activation.IActivationFunction;
import java.io.Serializable;

public class NeuronLayer implements Serializable {

    private final int inputAmount;
    private final Neuron[] neurons;
    private final IActivationFunction activationFunction;

    private float[] inputs; //Used for backpropagation
    private float[] outputs;
    private float[] deltas;

    public NeuronLayer(int neuronAmount, int inputAmount, IActivationFunction activationFunction) {
        this.neurons = new Neuron[neuronAmount];
        this.inputAmount = inputAmount;
        this.activationFunction = activationFunction;

        // Create neurons
        for (int i = 0; i < neuronAmount; i++) {
            neurons[i] = new Neuron(inputAmount, activationFunction);
        }
    }

    // Calculate the outputs of all neurons in the layer
    public float[] forward(float[] inputs) {
        this.inputs = inputs;
        outputs = new float[neurons.length];

        for (int i = 0; i < neurons.length; i++) {
            outputs[i] = neurons[i].forward(inputs);
        }

        return outputs;
    }


    // Neuron-based backpropagation for the layer
    public float[] backpropagate(float[] outputErrors, float LEARNING_RATE) {
        float[] inputErrors = new float[inputAmount];

        deltas = new float[neurons.length];

        // Calculate deltas for current layer
        for (int i = 0; i < neurons.length; i++) {
            deltas[i] = outputErrors[i] * activationFunction.outputDerivative(outputs[i]);
        }

        for (int i = 0; i < neurons.length; i++) { // Iterate through each neuron in the current layer
            float[] weights = getNeuron(i).getWeights(); // Get weights for neuron i

            for (int j = 0; j < inputAmount; j++) { // Iterate through each input connection
                inputErrors[j] += weights[j] * deltas[i]; // Accumulate input errors
                weights[j] += LEARNING_RATE * deltas[i] * inputs[j]; // Update weights
            }
        }
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].setBias(neurons[i].getBias() + LEARNING_RATE * deltas[i]);
        }
        return inputErrors; // Propagate errors back to the previous layer
    }


    // Setters / Getters
    public void randomizeWeights() {
        for (Neuron neuron : neurons) {
            neuron.randomizeWeights();
        }
    }
    public void randomizeBiases() {
        for (Neuron neuron : neurons) {
            neuron.randomizeBias();
        }
    }

    public Neuron[] getNeurons() {
        return neurons;
    }
    public Neuron getNeuron(int index) {
        if (index >= neurons.length || index < 0) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        return neurons[index];
    }
    public IActivationFunction getActivationFunction() {
        return activationFunction;
    }
    public float[] getOutputs() {
        return outputs.clone();
    }
}