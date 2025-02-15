package ProgramFlow;

import activation.ActivationFunctionType;
import data.Digit;
import data.DigitContainer;
import model.OutputData;
import persistence.MnistLoader;
import layer.NeuronLayer;
import model.NeuronModel;
import persistence.NeuronLoader;

import java.io.IOException;
import java.util.Scanner;

public class Program {

    public DigitContainer testingDigitContainer;
    public DigitContainer trainingDigitContainer;

    public NeuronLayer[] neuronLayers = {
            new NeuronLayer(20, 784, ActivationFunctionType.LEAKY_RELU.getActivationFunction()),
            new NeuronLayer(20, 20, ActivationFunctionType.LEAKY_RELU.getActivationFunction()),
            new NeuronLayer(20, 20, ActivationFunctionType.LEAKY_RELU.getActivationFunction()),
            new NeuronLayer(10, 20, ActivationFunctionType.SIGMOID.getActivationFunction()),
    };
    public NeuronModel neuronModel = new NeuronModel(neuronLayers);

    public NeuronLoader neuronLoader = new NeuronLoader();


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

            String name = command.getName();
            Command.InputType inputType = command.getInputType();
            Object data = command.getData();

            switch (name) {
                case "train":
                    train(trainingDigitContainer, (int) data);
                    break;
                case "test":
                    if (inputType == Command.InputType.INT) {
                        int index = (int) data;
                        test(testingDigitContainer, index);
                    } else {
                        test(testingDigitContainer);
                    }
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

    public void train(DigitContainer container, int epochs) {
        // Validate
        if (epochs < 0){
            System.out.println("Amount of epochs cannot be negative");
            return;
        }

        // Display
        System.out.println("Starting trainer for " + epochs + " epochs...");
        neuronModel.train(container, epochs);
        System.out.println("Finished training");
    }

    public void test(DigitContainer container) {
        // Display
        System.out.println("Starting tester...");

        int totalGuesses = container.getDigitAmount();
        int correctGuesses = neuronModel.feedforwardAll(container);

        System.out.println("Correct guesses / total guesses:");
        System.out.println(correctGuesses + " / " + totalGuesses);
    }

    public void test(DigitContainer container, int index) {
        // Validate
        if (index > container.getDigitAmount() || index < 0) {
            System.out.println("Index not found");
            return;
        }
        // Display
        System.out.println("Starting test on digit " + index);
        System.out.println(container.getDigit(index));

        OutputData outputData = neuronModel.feedforward(container.getDigit(index));
        System.out.println(outputData);
    }

    public void printDigit(DigitContainer container, int index) {
        // Validate
        if (index > container.getDigitAmount() || index < 0) {
            System.out.println("Index not found");
            return;
        }
        // Display
        System.out.println("Digit at index: " + index);
        Digit digit = container.getDigit(index);
        System.out.println(digit);
    }

    public void saveNeuronModel(String fileName) {
        try {
            neuronLoader.saveNeuronModel(neuronModel, fileName);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadNeuronModel(String fileName) {
        try {
            neuronModel = neuronLoader.loadNeuronModel(fileName);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Unexpected error happened: " + e.getMessage());
        }
    }

    public void randomizeNeuronModel() {
        neuronModel.randomizeModel();
        System.out.println("Model randomized");
    }
}
