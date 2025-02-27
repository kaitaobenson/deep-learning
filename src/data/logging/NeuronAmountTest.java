package data.logging;

import activation.IActivationFunction;
import data.DataSet;
import network.NeuronLayer;
import network.NeuronModel;
import network.output.OutputAllData;
import persistence.MnistLoader;

public class NeuronAmountTest implements NetworkTest {
    public int testRunSize;
    public int testRunAmount;
    public int inputAmount;
    public int outputAmount;
    public float learningRate;
    public IActivationFunction activationFunction;
    public int layerAmount;
    public int epochAmount;
    public boolean miniBatch;
    public int batchSize;

    // Independent variable
    public int neuronAmount;

    // Independent variable step size
    int neuronStepSize;

    public NeuronAmountTest(int testRunSize, int testRunAmount, int initialNeuronAmount, int neuronStepSize, int inputAmount, int outputAmount, float learningRate, IActivationFunction activationFunction, int layerAmount, int epochAmount, boolean miniBatch, int batchSize){
        this.testRunSize = testRunSize;
        this.testRunAmount = testRunAmount;
        this.inputAmount = inputAmount;
        this.outputAmount = outputAmount;
        this.learningRate = learningRate;
        this.activationFunction = activationFunction;
        this.layerAmount = layerAmount;
        this.epochAmount = epochAmount;
        this.miniBatch = miniBatch;
        this.batchSize = batchSize;

        // Set layer amount to it's initial amount
        this.neuronAmount = initialNeuronAmount;
        this.neuronStepSize = neuronStepSize;
    }

    @Override
    public void test(){
        PointLogger dataPointLogger = new PointLogger("Neuron amount logger", "For logging the input and output data of a neuron amount network test");

        for (int i = 0; i < testRunAmount; i++) {
            for (int j = 0; j < testRunSize; j++) {
                System.out.println(i+j);
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

                // Load test and train digits
                MnistLoader mnistLoader = new MnistLoader();
                DataSet testingDigitContainer = mnistLoader.getTestingDigits();
                DataSet trainingDigitContainer = mnistLoader.getTrainingDigits();

                // Train model with set parameters
                neuronModel.trainModel(trainingDigitContainer, epochAmount);

                // Test model
                OutputAllData outputData = neuronModel.testAll(testingDigitContainer);

                // Record input and output values
                dataPointLogger.addPoint(neuronAmount, OutputAllData.getAccuracy());
            }

            neuronAmount += neuronStepSize;
        }

        dataPointLogger.writeFile("neuronAmountData");
    }
}
