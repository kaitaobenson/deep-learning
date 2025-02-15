package Neuron;

import Activation.ActivationFunctionType;
import Digit.Digit;
import Digit.DigitContainer;
import Util.NeuronUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import Activation.IActivationFunction;

public class NeuronModel implements Serializable {

    private static final float LEARNING_RATE = .01f;
    private static boolean miniBatch = true;
    private static int batchSize = 5000;
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

    public int feedforwardALL(DigitContainer digitContainer) {
        int correctGuesses = 0;
        float[] incorrectGuesses = new float[neuronLayers[neuronLayers.length - 1].getNeuronAmount()];
        float[] incorrectGuessesTargets = new float[neuronLayers[neuronLayers.length - 1].getNeuronAmount()];

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

            DigitContainer[] batches = new DigitContainer[1];
            if (miniBatch){
                batches = generateMiniBatches(digitContainer, batchSize);
            }
            else{
                batches[0] = digitContainer;
            }

            for (DigitContainer batch : batches)
                for (int i = 0; i < batch.getDigitAmount(); i++){
                    Digit digit = batch.getDigit(i);

                    float[] outputs = feedforward(digit).getOutputs();
                    float[] targets = NeuronUtil.getTargets(digit);
                    float loss = NeuronUtil.getMse(outputs, targets);
                    //System.out.println("Loss: " + loss);

                    totalLoss += loss;

                    backpropagate(outputs, targets);
                }

                if (miniBatch){
                    for (NeuronLayer neuronLayer : neuronLayers) {
                        neuronLayer.step(batchSize, LEARNING_RATE);
                    }
            }

            System.out.printf("Epoch %d: Average Loss = %.4f%n", epoch + 1, totalLoss / digitContainer.getDigitAmount());
        }

        //System.out.println(Arrays.deepToString(neuronLayers[neuronLayers.length-1].getWeights()));
        //System.out.println(Arrays.toString(neuronLayers[neuronLayers.length-1].getBiases()));
    }

    public DigitContainer[] generateMiniBatches(DigitContainer digitContainer, int batchSize) {
        if (digitContainer.getDigitAmount() % batchSize != 0){
            throw new IllegalArgumentException("Error: The batch size does not evenly divide the dataset size. Please choose a batch size that is a factor of the total data amount.");
        }

        DigitContainer[] miniBatches = new DigitContainer[digitContainer.getDigitAmount()/batchSize];

        for (int i = 0; i < miniBatches.length; i++){
            miniBatches[i] = new DigitContainer();
        }

        Digit[] digits = digitContainer.getDigits();
        for (int i = 0; i < digits.length; i++){
            int batch = Math.floorDiv(i, batchSize);
            miniBatches[batch].addDigit(digits[i]);
        }

        return miniBatches;
    }

    public void backpropagate(float[] outputs, float[] targets) {
        //System.out.println("Targets: " + Arrays.toString(targets));
        //System.out.println("Outputs: " + Arrays.toString(outputs));

        float[] errors = calculateInitalErrors(outputs, targets);
        //System.out.println(Arrays.toString(errors));
        for (int i = neuronLayers.length - 1; i >= 0; i--){
            NeuronLayer previousLayer;
            if (i != 0){
                previousLayer = neuronLayers[i - 1];
            }
            else{
                previousLayer = null;
            }
            errors = neuronLayers[i].backpropagate(errors, LEARNING_RATE, previousLayer);
            
            if (!miniBatch){
                neuronLayers[i].step(1, LEARNING_RATE);
            }
        }
    }

    private float[] calculateInitalErrors(float[] outputs, float[] targets) {
        float[] errors = NeuronUtil.getMseDerivative(outputs, targets);

        NeuronLayer lastLayer = neuronLayers[neuronLayers.length - 1];
        for (int i = 0; i < errors.length; i++){
            errors[i] *= lastLayer.getActivationFunction().outputDerivative(lastLayer.getWeightedSums()[i]);
        }

        return errors;
    }
}