package network;

import activation.IActivationFunction;

import java.io.Serializable;
import java.util.Random;

public class NeuronLayer implements Serializable {

    public IActivationFunction activationFunction;
    public Neuron[] neurons;

    public int inputAmount = 0;

    public static NeuronLayer createInputLayer(float[] inputs) {
        NeuronLayer neuronLayer = new NeuronLayer();

        neuronLayer.activationFunction = null; // Not needed
        neuronLayer.inputAmount = inputs.length;
        neuronLayer.neurons = new Neuron[inputs.length];

        for (int i = 0; i < inputs.length; i++) {
            neuronLayer.neurons[i] = Neuron.createInputNeuron(inputs[i]);
        }

        return neuronLayer;
    }

    public static NeuronLayer createHiddenLayer(int inputAmount, int neuronAmount, IActivationFunction activationFunction) {
        NeuronLayer neuronLayer = new NeuronLayer();

        neuronLayer.activationFunction = activationFunction;
        neuronLayer.inputAmount = inputAmount;
        neuronLayer.neurons = new Neuron[neuronAmount];

        for (int i = 0; i < neuronAmount; i++) {
            float[] weights = getHeInitWeights(inputAmount);
            float bias = 0.0f;

            neuronLayer.neurons[i] = Neuron.createHiddenNeuron(weights, bias);
        }

        return neuronLayer;
    }

    public void randomize() {
        for (Neuron neuron : neurons) {
            neuron.weights = getHeInitWeights(inputAmount);
            neuron.weightDeltas = new float[inputAmount];
            neuron.bias = 0;
            neuron.biasDelta = 0;
        }
    }


    // Return initialized neuron with HE initialization
    private static float[] getHeInitWeights(int inputAmount) {
        float[] weights = new float[inputAmount];

        for (int i = 0; i < inputAmount; i++) {
            Random rand = new Random();
            weights[i] = (float) (rand.nextGaussian() * Math.sqrt(2.0 / inputAmount));
        }

        return weights;
    }
}
