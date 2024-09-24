package Java.Neuron;

import Java.Activation.ActivationFunctionType;
import Java.Digit.Digit;
import java.util.*;

public class NeuronModel {

    private NeuronLayer[] neuronLayers;

    public NeuronModel(NeuronLayer[] neuronLayers) {
        this.neuronLayers = neuronLayers.clone();
        randomizeModel();
    }

    public void randomizeModel() {
        if (neuronLayers == null) {
            return;
        }

        for (NeuronLayer layer : neuronLayers) {
            layer.randomizeWeights();
            layer.randomizeBiases();
        }
    }

    public NeuronLayer[] getNeuronLayers() {
        return neuronLayers.clone();
    }

    public void inputDigit(Digit digit) {

        float[] pixels = digit.getPixels();

        System.out.println(digit);

        // Calculate the outputs through each neuron layer
        float[] input = pixels;
        for (NeuronLayer neuronLayer : neuronLayers) {
            input = neuronLayer.calculateOutputs(input);
        }

        float[] outputLayerValue = input;

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
