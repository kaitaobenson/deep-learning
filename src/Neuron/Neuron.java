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

	// Calculates the output value
	public float forward(float[] inputs) {
	    if (weights == null) {
	        throw new IllegalStateException("Weights cannot be null.");
	    }
	    if (inputs == null) {
	        throw new IllegalArgumentException("Inputs cannot be null.");
	    }
	    if (inputs.length != inputAmount) {
	        throw new IllegalArgumentException("Input amount was not correct.");
	    }

	    float weightedSum = 0.0f;

	    for (int i = 0; i < inputs.length; i++) {
	        weightedSum += inputs[i] * weights[i];
	    }
	    
	    weightedSum += bias;

		output = activationFunction.output(weightedSum);
		return output;
	}

	// Updates weights and biases
	// Used for neuron-based backpropagation
	public void backpropagate(float error, float learningRate, float[] inputs) {
		delta = error * activationFunction.outputDerivative(output);

		for (int i = 0; i < weights.length; i++) {
			weights[i] += learningRate * delta * inputs[i];
		}
		bias += learningRate * delta;
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
	public float getDelta() {
		return delta;
	}

	@Override
	public String toString() {
		return "Neuron{" + "bias=" + bias + ", weights=" + Arrays.toString(weights) + '}';
	}
}
