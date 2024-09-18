package Java.Neuron;

import Java.Activation.ActivationFunctionType;
import Java.Digit.Digit;

import java.util.*;

public class NeuronModel {

    private NeuronLayer hidden1NeuronLayer = new NeuronLayer(16, 784, ActivationFunctionType.LEAKY_RELU);
    private NeuronLayer hidden2NeuronLayer = new NeuronLayer(16, 16, ActivationFunctionType.LEAKY_RELU);
    private NeuronLayer outputNeuronLayer = new NeuronLayer(10, 16, ActivationFunctionType.SIGMOID);

    public NeuronModel() {
        hidden1NeuronLayer.randomizeWeights();
        hidden1NeuronLayer.randomizeBiases();

        hidden2NeuronLayer.randomizeWeights();
        hidden2NeuronLayer.randomizeBiases();

        outputNeuronLayer.randomizeWeights();
        outputNeuronLayer.randomizeBiases();
    }

    public void inputDigit(Digit digit) {

        float[] pixels = digit.getPixels();

        System.out.println(digit);

        // Calculate the outputs through each neuron layer
        float[] hidden1LayerValue = hidden1NeuronLayer.calculateOutputs(pixels);
        float[] hidden2LayerValue = hidden2NeuronLayer.calculateOutputs(hidden1LayerValue);
        float[] outputLayerValue = outputNeuronLayer.calculateOutputs(hidden2LayerValue);

        // Store the output in a HashMap
        HashMap<Integer, Float> outputHashMap = new HashMap<>();
        for (int i = 0; i < outputLayerValue.length; i++) {
            outputHashMap.put(i, outputLayerValue[i]);
        }

        // Sort the HashMap
        List<Map.Entry<Integer, Float>> sortedEntries = new ArrayList<>(outputHashMap.entrySet());
        sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Display
        System.out.println("Output:");
        for (Map.Entry<Integer, Float> entry : sortedEntries) {
            System.out.println(entry.getKey() + " - Confidence: " + entry.getValue());
        }

        System.out.println("Best Guess:");
        System.out.println(sortedEntries.get(0).getKey());

    }

}
