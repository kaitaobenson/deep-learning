package data;

import activation.IActivationFunction;
import network.NeuronLayer;
import network.NeuronModel;
import network.output.OutputAllData;
import persistence.MnistLoader;

import java.util.Arrays;

public class LayerAmountTest {
    public int testSize;
    public int inputAmount;
    public int outputAmount;
    public float learningRate;
    public IActivationFunction activationFunction;
    public int neuronAmount;
    public int epochAmount;
    public boolean miniBatch;
    public int batchSize;

    // Independent variable
    public int layerAmount;

    public LayerAmountTest(int testSize, int inputAmount, int outputAmount, float learningRate, IActivationFunction activationFunction, int initialLayerAmount, int neuronAmount, int epochAmount, boolean miniBatch, int batchSize){
        this.testSize = testSize;
        this.inputAmount = inputAmount;
        this.outputAmount = outputAmount;
        this.learningRate = learningRate;
        this.activationFunction = activationFunction;
        this.neuronAmount = neuronAmount;
        this.epochAmount = epochAmount;
        this.miniBatch = miniBatch;
        this.batchSize = batchSize;

        // Set layer amount to it's initial amount
        this.layerAmount = initialLayerAmount;
    }

    public float[][] TestLayerAmount(){
        float[][] data = new float[2][testSize];

        for (int i = 0; i < testSize; i++) {
            // Create layers
            NeuronLayer[] neuronLayers = new NeuronLayer[layerAmount + 1];

            // Create input layer
            neuronLayers[0] = NeuronLayer.createInputLayer(new float[inputAmount]);
            // Create hidden layer(s)
            if (layerAmount > 1) {
                neuronLayers[1] = NeuronLayer.createHiddenLayer(inputAmount, neuronAmount, activationFunction);
                Arrays.fill(neuronLayers, 2, layerAmount, NeuronLayer.createHiddenLayer(neuronAmount, neuronAmount, activationFunction));
                // Create output layer
                neuronLayers[layerAmount] = NeuronLayer.createHiddenLayer(neuronAmount, outputAmount, activationFunction);
            }
            else if (layerAmount == 1){
                neuronLayers[1] = NeuronLayer.createHiddenLayer(inputAmount, outputAmount, activationFunction);
            }

            // Init model
            NeuronModel neuronModel = new NeuronModel(neuronLayers, miniBatch, batchSize, learningRate);

            // Load test and train digits
            MnistLoader mnistLoader = new MnistLoader();
            DataSet testingDigitContainer = mnistLoader.getTestingDigits();
            DataSet trainingDigitContainer = mnistLoader.getTrainingDigits();

            // Train model with set parameters
            neuronModel.trainModel(trainingDigitContainer, epochAmount);

            // Test model
            OutputAllData outputData = neuronModel.testAll(testingDigitContainer);

            // Record input value
            data[0][i] = layerAmount;
            // Record output value
            data[1][i] = OutputAllData.getAccuracy();


        }

        return data;
    }
}
