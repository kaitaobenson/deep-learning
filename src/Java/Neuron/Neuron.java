package Java.Neuron;

import Java.Activation.IActivationFunction;
import Java.Util.Math.RandomGenerator;

// Stores weights and a bias, takes an input and calculates an output.

public class Neuron {
	
	private static final int MAX_STARTING_BIAS = 1;
	private static final int MIN_STARTING_BIAS = -1;
	
	private static final int MAX_STARTING_WEIGHT = 1;
	private static final int MIN_STARTING_WEIGHT = -1;

	private float[] weights;
	private float bias;

	private NeuronLayer neuronLayer;


	public Neuron(NeuronLayer neuronLayer) {
		this.neuronLayer = neuronLayer;
	}

	
	public float calculateOutput(float[] inputs) {
	    if (weights == null) {
	        throw new IllegalStateException("Weights must be initialized before calculating output.");
	    }
	    if (inputs == null) {
	        throw new IllegalArgumentException("Inputs cannot be null.");
	    }
	    if (inputs.length != weights.length) {
	        throw new IllegalArgumentException("Input array length must be the same as weights length.");
	    }

	    float weightedSum = (float) 0.0;

	    for (int i = 0; i < inputs.length; i++) {
	        weightedSum += inputs[i] * weights[i];
	    }
	    
	    weightedSum += bias;

		IActivationFunction activationFunction = neuronLayer.getActivationFunctionType().getActivationFunction();
		return activationFunction.output(weightedSum);
	}


	// Weights setters / getters
	
	public void setWeights(float[] weights) {
		this.weights = weights.clone();
	}
	
	public float[] getWeights() {
		return weights.clone();
	}

	public void setWeight(float weight, int index) {
		weights[index] = weight;
	}
	
	public void randomizeWeights() {
	    for (int i = 0; i < weights.length; i++) {
			weights[i] = RandomGenerator.randomFloat(MIN_STARTING_WEIGHT, MAX_STARTING_WEIGHT);
		}
	}
	
	// Bias setters / getters
	
	public void setBias(float bias) {
		this.bias = bias;
	}

	public float getBias() {
		return bias;
	}

	public void randomizeBias() {
	    bias = RandomGenerator.randomFloat(MIN_STARTING_BIAS, MAX_STARTING_BIAS);
	}
}
