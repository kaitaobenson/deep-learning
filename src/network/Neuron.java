package network;

import java.io.Serializable;

public class Neuron implements Serializable {

	public float[] weights;
	public float[] weightDeltas;

	public float bias;
	public float biasDelta;

	public float gradient;
	public float value;


	public static Neuron createInputNeuron(float value) {
		Neuron neuron = new Neuron();

		neuron.weights = new float[0];
		neuron.weightDeltas = new float[0];

		neuron.bias = -1;
		neuron.biasDelta = -1;

		neuron.gradient = -1;
		neuron.value = value;

		return neuron;
	}

	public static Neuron createHiddenNeuron(float[] weights, float bias) {
		Neuron neuron = new Neuron();

		neuron.weights = weights;
		neuron.weightDeltas = new float[weights.length];
		neuron.bias = bias;
		neuron.biasDelta = 0;

		neuron.gradient = 0;
		neuron.value = 0;

		return neuron;
	}

	public void updateWeights(int batchSize) {
		for (int i = 0; i < weights.length; i++) {
			weights[i] += weightDeltas[i] / batchSize;
		}
		bias += biasDelta / batchSize;

		weightDeltas = new float[weights.length];
		biasDelta = 0;
	}
}
