package Persistance;

import Digit.Digit;
import Digit.DigitContainer;
import Util.Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class MnistLoader {

    private static final int DIGIT_AMOUNT = 1_000_000;
    private static final String TRAINING_DATA_PATH = "../MNIST_CSV/mnist_train.csv";
    private static final String TESTING_DATA_PATH = "../MNIST_CSV/mnist_test.csv";

    public MnistLoader() {}

    // Load and return training digits
    public DigitContainer getTrainingDigits() {
        DigitContainer digitContainer = loadFileIntoDigits(TRAINING_DATA_PATH, DIGIT_AMOUNT);
        System.out.println("Training Digits Loaded: " + digitContainer.getDigitAmount());
        return digitContainer;
    }

    // Load and return testing digits
    public DigitContainer getTestingDigits() {
        DigitContainer digitContainer = loadFileIntoDigits(TESTING_DATA_PATH, DIGIT_AMOUNT);
        System.out.println("Testing Digits Loaded: " + digitContainer.getDigitAmount());
        return digitContainer;
    }

    // Load digits from the CSV and return a DigitContainer
    private DigitContainer loadFileIntoDigits(String path, int digitAmount) {
        DigitContainer digitContainer = new DigitContainer();
        int digitCounter = digitAmount;
        Scanner scanner;

        try {
            scanner = getFileScanner(path);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }

        // Get each line from the file
        while (scanner.hasNextLine() && digitCounter > 0) {
            String[] stringPixelArray = scanner.nextLine().split(",");
            int[] intPixelArrayWithLabel = Util.stringArrayToIntArray(stringPixelArray);

            int label = intPixelArrayWithLabel[0];
            int[] intPixelArray = Arrays.copyOfRange(intPixelArrayWithLabel, 1, intPixelArrayWithLabel.length);
            float[] floatPixelArray = normalizePixels(intPixelArray);

            // Create the digit
            Digit digit = new Digit();
            digit.setLabel(label);
            digit.setPixels(floatPixelArray);
            digitContainer.addDigit(digit);

            digitCounter--;
        }

        scanner.close();
        return digitContainer;
    }

    // Normalize pixel values to the range [0, 1]
    private float[] normalizePixels(int[] pixelArray) {
        float[] normalizedPixels = new float[pixelArray.length];
        for (int i = 0; i < pixelArray.length; i++) {
            normalizedPixels[i] = pixelArray[i] / 255.0f;
        }
        return normalizedPixels;
    }

    // Get a scanner for the specified file
    private Scanner getFileScanner(String path) throws FileNotFoundException {
        File file = new File(path);
        try {
            return new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Data from " + file.getAbsolutePath() + " could not be loaded.");
        }
    }
}