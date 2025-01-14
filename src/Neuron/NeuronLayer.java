package Neuron;

import Activation.IActivationFunction;
import Activation.LeakyReLu;
import Util.GeneralUtil;

import java.io.Serializable;

public class NeuronLayer implements Serializable {
    private static final float MAX_STARTING_BIAS = .1f;
    private static final float MIN_STARTING_BIAS = -.1f;

    private static final float MAX_STARTING_WEIGHT = .1f;
    private static final float MIN_STARTING_WEIGHT = -.1f;

    private final int inputAmount;
    private final int neuronAmount;

    private final IActivationFunction activationFunction;

    private float[][] weights;
    private float[] biases;

    private float[] inputs;
    private float[] weightedSums;
    private float[] outputs;


    public NeuronLayer(int neuronAmount, int inputAmount, IActivationFunction activationFunction) {
        this.inputAmount = inputAmount;
        this.neuronAmount = neuronAmount;
        this.activationFunction = activationFunction;
        this.weights = new float[neuronAmount][inputAmount];
        this.biases = new float[neuronAmount];
    }

    // Calculate the outputs of all neurons in the layer
    public float[] forward(float[] inputs) {
        this.inputs = inputs;
        outputs = new float[weights.length];
        weightedSums = new float[weights.length];

        weightedSums = dotProduct(weights, inputs);

        for (int i = 0; i < weightedSums.length; i++){
            weightedSums[i] += biases[i];
            outputs[i] = activationFunction.output(weightedSums[i]);
        }

        return outputs;
    }


    // Neuron-based backpropagation for the layer
    public float[] backpropagate(float[] errors, float LEARNING_RATE, NeuronLayer previousLayer) {
        float[] nextErrors = new float[inputs.length];

        if (previousLayer != null) {
            float[][] weightsT = transpose(weights);
            for (int i = 0; i < weightsT.length; i++) {
                float sum = 0.0f;

                for (int j = 0; j < weightsT[i].length; j++) {
                    sum += errors[j] * weightsT[i][j];
                }

                nextErrors[i] = sum * previousLayer.getActivationFunction().outputDerivative(previousLayer.getWeightedSums()[i]);
            }
        }

        for (int i = 0; i < weights.length; i++){
            for (int j = 0; j < weights[i].length; j++){
                float weightGradient = errors[i] * inputs[j];

                weights[i][j] -= weightGradient * LEARNING_RATE;
            }

            float biasGradient = errors[i];

            biases[i] -= biasGradient * LEARNING_RATE;
        }

        return nextErrors;
    }

    // Setters / Getters
    public void randomizeWeights() {
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = GeneralUtil.randomFloat(MIN_STARTING_WEIGHT, MAX_STARTING_WEIGHT);
            }
        }
    }
    public void randomizeBiases() {
        for (int i = 0; i < biases.length; i++) {
            biases[i] = GeneralUtil.randomFloat(MIN_STARTING_BIAS, MAX_STARTING_BIAS);
        }
    }

    public IActivationFunction getActivationFunction() {
        return activationFunction;
    }
    public float[] getOutputs() {
        return outputs.clone();
    }
    public float[] getWeightedSums(){
        return weightedSums.clone();
    }
    public float[][] getWeights(){
        return weights.clone();
    }
    public float[] getBiases(){
        return biases.clone();
    }
    public int getNeuronAmount(){
        return neuronAmount;
    }
    public int getInputAmount(){
        return inputAmount;
    }

    public float[] dotProduct(float[][] weights, float[] inputs){
        float[] multipliedArray = new float[weights.length];

        if (weights[0].length != inputs.length){
            throw new IllegalArgumentException("The number of input features must match the number of weight columns.");
        }

        for (int i = 0; i < weights.length; i++){
            float sum = 0.0f;
            for (int j = 0; j < inputs.length; j++){
                sum += weights[i][j] * inputs[j];
            }
            multipliedArray[i] = sum;
        }

        return multipliedArray;
    }

    public float[][] transpose(float[][] matrix){
        // Validate matrix uniformity (all rows must have the same length)
        int secondDimensionShape = matrix[0].length;
        for (int i = 1; i < matrix.length; i++){
            if (matrix[i].length != secondDimensionShape){
                throw new IllegalArgumentException("Matrix shape must be uniform to transpose");
            }
        }

        float[][] transposedMatrix = new float[secondDimensionShape][matrix.length];

        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < secondDimensionShape; j++){
                transposedMatrix[j][i] = matrix[i][j];
            }
        }

        return transposedMatrix;
    }

    public float[] softmax(float[] outputs){
        float sum = 0;
        for (int i = 0; i < outputs.length; i++){
            sum += (float) Math.exp(outputs[i]);
        }

        float[] softmaxOutputs = new float[outputs.length];
        for (int i = 0; i < outputs.length; i++){
            softmaxOutputs[i] = (float) Math.exp(outputs[i]/sum);
        }
        return softmaxOutputs;
    }
}