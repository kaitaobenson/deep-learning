package Neuron;

import Digit.Digit;
import Digit.DigitContainer;
import Util.NeuronUtil;
import java.io.Serializable;


public class NeuronModel implements Serializable {

    private static final float LEARNING_RATE = 0.005f;
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

    public int feedforwardAll(DigitContainer digitContainer) {
        int correctGuesses = 0;

        for (Digit digit : digitContainer.getDigits()) {
            OutputData outputData = feedforward(digit);

            if (outputData.getBestGuess() == digit.getLabel()) {
                correctGuesses += 1;
            }
        }

        return correctGuesses;
    }

    // Gets OutputData from a digit
    public OutputData feedforward(Digit digit) {
        float[] inputs = digit.getPixels();

        float[] outputs = processInputs(inputs);
        float[] targets = NeuronUtil.getTargets(digit);

        System.out.println(NeuronUtil.getMse(outputs, targets));

        return new OutputData(outputs, digit);
    }

    // Processes inputs through the neuron layers
    private float[] processInputs(float[] inputs) {
        for (NeuronLayer neuronLayer : neuronLayers) {
            inputs = neuronLayer.forward(inputs);
        }
        return inputs;
    }

    public void backpropagateAll(DigitContainer digitContainer) {
        for (int i = 0; i < digitContainer.getDigitAmount(); i++) {
            System.out.println("Backpropagate: " + i);
            Digit digit = digitContainer.getDigit(i);
            backpropagate(digit);
        }
    }


    public void backpropagate(Digit digit) {
        // Perform a forward pass to get the output data
        OutputData outputData = feedforward(digit);

        // Predicted outputs (what the model thinks the digit is)
        float[] predictedOutputs = outputData.getOutputs();

        // Target outputs (actual labels) for the digit
        float[] targetOutputs = NeuronUtil.getTargets(digit);

        // Inputs to the first layer are the pixels of the digit
        float[] layerInputs = digit.getPixels();

        float[] outputErrors = new float[predictedOutputs.length];

        for (int i = predictedOutputs.length - 1; i >= 0; i--){
            outputErrors[i] = targetOutputs[i] - predictedOutputs[i];
        }

        float[] nextErrors = outputErrors;

        for (int i = neuronLayers.length - 1; i >= 0; i--){
            nextErrors = neuronLayers[i].backpropagate(nextErrors, LEARNING_RATE);
        }
    }


}