package Java.Neuron;

import Java.Activation.ActivationFunctionType;

public class NeuronLayer {

    private static final ActivationFunctionType activationFunctionType = ActivationFunctionType.LEAKY_RELU;

    private Neuron[] neurons;

    private int layer;

    public NeuronLayer(int layer, int neuronAmount) {
        this.layer = layer;

        neurons = new Neuron[neuronAmount];
        for (int i = 0; i < neuronAmount; i++) {
            NeuronDTO neuronDTO = new NeuronDTO();
            neuronDTO.setLayer(layer);
            neuronDTO.setIndex(i);
            neuronDTO.setActivationFunctionType(activationFunctionType);
            //neuronDTO.setInputAmount(neuronAmount);
        }
    }

    public Neuron[] getNeurons() {
        return neurons;
    }
}
