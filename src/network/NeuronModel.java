package model;

import data.DataSample;
import layer.Neuron;
import layer.NeuronLayer;

import java.io.Serializable;

public class NeuronModel implements Serializable {

    public static float learningRate = 1.0f;

    private final NeuronLayer[] layers;


    public NeuronModel(NeuronLayer[] neuronLayers) {
        this.layers = neuronLayers;
    }

    public void forward(float[] inputs) {
        // Put inputs in first layer
        layers[0] = new NeuronLayer(inputs);

        // Loop layers
        for (int i = 1; i < layers.length; i++) {

            NeuronLayer prevLayer = layers[i - 1];
            NeuronLayer currentLayer = layers[i];

            // Loop neurons in layer
            for (Neuron neuron : currentLayer.neurons) {
                float sum = neuron.bias;

                // Loop neurons in previous layer
                for (int j = 0; j < prevLayer.neurons.length; j++) {
                    sum += prevLayer.neurons[j].value * neuron.weights[j];
                }

                neuron.value = currentLayer.activationFunction.output(sum);
            }
        }
    }

    public void backward(DataSample data) {
        int numLayers = layers.length;
        int outIndex = layers.length - 1;

        // Update output layer gradients
        for (Neuron neuron : layers[outIndex].neurons) {
            neuron.gradient = computeOutputGradient(neuron, data.expectedOutput);
        }

        // Update hidden layers
        for (int i = outIndex - 1; i > 0; i--) {
            for (Neuron neuron : layers[i].neurons) {
                neuron.gradient = computeHiddenGradient(neuron, i + 1);
            }
        }

        // Update all weights
        updateAllWeights();
    }



    private float sumGradient(int n_index,int l_index) {
        float gradient_sum = 0;
        NeuronLayer current_layer = layers[l_index];

        for (Neuron neuron : current_layer.neurons) {
            gradient_sum += neuron.weights[n_index] * neuron.gradient;
        }
        return gradient_sum;
    }

    private float computeOutputGradient(Neuron neuron, float[] expectedOutput) {
        float output = neuron.value;
        float target = expectedOutput[neuron.index];
        float derivative = output - target;
        return derivative * (output * (1 - output)); // Sigmoid derivative
    }

    private float computeHiddenGradient(Neuron neuron, int nextLayerIndex) {
        float output = neuron.value;
        float gradientSum = sumGradient(neuron.index, nextLayerIndex);
        return gradientSum * (output * (1 - output)); // Sigmoid derivative
    }

    private void updateAllWeights() {
        for (Layer layer : layers) {
            for (Neuron neuron : layer.neurons) {
                neuron.update_weight();
            }
        }
    }


}