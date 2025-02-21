package data;

import activation.IActivationFunction;
import activation.LeakyReLu;
import network.NeuronLayer;
import network.NeuronModel;

import java.util.Arrays;

public class DataCollector {
    public String name;
    public int testSize;
    public float[] xColumn;
    public float[] yColumn;

    public DataCollector(String name, int testSize, int inputAmount, int outputAmount, float learningRate, IActivationFunction activationFunction, int initialLayerAmount, int neuronAmount, int epochAmount, boolean miniBatch, int batchSize){
        this.name = name;
        this.testSize = testSize;
        this.xColumn = new float[0];
        this.yColumn = new float[0];
    }
}
