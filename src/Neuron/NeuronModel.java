package Neuron;

import Digit.Digit;
import Digit.DigitContainer;
import Util.NeuronUtil;

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

    public int testDigits(DigitContainer digitContainer) {
        int correctGuesses = 0;

        for (Digit digit : digitContainer.getDigits()) {
            OutputData outputData = inputDigit(digit);

            if (outputData.getBestGuess() == digit.getLabel()) {
                correctGuesses += 1;
            }
        }

        return correctGuesses;
    }

    // Gets OutputData from a digit
    public OutputData inputDigit(Digit digit) {
        float[] inputs = digit.getPixels();

        float[] outputs = processInputs(inputs);
        float[] targets = NeuronUtil.getTargets(digit);

        return new OutputData(outputs, digit);
    }

    // Processes inputs through the neuron layers
    private float[] processInputs(float[] inputs) {
        for (NeuronLayer neuronLayer : neuronLayers) {
            inputs = neuronLayer.calculateOutputs(inputs);
        }
        return inputs;
    }
}