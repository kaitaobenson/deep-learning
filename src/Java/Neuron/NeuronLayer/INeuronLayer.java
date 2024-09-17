package Java.Neuron.NeuronLayer;

import Java.Neuron.Neuron;

public interface INeuronLayer {

    public Neuron[] getNeurons();

    public float[] inputToNeurons(float[] inputs);

}
