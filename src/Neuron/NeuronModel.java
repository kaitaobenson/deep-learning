package Neuron;

import Activation.ActivationFunctionType;
import Digit.Digit;
import Digit.DigitContainer;
import Util.NeuronUtil;
import java.io.Serializable;
import java.util.Arrays;

import Activation.IActivationFunction;

public class NeuronModel implements Serializable {

    private static final float LEARNING_RATE = .01f;
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
        float[] incorrectGuesses = new float[neuronLayers[neuronLayers.length - 1].getNeurons().length];
        float[] incorrectGuessesTargets = new float[neuronLayers[neuronLayers.length - 1].getNeurons().length];

        for (Digit digit : digitContainer.getDigits()) {
            OutputData outputData = feedforward(digit);

            if (outputData.getBestGuess() == digit.getLabel()) {
                correctGuesses += 1;
            }
            else{
                incorrectGuesses[outputData.getBestGuess()] += 1;
                incorrectGuessesTargets[digit.getLabel()] += 1;
            }
        }
        System.out.println("Incorrect guesses: " + Arrays.toString(incorrectGuesses) + "\n" + "Incorrect guesses targets: " + Arrays.toString(incorrectGuessesTargets));
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

    public void train(DigitContainer digitContainer, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            float totalLoss = 0.0f;

            for (int i = 0; i < digitContainer.getDigitAmount(); i++){
                Digit digit = digitContainer.getDigit(i);

                float[] outputs = feedforward(digit).getOutputs();
                float[] targets = NeuronUtil.getTargets(digit);
                float loss = NeuronUtil.getMse(outputs, targets);
                System.out.println("Loss: " + loss);

                totalLoss += loss;

                backpropagate(outputs, targets, digit);
            }
            System.out.printf("Epoch %d: Average Loss = %.4f%n", epoch + 1, totalLoss / digitContainer.getDigitAmount());
        }
    }


    public void backpropagate(float[] outputs, float[] targets, Digit digit) {
        System.out.println("Targets: " + Arrays.toString(targets));
        System.out.println("Outputs: " + Arrays.toString(outputs));

        float[] errors = calculateInitalErrors(outputs, targets);
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
    }

    private float[] calculateInitalErrors(float[] outputs, float[] targets) {
        float[] errors = NeuronUtil.getMseDerivative(outputs, targets);

        NeuronLayer lastLayer = neuronLayers[neuronLayers.length - 1];
        for (int i = 0; i < errors.length; i++){
            errors[i] *= lastLayer.getActivationFunction().outputDerivative(lastLayer.getNeurons()[i].getWeightedSum());
        }

        return errors;
    }
}