package network;

import java.io.Serializable;
import java.util.Arrays;

public class Neuron implements Serializable {

	public float[] weights;
	public float[] cacheWeights;

	public float bias;
	public float cacheBias;

	public float gradient;
	public float value;


	public static Neuron createInputNeuron(float value) {
		Neuron neuron = new Neuron();

		neuron.weights = new float[0]; // Empty array instead of null
		neuron.cacheWeights = new float[0];

		neuron.bias = -1;
		neuron.cacheBias = -1;

		neuron.gradient = -1;
		neuron.value = value;

		return neuron;
	}


	public static Neuron createHiddenNeuron(float[] weights, float bias) {
		Neuron neuron = new Neuron();

		neuron.weights = weights;
		neuron.cacheWeights = Arrays.copyOf(weights, weights.length);
		neuron.bias = bias;
		neuron.cacheBias = bias;

		neuron.gradient = 0;
		neuron.value = 0;

		return neuron;
	}

	public void updateWeights(float learningRate) {
		if (weights != null) { // Skip input neurons
			for (int i = 0; i < weights.length; i++) {
				weights[i] = cacheWeights[i]; // Apply cached updates
			}
		}
		bias = cacheBias;
	}


}
