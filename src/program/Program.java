package program;

import activation.LeakyReLu;
import data.DataSample;
import data.Digit;
import data.DataSet;
import network.Neuron;
import network.output.OutputAllData;
import network.output.OutputSingleData;
import persistence.MnistLoader;
import network.NeuronLayer;
import network.NeuronModel;
import persistence.NeuronModelLoader;

import java.io.IOException;
import java.util.Scanner;

public class Program {

    public DataSet testingDigitContainer;
    public DataSet trainingDigitContainer;

    public static float learningRate = .01f;
    public static boolean miniBatch = true;
    public static int batchSize = 100;

    public NeuronLayer[] neuronLayers = {
            NeuronLayer.createInputLayer(new float[784]),
            NeuronLayer.createHiddenLayer(784, 20, new LeakyReLu()),
            //NeuronLayer.createHiddenLayer(20, 20, new LeakyReLu()),
            //NeuronLayer.createHiddenLayer(20, 20, new LeakyReLu()),
            //NeuronLayer.createHiddenLayer(20, 20, new LeakyReLu()),
            NeuronLayer.createHiddenLayer(20, 10, new LeakyReLu()),
    };
    public NeuronModel neuronModel = new NeuronModel(neuronLayers, miniBatch, batchSize, learningRate);

    public NeuronModelLoader neuronModelLoader = new NeuronModelLoader();


    public void init() {
        System.out.println("Program started...");

        MnistLoader mnistLoader = new MnistLoader();
        testingDigitContainer = mnistLoader.getTestingDigits();
        trainingDigitContainer = mnistLoader.getTrainingDigits();

        CommandParser commandParser = new CommandParser();
        commandParser.printCommands();

        Scanner scanner = new Scanner(System.in);

        programLoop:
        while (true) {
            String input = scanner.nextLine();

            Command command;

            try {
                command = commandParser.parseCommand(input);
            } catch (Exception e) {
                System.out.println("Invalid command.");
                System.out.println(e.getMessage());
                continue;
            }

            String name = command.name;
            Command.InputType inputType = command.inputType;
            Object data = command.getData();

            switch (name) {
                case "train":
                    train(trainingDigitContainer, (int) data);
                    break;
                case "test":
                    if (inputType == Command.InputType.INT) {
                        int index = (int) data;
                        test(testingDigitContainer.getSample(index));
                    } else {
                        test(testingDigitContainer);
                    }
                    break;
                case "test-png":
                    String path = (String) data;
                    test(path);
                    break;
                case "print-train-digit":
                    printDigit(trainingDigitContainer, (int) data);
                    break;
                case "print-test-digit":
                    printDigit(testingDigitContainer, (int) data);
                    break;
                case "save-model":
                    saveNeuronModel((String) data);
                    break;
                case "load-model":
                    loadNeuronModel((String) data);
                    break;
                case "random-model":
                    randomizeNeuronModel();
                    break;
                case "help":
                    commandParser.printCommands();
                    break;
                case "quit":
                    System.out.println("Quitting...");
                    break programLoop;
            }
        }

        scanner.close();
        System.exit(0);
    }

    public void train(DataSet container, int epochs) {
        // Validate
        if (epochs < 0){
            System.out.println("Amount of epochs cannot be negative");
            return;
        }

        // Display
        System.out.println("Starting trainer for " + epochs + " epochs...");
        neuronModel.trainModel(container, epochs);

        System.out.println("Finished training");
    }

    public void test(DataSet dataSet) {
        // Display
        System.out.println("Starting tester...");

        OutputAllData outputData = neuronModel.testAll(dataSet);

        System.out.println(outputData);
    }

    public void test(DataSample dataSample) {
        // Display
        System.out.println(dataSample);

        OutputSingleData outputData = neuronModel.testSingle(dataSample);
        System.out.println(outputData);
    }

    public void test(String path) {
        MnistLoader mnistLoader = new MnistLoader();
        Digit digit = mnistLoader.parseDigitFromPng(path);

        test(digit);
        visualizeNeurons(neuronModel.layers[1].neurons);
    }

    public void printDigit(DataSet dataSet, int index) {
        // Validate
        if (index > dataSet.getSize() || index < 0) {
            System.out.println("Index not found");
            return;
        }
        // Display
        System.out.println("Digit at index: " + index);
        Digit digit = (Digit) dataSet.getSample(index); //TODO: Fix casting ??
        System.out.println(digit);
    }

    public void saveNeuronModel(String fileName) {
        try {
            neuronModelLoader.save(neuronModel, fileName);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadNeuronModel(String fileName) {
        try {
            neuronModel = neuronModelLoader.load(fileName);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Unexpected error happened: " + e.getMessage());
        }
    }

    public void visualizeNeurons(Neuron[] neurons) {
        for (int i = 0; i < neurons.length; i++) {
            MnistLoader mnistLoader = new MnistLoader();

            Digit digit = new Digit(neurons[i].weights, 0);

            System.out.println("Saved Neuron: " + i);
            mnistLoader.saveDigitAsPng(digit, "src/persistence/saved_models/neuron" + i + ".png");
        }
    }

    public void randomizeNeuronModel() {
        neuronModel.randomize();
        System.out.println("Model randomized");
    }
}
