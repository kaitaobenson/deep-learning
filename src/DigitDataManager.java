import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class DigitDataManager {

    private static final int DIGIT_AMOUNT = 1000;
    private static final String TRAINING_DATA_PATH = "src/MNIST_CSV/mnist_train.csv";
    private static final String TESTING_DATA_PATH = "src/MNIST_CSV/mnist_test.csv";

    public DigitDataManager() {}

    public DigitContainer getTrainingDigits() {
        return loadFileIntoDigits(TRAINING_DATA_PATH, DIGIT_AMOUNT);
    }

    public DigitContainer getTestingDigits() {
        return loadFileIntoDigits(TESTING_DATA_PATH, DIGIT_AMOUNT);
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
            digit.setPixels(intPixelArray);  // This should be 784 elements long

            digitContainer.addDigit(digit);
            digitCounter--;
        }

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
