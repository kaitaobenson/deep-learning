package data;

import activation.IActivationFunction;
import activation.LeakyReLu;
import network.NeuronLayer;
import network.NeuronModel;

import java.util.Arrays;

public class LayerAmountDataCollector {
    public String name;
    public int testSize;
    public int inputAmount;
    public int outputAmount;

    public float[] xColumn;
    public float[] yColumn;

    public float learningRate;
    public IActivationFunction activationFunction;
    public int layerAmount;
    public int neuronAmount;
    public int epochAmount;

    public boolean miniBatch;
    public int batchSize;

    public LayerAmountDataCollector(String name, int testSize, int inputAmount, int outputAmount, float learningRate, IActivationFunction activationFunction, int initialLayerAmount, int neuronAmount, int epochAmount, boolean miniBatch, int batchSize){
        this.name = name;
        this.testSize = testSize;
        this.inputAmount = inputAmount;
        this.outputAmount = outputAmount;
        this.xColumn = new float[0];
        this.yColumn = new float[0];
        this.learningRate = learningRate;
        this.activationFunction = activationFunction;
        this.layerAmount = initialLayerAmount;
        this.neuronAmount = neuronAmount;
        this.epochAmount = epochAmount;
        this.miniBatch = miniBatch;
        this.batchSize = batchSize;
    }

    public float[][] CollectData(){
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
        }
    }
}
