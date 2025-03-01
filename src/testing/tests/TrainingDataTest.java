package testing.tests;

import activation.IActivationFunction;
import data.DataSet;
import network.NeuronLayer;
import network.NeuronModel;
import network.output.OutputAllData;
import persistence.MnistLoader;
import testing.NetworkTest;
import testing.PointLogger;

public class TrainingDataTest implements NetworkTest {
    public int testRunSize;
    public int testRunAmount;
    public int inputAmount;
    public int outputAmount;
    public float learningRate;
    public IActivationFunction activationFunction;
    public int neuronAmount;
    public int batchSize;
    public int layerAmount;

    // Independent variable
    public int batchAmount;

    // Independent variable step size
    public int batchStepSize;

    public TrainingDataTest(int testRunSize, int testRunAmount, int initialBatchAmount, int batchStepSize, int inputAmount, int outputAmount, float learningRate, IActivationFunction activationFunction, int layerAmount, int neuronAmount, int batchSize){
        this.testRunSize = testRunSize;
        this.testRunAmount = testRunAmount;
        this.inputAmount = inputAmount;
        this.outputAmount = outputAmount;
        this.learningRate = learningRate;
        this.activationFunction = activationFunction;
        this.neuronAmount = neuronAmount;
        this.batchSize = batchSize;
        this.layerAmount = layerAmount;

        // Set batch amount to its initial size
        this.batchAmount = initialBatchAmount;
        this.batchStepSize = batchStepSize;
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
                NeuronModel neuronModel = new NeuronModel(neuronLayers, true, batchSize, learningRate);

                trainingDigitContainer.shuffle();

                // Create training digit thing
                DataSet trainingDigits = new DataSet(batchAmount * batchSize);
                int iterations = Math.ceilDiv(batchAmount * batchSize, trainingDigitContainer.data.size());
                for (int k = 0; k < iterations; k++){
                    if (k != iterations - 1) {
                        trainingDigits.data.addAll(trainingDigitContainer.data);
                    } else {
                        trainingDigits.data.addAll(trainingDigitContainer.data.subList(0, (batchAmount * batchSize) % trainingDigitContainer.data.size()));
                    }
                }

                // Train model with set parameters
                neuronModel.trainModel(trainingDigits, 1);

                // Test model
                neuronModel.testAll(testingDigitContainer);

                // Record input and output values
                int correctSamples = OutputAllData.correctSamples;
                dataPointLogger.addPoint(batchAmount, correctSamples);
            }

            batchAmount += batchStepSize;
        }

        dataPointLogger.writeFile("TrainingDataTest", savePath);

        PointLogger averageLogger = dataPointLogger.getAvgPointLogger();
        averageLogger.writeFile("TrainingDataTestAvg", savePath);
    }
}
