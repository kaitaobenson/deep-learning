package Java.Neuron;

import Java.Main.Util;
import Java.Math.Vector2;

import java.util.Random;
import java.util.UUID;

// Stores weights and a bias, and computes an output from 0-1.

public class Neuron {
	
	private static final int MAX_STARTING_BIAS = 1;
	private static final int MIN_STARTING_BIAS = -1;
	
	private static final int MAX_STARTING_WEIGHT = 1;
	private static final int MIN_STARTING_WEIGHT = -1;
	
	private float[] weights;
	private float bias;

	private Vector2 position;

	private final UUID id = UUID.randomUUID();

	
	// Compute stuff

	public Neuron(int columnX, int rowY) {
		position = new Vector2(columnX, rowY);
	}

	
	public float computeOutput(float[] inputs) {
	    if (weights == null) {
	        throw new IllegalStateException("Weights must be initialized before computing output.");
	    }
	    if (inputs == null) {
	        throw new IllegalArgumentException("Inputs cannot be null.");
	    }
	    if (inputs.length != weights.length) {
	        throw new IllegalArgumentException(
	                "Input array length must be the same as weights length");
	    }

	    float weightedSum = 0.0f;
	    
	    for (int i = 0; i < inputs.length; i++) {
	        weightedSum += inputs[i] * weights[i];
	    }
	    
	    weightedSum += bias;
	    
	    return (float) 0.0; //(float) Util.sigmoid(weightedSum);
	}
	
	// Weights setters / getters
	
	public void setWeights(float[] weights) {
		this.weights = weights.clone();
	}
	
	public float[] getWeights() {
		return weights.clone();
	}
	
	public void randomizeWeights(int weightsAmount) {
	    weights = new float[weightsAmount];
	    
	    Random random = new Random();
	    for (int i = 0; i < weightsAmount; i++) {
	        weights[i] = MIN_STARTING_WEIGHT + random.nextFloat() * (MAX_STARTING_WEIGHT - MIN_STARTING_WEIGHT);
	    }
	}

	public void randomizeWeights() {
	    Random random = new Random();
	    for (int i = 0; i < weights.length; i++) {
	        weights[i] = MIN_STARTING_WEIGHT + random.nextFloat() * (MAX_STARTING_WEIGHT - MIN_STARTING_WEIGHT);
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
	    Random random = new Random();
	    bias = MIN_STARTING_BIAS + random.nextFloat() * (MAX_STARTING_BIAS - MIN_STARTING_BIAS);
	}

	// Identifiers

	public UUID getId() {
		return id;
	}

	public Vector2 getPosition() {
		return position;
	}
	
	
	
}
