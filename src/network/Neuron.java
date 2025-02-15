package layer;

import java.io.Serializable;

public class Neuron implements Serializable {

	public int layer;
	public int layerIndex;
	public float[] weights;
	public float[] cacheWeights;
	public float gradient;
	public float bias;
	public float value;


	public static Neuron createInputNeuron(float value, int layerIndex) {
		Neuron neuron = new Neuron();

		neuron.weights = null;
		neuron.cacheWeights = null;

		neuron.bias = -1;
		neuron.gradient = -1;
		neuron.value = value;

		neuron.layer = 0;
		neuron.layerIndex = layerIndex;

		return neuron;
	}

	public static Neuron createHiddenNeuron(float[] weights, float bias, int layer, int layerIndex) {
		Neuron neuron = new Neuron();

		neuron.weights = weights;
		neuron.cacheWeights = weights;
		neuron.bias = bias;

		neuron.gradient = 0;
		neuron.value = 0;

		neuron.layer = layer;
		neuron.layerIndex = layerIndex;

		return neuron;
	}

	// Used at the end of backpropagation
	public void update_weights() {
		this.weights = this.cacheWeights;
	}
}
