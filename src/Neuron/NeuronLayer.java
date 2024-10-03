package Neuron;

import Activation.ActivationFunctionType;
import java.io.Serializable;

public class NeuronLayer implements Serializable {

    private final Neuron[] neurons;
    private final ActivationFunctionType activationFunctionType;

    public NeuronLayer(int neuronAmount, int inputAmount, ActivationFunctionType activationFunctionType) {
        this.neurons = new Neuron[neuronAmount];
        this.activationFunctionType = activationFunctionType;

        // Initialize neurons and set weights
        for (int i = 0; i < neuronAmount; i++) {
            neurons[i] = new Neuron(this);
            neurons[i].setWeights(new float[inputAmount]);
        }
    }

    // Calculate the outputs of all neurons in the layer
    public float[] calculateOutputs(float[] inputs) {
        float[] outputs = new float[neurons.length];
        for (int i = 0; i < neurons.length; i++) {
            outputs[i] = neurons[i].calculateOutput(inputs);
        }
        return outputs;
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
    public ActivationFunctionType getActivationFunctionType() {
        return activationFunctionType;
    }
}