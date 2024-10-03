package Neuron;

import Activation.ActivationFunctionType;

import java.io.Serializable;

public class NeuronLayer implements Serializable {

    private Neuron[] neurons;

    private ActivationFunctionType activationFunctionType;

    public NeuronLayer(int neuronAmount, int inputAmount, ActivationFunctionType activationFunctionType) {
        neurons = new Neuron[neuronAmount];

        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new Neuron(this);

            neurons[i].setWeights(new float[inputAmount]);
        }

        this.activationFunctionType = activationFunctionType;
    }

    public float[] calculateOutputs(float[] inputs) {
        float[] outputs = new float[neurons.length];

        // Get the output from each neuron, add it to the list
        for (int i = 0; i < neurons.length; i++) {
            outputs[i] = neurons[i].calculateOutput(inputs);
        }

        return outputs;
    }


    public Neuron[] getNeurons() {
        return neurons;
    }
    public Neuron getNeuron(int index) {
        return neurons[index];
    }
    public ActivationFunctionType getActivationFunctionType() {
        return activationFunctionType;
    }

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
}
