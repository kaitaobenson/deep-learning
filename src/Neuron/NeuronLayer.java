package Neuron;

import Activation.IActivationFunction;
import Activation.LeakyReLu;

import java.io.Serializable;

public class NeuronLayer implements Serializable {

    private final int inputAmount;
    private final Neuron[] neurons;
    private final IActivationFunction activationFunction;

    private float[] inputs;
    private float[] weightedSums;
    private float[] outputs;


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
        weightedSums = new float[neurons.length];

        for (int i = 0; i < neurons.length; i++) {
            float[] outputData = neurons[i].forward(inputs);
            weightedSums[i] = outputData[0]; //Saves the weighted sums
            outputs[i] = outputData[1];
        }

        return outputs;
    }


    // Neuron-based backpropagation for the layer
    public float[] backpropagate(float[] errors, float LEARNING_RATE, NeuronLayer previousLayer) {
        float[] nextErrors = new float[inputs.length];

        if (previousLayer != null) {
            for (int i = 0; i < previousLayer.getNeurons().length; i++) {
                float sum = 0;
                for (int j = 0; j < neurons.length; j++) {
                    sum += errors[j] * getNeurons()[j].getWeights()[i];
                }
                nextErrors[i] = sum * previousLayer.getActivationFunction().outputDerivative(previousLayer.getWeightedSums()[i]);
            }
        }

        for (int i = 0; i < neurons.length; i++){
            neurons[i].backpropagate(errors[i], inputs, LEARNING_RATE);
        }

        return nextErrors;
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
    public float[] getWeightedSums(){
        return weightedSums;
    }
}