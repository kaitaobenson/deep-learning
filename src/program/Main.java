package program;

import activation.LeakyReLu;
import testing.tests.BatchTest;
import testing.tests.LayerTest;
import testing.tests.NeuronTest;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        /*
        // Test 1
        long startTime1 = System.currentTimeMillis();
        BatchTest dataTester = new BatchTest(3, 61, 0, 10,784, 10, .05f, new LeakyReLu(), 3, 15, 100);
        dataTester.test();

        long endTime1 = System.currentTimeMillis();
        System.out.println("Test1 done");
        System.out.println("Time elapsed: " + (endTime1 - startTime1));

        // Test 2
        long startTime2 = System.currentTimeMillis();
        LayerTest layerTester = new LayerTest(3, 10, 2, 1, 784, 10, .01f, new LeakyReLu(), 15, 1, true, 100);
        layerTester.test();

        long endTime2 = System.currentTimeMillis();
        System.out.println("Test2 done");
        System.out.println("Time elapsed: " + (endTime2 - startTime2));


        // Test 3
        long startTime3 = System.currentTimeMillis();
        NeuronTest neuronTester = new NeuronTest(3, 30, 1, 2, 784, 10, .05f, new LeakyReLu(), 3, 1, true, 100);
        neuronTester.test();

        long endTime3 = System.currentTimeMillis();
        System.out.println("Test3 done");
        System.out.println("Time elapsed: " + (endTime3 - startTime3));
        */
        Program program = new Program();
        program.init();
    }
}
