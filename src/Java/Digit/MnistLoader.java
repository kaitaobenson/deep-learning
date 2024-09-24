package Java.Digit;

import Java.Util.Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class MnistLoader {

    // Load all of them for now
    private static final int DIGIT_AMOUNT = 1000000;

    private static final String TRAINING_DATA_PATH = "../MNIST_CSV/mnist_train.csv";
    private static final String TESTING_DATA_PATH = "../MNIST_CSV/mnist_test.csv";

    public MnistLoader() {}

    public DigitContainer getTrainingDigits() {
        DigitContainer digitContainer = loadFileIntoDigits(TRAINING_DATA_PATH, DIGIT_AMOUNT);
        System.out.println("Training Digits Loaded: " + digitContainer.getDigitAmount());
        return digitContainer;
    }

    public DigitContainer getTestingDigits() {
        DigitContainer digitContainer = loadFileIntoDigits(TESTING_DATA_PATH, DIGIT_AMOUNT);
        System.out.println("Testing Digits Loaded: " + digitContainer.getDigitAmount());
        return digitContainer;
    }

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

        while (scanner.hasNextLine() && digitCounter > 0) {
            String line = scanner.nextLine();
            String[] stringPixelArray = line.split(",");

            // First value is the label
            int[] intPixelArrayWithLabel = Util.stringArrayToIntArray(stringPixelArray);
            int[] intPixelArray = Arrays.copyOfRange(intPixelArrayWithLabel, 1, intPixelArrayWithLabel.length);
            int label = intPixelArrayWithLabel[0];

            Digit digit = new Digit();
            digit.setLabel(label);

            float[] floatPixelArray = new float[intPixelArray.length];

            for (int i = 0; i < intPixelArray.length; i++) {
                floatPixelArray[i] = intPixelArray[i] / 255.0f;  // Normalize to range [0, 1]
            }

            digit.setPixels(floatPixelArray);  // Assign the normalized pixel array
            digitContainer.addDigit(digit);
            digitCounter--;
        }

        scanner.close();

        return digitContainer;
    }

    private Scanner getFileScanner(String path) throws FileNotFoundException {
        Scanner scanner;
        File file = new File(path);

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Data from " + file.getAbsolutePath() + " could not be loaded.");
        }

        return scanner;
    }

}
