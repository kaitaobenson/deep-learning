package Neuron;

import Activation.IActivationFunction;
import Util.GeneralUtil;
import java.io.Serializable;
import java.util.Arrays;

public class Neuron implements Serializable {
	
	private static final int MAX_STARTING_BIAS = 1;
	private static final int MIN_STARTING_BIAS = -1;
	
	private static final int MAX_STARTING_WEIGHT = 1;
	private static final int MIN_STARTING_WEIGHT = -1;

	private final int inputAmount;
	private final IActivationFunction activationFunction;

	private float[] weights;
	private float bias;

	private float output;
	float weightedSum;
	private float delta;

	public Neuron(int inputAmount, IActivationFunction activationFunction) {
		this.inputAmount = inputAmount;
		this.activationFunction = activationFunction;
		this.weights = new float[inputAmount];

		randomizeWeights();
		randomizeBias();
	}

	public Neuron(int inputAmount, IActivationFunction activationFunction, float[] weights, float bias) {
		this.inputAmount = inputAmount;
		this.activationFunction = activationFunction;
		this.weights = weights;
		this.bias = bias;
	}

	// Calculates the output value and returns the output and weighted sum
	public float[] forward(float[] inputs) {
	    if (weights == null) {
	        throw new IllegalStateException("Weights cannot be null.");
	    }
	    if (inputs == null) {
	        throw new IllegalArgumentException("Inputs cannot be null.");
	    }
	    if (inputs.length != inputAmount) {
	        throw new IllegalArgumentException("Input amount was not correct.");
	    }

		float[] outputs = new float[2]; // Since both the weightedSum and the output is needed, I'm just going to have it return a float array cus i don't know a better way to do it

		weightedSum = 0.0f;

	    for (int i = 0; i < inputs.length; i++) {
	        weightedSum += inputs[i] * weights[i];
	    }
	    
	    weightedSum += bias;

		outputs[0] = weightedSum; // First element is weighted sum

		output = activationFunction.output(weightedSum);

		outputs[1] = output; // Second element is output

		return outputs;
	}

	// Updates weights and biases
	public void backpropagate(float error, float[] inputs, float LEARNING_RATE) {
		for (int i = 0; i < inputs.length; i++){
			float weightGradient = error * inputs[i];

			weights[i] -= weightGradient * LEARNING_RATE;
		}

		float biasGradient = error;
		bias += biasGradient * LEARNING_RATE;
	}

	// Setters / Getters
	public void setWeights(float[] weights) {
		this.weights = weights.clone();
	}
	public float[] getWeights() {
		return weights.clone();
	}
	public void randomizeWeights() {
		for (int i = 0; i < weights.length; i++) {
			weights[i] = GeneralUtil.randomFloat(MIN_STARTING_WEIGHT, MAX_STARTING_WEIGHT);
		}
	}

	public void setBias(float bias) {
		this.bias = bias;
	}
	public float getBias() {
		return bias;
	}
	public void randomizeBias() {
		bias = GeneralUtil.randomFloat(MIN_STARTING_BIAS, MAX_STARTING_BIAS);
	}

	public int getInputAmount() {
		return inputAmount;
	}
	public IActivationFunction getActivationFunction() {
		return activationFunction;
	}
	public float getOutput() {
		return output;
	}
	public float getWeightedSum() {
		return weightedSum;
	}
	public float getDelta() {
		return delta;
	}

	@Override
	public String toString() {
		return "Neuron{" + "bias=" + bias + ", weights=" + Arrays.toString(weights) + '}';
	}
}
