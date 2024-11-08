package Neuron;

import Activation.ActivationFunctionType;
import Digit.Digit;
import Digit.DigitContainer;
import Util.NeuronUtil;
import java.io.Serializable;
import java.util.Arrays;

import Activation.IActivationFunction;

public class NeuronModel implements Serializable {

    private static final float LEARNING_RATE = .001f;
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

        System.out.println("Targets: " + Arrays.toString(targetOutputs));
        System.out.println(digit);

        // Inputs to the first layer are the pixels of the digit
        float[] layerInputs = digit.getPixels();

        float[] mseDerivatives = NeuronUtil.getMseDerivative(predictedOutputs, targetOutputs);

        float[] errors = new float[mseDerivatives.length];

        for (int i = 0; i < errors.length; i++){
            errors[i] = mseDerivatives[i] * neuronLayers[neuronLayers.length - 1].getActivationFunction().outputDerivative(neuronLayers[neuronLayers.length - 1].getWeightedSums()[i]);
        }

        for (int i = neuronLayers.length - 1; i >= 0; i--){
            NeuronLayer previousLayer;
            if (i != 0){
                previousLayer = neuronLayers[i - 1];
            }
            else{
                previousLayer = null;
            }

            errors = neuronLayers[i].backpropagate(errors, LEARNING_RATE, previousLayer);
        }
        feedforward(digit);
    }
}