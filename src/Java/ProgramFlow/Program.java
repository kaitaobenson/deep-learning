package Java.ProgramFlow;

import Java.Digit.Digit;
import Java.Digit.DigitContainer;
import Java.Digit.MnistLoader;
import java.util.Scanner;

public class Program {

    public DigitContainer testingDigitContainer;
    public DigitContainer trainingDigitContainer;

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

            switch (command.getName()) {
                case "train":
                    train(trainingDigitContainer);
                    break;
                case "test":
                    test(testingDigitContainer);
                    break;
                case "print-train-digit":
                    printTrainingDigit(command.getData());
                    break;
                case "print-test-digit":
                    printTestingDigit(command.getData());
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
