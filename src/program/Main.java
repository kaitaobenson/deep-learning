package program;

import activation.ActivationFunctionType;
import activation.IActivationFunction;
import activation.LeakyReLu;
import data.logging.DataAmountTest;
import data.logging.LayerAmountTest;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        DataAmountTest tester = new DataAmountTest(3, 100,784, 10, .01f, new LeakyReLu(), 3, 10, 1000, 1);
        tester.test();
    }
}
