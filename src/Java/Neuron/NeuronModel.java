package Java.Neuron;

import Java.Digit.Digit;

public class NeuronModel {

    private NeuronLayer neuronLayer1 = new NeuronLayer(1, 16);
    private NeuronLayer neuronLayer2 = new NeuronLayer(2, 16);

    public NeuronModel() {

    }

    public void inputDigit(Digit digit) {
        Neuron[] neurons = neuronLayer1.getNeurons();
        //for (neuron : neurons) {

        //}

    }



}
