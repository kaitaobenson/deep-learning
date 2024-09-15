package Java.ProgramFlow;

import Java.Digit.Digit;
import Java.Digit.DigitContainer;
import Java.Digit.MnistLoader;

import java.util.Scanner;

public class Program {

    public DigitContainer testingDigitContainer;
    public DigitContainer trainingDigitContainer;

    public Program() {}

    public void init() {
        System.out.println("Program started...");

        MnistLoader mnistLoader = new MnistLoader();
        testingDigitContainer = mnistLoader.getTestingDigits();
        trainingDigitContainer = mnistLoader.getTrainingDigits();

        CommandManager commandManager = new CommandManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            commandManager.doCommand(input);
        }
    }

    public void train(DigitContainer trainingDigitContainer) {
        // No-op
        System.out.println("Starting trainer...");
    }

    public void test(DigitContainer testingDigitContainer) {
        // No-op
        System.out.println("Starting tester...");
    }

    public void test(Digit testingDigit) {
        // No-op
        System.out.println("Starting test...");
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
}
