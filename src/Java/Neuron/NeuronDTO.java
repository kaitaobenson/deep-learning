package Java.Neuron;

import Java.Activation.ActivationFunctionType;

public class NeuronDTO {

    private int layer;
    private int index;

    private ActivationFunctionType activationFunctionType;

    private int inputAmount;


    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ActivationFunctionType getActivationFunctionType() {
        return activationFunctionType;
    }

    public void setActivationFunctionType(ActivationFunctionType activationFunctionType) {
        this.activationFunctionType = activationFunctionType;
    }

    public int getInputAmount() {
        return inputAmount;
    }

    public void setInputAmount(int inputAmount) {
        this.inputAmount = inputAmount;
    }
}
