package Java.Neuron.NeuronLayer;

import Java.Activation.ActivationFunctionType;
import Java.Neuron.Neuron;
import Java.Neuron.NeuronDTO;

public class NeuronLayer {

    private static final ActivationFunctionType activationFunctionType = ActivationFunctionType.SIGMOID;

    private Neuron[] neurons;

    private int layer;

    public NeuronLayer(int layer, int neuronAmount) {
        this.layer = layer;

        neurons = new Neuron[neuronAmount];

        for (int i = 0; i < neuronAmount; i++) {
            NeuronDTO neuronDTO = new NeuronDTO();
            neuronDTO.setLayer(layer);
            neuronDTO.setIndex(i);
            //neuronDTO.setActivationFunctionType(activationFunctionType);
            //neuronDTO.setInputAmount(neuronAmount);
        }
    }

    public Neuron[] getNeurons() {
        return neurons;
    }
}
