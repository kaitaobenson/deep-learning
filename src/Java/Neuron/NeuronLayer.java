package Java.Neuron;

public class NeuronLayer {

    private Neuron[] neurons;

    private int layer;

    public NeuronLayer(int layer, int neuronAmount) {
        this.layer = layer;

        neurons = new Neuron[neuronAmount];
        for (int i = 0; i < neuronAmount; i++) {
            neurons[i] = new Neuron(layer, i);
        }
    }

    public Neuron[] getNeurons() {
        return neurons;
    }
}
