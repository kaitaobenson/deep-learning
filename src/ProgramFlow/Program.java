package ProgramFlow;

import Activation.ActivationFunctionType;
import Digit.Digit;
import Digit.DigitContainer;
import Persistance.MnistLoader;
import Neuron.NeuronLayer;
import Neuron.NeuronModel;
import Persistance.NeuronLoader;

import java.io.IOException;
import java.util.Scanner;

public class Program {

    public DigitContainer testingDigitContainer;
    public DigitContainer trainingDigitContainer;

    public NeuronLayer[] neuronLayers = {
            new NeuronLayer(16, 784, ActivationFunctionType.LEAKY_RELU),
            new NeuronLayer(16, 16, ActivationFunctionType.LEAKY_RELU),
            new NeuronLayer(10, 16, ActivationFunctionType.SIGMOID),
    };
    public NeuronModel neuronModel = new NeuronModel(neuronLayers);

    public NeuronLoader neuronLoader = new NeuronLoader();

    public Program() {}

    //TODO: Geeze this command stuff is pretty silly
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
                    train(trainingDigitContainer);
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
                    printTrainingDigit((int) data);
                    break;
                case "print-test-digit":
                    printTestingDigit((int) data);
                    break;
                case "save-model":
                    saveNeuronModel((String) data);
                    break;
                case "load-model":
                    loadNeuronModel((String) data);
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

    public void train(DigitContainer trainingDigitContainer) {
        // No-op
        System.out.println("Starting trainer...");
    }

    public void test(DigitContainer testingDigitContainer) {
        // No-op
        System.out.println("Starting tester...");
    }

    public void test(DigitContainer testingDigitContainer, int index) {
        System.out.println("Starting test on digit " + index);
        System.out.print("\n");

        neuronModel.inputDigit(testingDigitContainer.getDigit(index));

        System.out.println("Done");
        System.out.print("\n");
    }

    public void printTrainingDigit(int index) {
        // Validate
        if (index > trainingDigitContainer.getDigitAmount() || index < 0) {
            System.out.println("Index not found");
        }
        // Display
        System.out.println("Train digit at index: " + index);
        Digit digit = trainingDigitContainer.getDigit(index);
        System.out.println(digit);
    }

    public void printTestingDigit(int index) {
        // Validate
        if (index > testingDigitContainer.getDigitAmount() || index < 0) {
            System.out.println("Index not found");
        }
        // Display
        System.out.println("Testing digit at index: " + index);
        Digit digit = testingDigitContainer.getDigit(index);
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
}