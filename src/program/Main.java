package program;


import activation.ActivationFunctionType;
import activation.IActivationFunction;
import activation.LeakyReLu;
import data.DataCollector;
import data.LayerAmountTest;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        LayerAmountTest tester = new LayerAmountTest(10, 784, 10, .01f, new LeakyReLu(), 1, 10, 5, false, 0);
        float[][] data = tester.TestLayerAmount();

        DataCollector collector = new DataCollector("Rizzer", 34444);
        collector.DataCSVWriter("rizzing.csv", data);
    }
}
