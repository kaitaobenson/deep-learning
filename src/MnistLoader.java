import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class MnistLoader {

    private static final String TRAINING_DATA_PATH = "MNIST_CSV/mnist_train.csv";
    private static final String TESTING_DATA_PATH = "MNIST_CSV/mnist_test.csv";

    public MnistLoader() {}

    public DigitContainer getTrainingDigits() {
        return getDigits(TRAINING_DATA_PATH, 1000);
    }

    public DigitContainer getTestingDigits() {
        return getDigits(TESTING_DATA_PATH, 1000);
    }


    // File data loader
    public DigitContainer getDigits(String path, int amount) {
        DigitContainer digitContainer = new DigitContainer();

        File file = new File(path);
        Scanner scanner;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Data from " + path + " could not be loaded.");
            return null;
        }

        int amountScanned = 0;

        while (scanner.hasNextLine() && amountScanned <= amount) {
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

                amountScanned++;
        }

            scanner.close();
            System.out.println("Data from " + path + " successfully loaded.");

        return digitContainer;
    }


}
