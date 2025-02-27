package program;

import activation.ActivationFunctionType;
import activation.IActivationFunction;
import activation.LeakyReLu;
import data.logging.DataAmountTest;
import data.logging.LayerAmountTest;
import data.logging.NeuronAmountTest;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        //DataAmountTest dataTester = new DataAmountTest(3, 100, 0, 10,784, 10, .01f, new LeakyReLu(), 3, 10, 100);
        //dataTester.test();
        //LayerAmountTest layerTester = new LayerAmountTest(3, 10, 1, 1, 784, 10, .01f, new LeakyReLu(), 16, 1, true, 100);
        //layerTester.test();
        NeuronAmountTest neuronTester = new NeuronAmountTest(3, 100, 1, 1, 784, 10, .01f, new LeakyReLu(), 3, 1, true, 100);
        neuronTester.test();
    }
}
