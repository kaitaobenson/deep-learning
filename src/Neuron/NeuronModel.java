package Neuron;

import Digit.Digit;
import Digit.DigitContainer;
import Util.NeuronUtil;
import java.io.Serializable;


public class NeuronModel implements Serializable {

    private static final float LEARNING_RATE = 1.0f;
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

        return new OutputData(outputs, digit);
    }

    // Processes inputs through the neuron layers
    private float[] processInputs(float[] inputs) {
        for (NeuronLayer neuronLayer : neuronLayers) {
            inputs = neuronLayer.forward(inputs);
        }
        return inputs;
    }


    public void backpropagate(Digit digit, float[] targetOutputs) {
        OutputData outputData = feedforward(digit);

        float[] predictedOutputs = outputData.getOutputs();

        float[] errors = NeuronUtil.getMseDerivative(predictedOutputs, targetOutputs);

        float[] nextErrors = errors.clone();

        for (int i = neuronLayers.length - 1; i >= 0; i--){
            float[] layerInputs = digit.getPixels();

            // If the current layer isn't the first layer in the network, finds the current layer's inputs by doing a foward pass up to the current layer.
            // Otherwise, we can just use the inputs of the network
            if (i != 0) {
                for (int f = 0; f < i; i++){
                    // Very inefficient, but we don't have a variable that saves the outputs of a layer for the current training example I don't think
                    // So this is the best idea I had
                    layerInputs = neuronLayers[f].forward(layerInputs);
                }
            }
            nextErrors = neuronLayers[i].backpropagate(nextErrors, layerInputs, LEARNING_RATE);

            errors = neuronLayers[i].backpropagate(errors, layerInputs, LEARNING_RATE);
        }
    }


}