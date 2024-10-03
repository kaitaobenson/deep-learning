package Neuron;

import Digit.Digit;
import java.io.Serializable;
import java.util.*;

public class NeuronModel implements Serializable {

    private final NeuronLayer[] neuronLayers;

    public NeuronModel(NeuronLayer[] neuronLayers) {
        this.neuronLayers = neuronLayers.clone();
        randomizeModel();
    }

    // Randomizes weights and biases for all layers
    public void randomizeModel() {
        if (neuronLayers == null) return;

        for (NeuronLayer layer : neuronLayers) {
            layer.randomizeWeights();
            layer.randomizeBiases();
        }
    }

    public NeuronLayer[] getNeuronLayers() {
        return neuronLayers.clone();
    }

    public void inputDigit(Digit digit) {
        float[] inputs = digit.getPixels();
        float[] outputs = processInputs(inputs);

        float[] targets = new float[outputs.length];
        targets[digit.getLabel()] = 1.0f;

        // Sort the output into a list of entries
        Map<Integer, Float> sortedOutputs = sortOutputs(outputs);

        // Display results
        displayOutputs(sortedOutputs);
        displayLoss(outputs, targets);
    }

    // Processes inputs through the neuron layers
    private float[] processInputs(float[] inputs) {
        for (NeuronLayer neuronLayer : neuronLayers) {
            inputs = neuronLayer.calculateOutputs(inputs);
        }
        return inputs;
    }

    // Sorts the outputs into a map with their confidence values
    private Map<Integer, Float> sortOutputs(float[] outputs) {
        Map<Integer, Float> outputMap = new HashMap<>();
        for (int i = 0; i < outputs.length; i++) {
            outputMap.put(i, outputs[i]);
        }

        // Sort by confidence values
        return outputMap.entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Float>comparingByValue().reversed())
                .collect(LinkedHashMap::new,
                        (m, e) -> m.put(e.getKey(), e.getValue()),
                        LinkedHashMap::putAll);
    }

    // Displays the output results and the best guess
    private void displayOutputs(Map<Integer, Float> sortedOutputs) {
        System.out.println("Output:");
        sortedOutputs.forEach((key, confidence) ->
                System.out.println(key + " - Confidence: " + confidence)
        );

        System.out.println("Best Guess:");
        System.out.println(sortedOutputs.keySet().iterator().next());
    }

    // Calculates and displays the loss
    private void displayLoss(float[] outputs, float[] targets) {
        System.out.println("Loss: " + calculateLoss(outputs, targets));
    }

    // Calculates the mean squared error (MSE) loss between outputs and targets
    public static float calculateLoss(float[] outputs, float[] targets) {
        if (outputs.length != targets.length) {
            throw new IllegalArgumentException("Outputs and targets must be the same length.");
        }

        float sumSquaredDifference = 0.0f;
        for (int i = 0; i < outputs.length; i++) {
            sumSquaredDifference += Math.pow(outputs[i] - targets[i], 2);
        }

        // Return the mean squared difference
        return sumSquaredDifference / targets.length;
    }
}