package Java.Neuron;

import Java.Activation.ActivationFunctionType;

public class NeuronDTO {

    private Integer layer;
    private Integer index;
    private Integer inputAmount;

    private ActivationFunctionType activationFunction;


    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getInputAmount() {
        return inputAmount;
    }

    public void setInputAmount(Integer inputAmount) {
        this.inputAmount = inputAmount;
    }

    public ActivationFunctionType getActivationFunction() {
        return activationFunction;
    }

    public void setActivationFunction(ActivationFunctionType activationFunction) {
        this.activationFunction = activationFunction;
    }
}
