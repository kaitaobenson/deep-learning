package data.logging;

import activation.IActivationFunction;
import data.DataSet;
import network.NeuronLayer;
import network.NeuronModel;
import network.output.OutputAllData;
import persistence.MnistLoader;

import java.util.Arrays;

public class DataAmountTest implements NetworkTest {
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

    public DataAmountTest(int testRunSize, int testRunAmount, int inputAmount, int outputAmount, float learningRate, IActivationFunction activationFunction, int layerAmount, int neuronAmount, int batchSize, int initialBatchAmount){
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
    }

    @Override
    public void test(){
        PointLogger dataPointLogger = new PointLogger("Layer amount logger", "For logging the input and output data of a layer amount network test");

        for (int i = 0; i < testRunAmount; i++) {
            for (int j = 0; j < testRunSize; j++) {

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
                } else if (layerAmount == 1) {
                    neuronLayers[1] = NeuronLayer.createHiddenLayer(inputAmount, outputAmount, activationFunction);
                }

                // Init model
                NeuronModel neuronModel = new NeuronModel(neuronLayers, true, batchSize, learningRate);

                // Load test and train digits
                MnistLoader mnistLoader = new MnistLoader();
                DataSet testingDigitContainer = mnistLoader.getTestingDigits();
                DataSet trainingDigitContainer = mnistLoader.getTrainingDigits();

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
                OutputAllData outputData = neuronModel.testAll(testingDigitContainer);

                // Record input and output values
                dataPointLogger.addPoint(batchAmount, OutputAllData.getAccuracy());
            }

            batchAmount += 1;
        }

        dataPointLogger.writeFile("data");
    }
}
