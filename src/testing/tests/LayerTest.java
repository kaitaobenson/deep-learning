package testing.tests;

import activation.IActivationFunction;
import data.DataSet;
import network.NeuronLayer;
import network.NeuronModel;
import network.output.OutputAllData;
import persistence.MnistLoader;
import testing.NetworkTest;
import testing.PointLogger;

public class LayerTest implements NetworkTest {
    public int testRunSize;
    public int testRunAmount;
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

    // Independent variable step size
    int layerStepSize;

    public LayerTest(int testRunSize, int testRunAmount, int initialLayerAmount, int layerStepSize, int inputAmount, int outputAmount, float learningRate, IActivationFunction activationFunction, int neuronAmount, int epochAmount, boolean miniBatch, int batchSize) {
        this.testRunSize = testRunSize;
        this.testRunAmount = testRunAmount;
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
        this.layerStepSize = layerStepSize;
    }

    @Override
    public void test() {
        PointLogger dataPointLogger = new PointLogger();

        // Load test and train digits
        MnistLoader mnistLoader = new MnistLoader();
        DataSet testingDigitContainer = mnistLoader.getTestingDigits();
        DataSet trainingDigitContainer = mnistLoader.getTrainingDigits();

        for (int i = 0; i < testRunAmount; i++) {
            for (int j = 0; j < testRunSize; j++) {
                // Create layer array
                NeuronLayer[] neuronLayers = new NeuronLayer[layerAmount + 1];

                // Create input layer
                neuronLayers[0] = NeuronLayer.createInputLayer(new float[inputAmount]);
                // Create hidden layer(s)
                if (layerAmount > 1) {
                    neuronLayers[1] = NeuronLayer.createHiddenLayer(inputAmount, neuronAmount, activationFunction);
                    for (int k = 2; k < layerAmount; k++){
                        neuronLayers[k] = NeuronLayer.createHiddenLayer(neuronAmount, neuronAmount, activationFunction);
                    }
                    // Create output layer
                    neuronLayers[layerAmount] = NeuronLayer.createHiddenLayer(neuronAmount, outputAmount, activationFunction);
                } else if (layerAmount == 1) {
                    neuronLayers[1] = NeuronLayer.createHiddenLayer(inputAmount, outputAmount, activationFunction);
                }

                // Init model
                NeuronModel neuronModel = new NeuronModel(neuronLayers, miniBatch, batchSize, learningRate);

                trainingDigitContainer.shuffle();

                // Train model with set parameters
                neuronModel.trainModel(trainingDigitContainer, epochAmount);

                // Test model
                OutputAllData outputData = neuronModel.testAll(testingDigitContainer);

                // Record input and output values
                int correctSamples = outputData.correctSamples;
                dataPointLogger.addPoint(layerAmount, correctSamples);
            }

            layerAmount += layerStepSize;
        }

        dataPointLogger.writeFile("LayerData", savePath);

        PointLogger averageLogger = dataPointLogger.getAvgPointLogger();
        averageLogger.writeFile("LayerDataAvg", savePath);
    }
}
