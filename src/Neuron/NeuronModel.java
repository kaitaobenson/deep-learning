package Neuron;

import Digit.Digit;
import Digit.DigitContainer;
import Util.NeuronUtil;
import java.io.Serializable;


public class NeuronModel implements Serializable {

    private static final float LEARNING_RATE = -0.05f;
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

        // Inputs to the first layer are the pixels of the digit
        float[] layerInputs = digit.getPixels();

        // Target outputs (actual labels) for the digit
        float[] targetOutputs = NeuronUtil.getTargets(digit);

        // Predicted outputs (what the model thinks the digit is)
        float[] predictedOutputs = outputData.getOutputs();

        // Initial error is the derivative of MSE with respect to the predicted outputs
        float[] errors = NeuronUtil.getMseDerivative(predictedOutputs, targetOutputs);

        // Initialize nextErrors to store the propagated error for the next layer
        float[] nextErrors = errors;

        // Backpropagate through all layers starting from the last layer
        for (int i = neuronLayers.length - 1; i >= 0; i--) {
            NeuronLayer layer = neuronLayers[i];

            // Perform backpropagation on the current layer using the errors and update the weights
            layer.backpropagate(nextErrors, LEARNING_RATE, layerInputs);

            if (i != 0) {
                // The inputs to the current layer come from the outputs of the previous layer
                layerInputs = neuronLayers[i - 1].getOutputs();

                // Initialize the errors for the previous layer, which should match the size of the inputs to the current layer
                nextErrors = new float[layerInputs.length]; // Size should be the number of inputs to the current layer

                // Loop over each neuron in the current layer
                for (int j = 0; j < layer.getNeurons().length; j++) {
                    Neuron neuron = layer.getNeurons()[j];
                    float delta = neuron.getDelta();

                    // For each neuron, propagate the delta back to each corresponding input using its weights
                    for (int k = 0; k < neuron.getWeights().length; k++) {
                        // Ensure we are not going out of bounds in nextErrors and weights
                        if (k < nextErrors.length) {
                            // Accumulate the error for the input neuron at index k
                            nextErrors[k] += delta * neuron.getWeights()[k];
                        } else {
                            System.err.println("Index out of bounds! Weights length: " + neuron.getWeights().length + ", nextErrors length: " + nextErrors.length);
                        }
                    }
                }
            }
        }
    }


}