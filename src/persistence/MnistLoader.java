package persistence;

import data.DataSet;
import data.Digit;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class MnistLoader {

    private static final String TRAINING_DATA_PATH = "../MNIST_CSV/mnist_train.csv";
    private static final String TESTING_DATA_PATH = "../MNIST_CSV/mnist_test.csv";

    // Load training digits
    public DataSet getTrainingDigits() {
        DataSet dataset = parseDataSetFromCsv(TRAINING_DATA_PATH);
        assert dataset != null;
        System.out.println("Training Digits Loaded: " + dataset.getSize());
        return dataset;
    }

    // Load testing digits
    public DataSet getTestingDigits() {
        DataSet dataset = parseDataSetFromCsv(TESTING_DATA_PATH);
        assert dataset != null;
        System.out.println("Testing Digits Loaded: " + dataset.getSize());
        return dataset;
    }

    // Get a digit from an image
    public Digit parseDigitFromPng(String path) {
        File file = new File(path);

        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("ERROR: Failed to load PNG file from: " + path);
            return null;
        }

        assert(img.getHeight() == 28 && img.getWidth() == 28);

        float[] pixels = new float[784];  // 28*28

        for (int y = 0; y < 28; y++) {
            for (int x = 0; x < 28; x++) {
                int rgb = img.getRGB(x, y);

                // Convert to float normalized 0-1
                float alpha = ((rgb >> 24) & 0xFF) / 255.0f;
                float red = ((rgb >> 16) & 0xFF) / 255.0f;
                float green = ((rgb >> 8) & 0xFF) / 255.0f;
                float blue = ((rgb) & 0xFF) / 255.0f;

                float grayscale = (0.299f * red + 0.587f * green + 0.114f * blue) * alpha;

                pixels[x + y*28] = grayscale;
            }
        }

        return new Digit(pixels, 0);  // No label
    }


    public void saveDigitAsPng(Digit digit, String path) {
        BufferedImage bufferedImage = new BufferedImage(28, 28, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < 28; y++) {
            for (int x = 0; x < 28; x++) {
                float value = digit.getPixel(x, y);
                int gray = (int) (value * 255); // Scale 0-1 to 0-255

                // Convert grayscale to heatmap color
                int rgb = getHeatmapColor(gray);

                bufferedImage.setRGB(x, y, rgb);
            }
        }

        try {
            ImageIO.write(bufferedImage, "png", new File(path));
        } catch (IOException e) {
            System.out.println("ERROR: Failed to write PNG");
        }
    }

    private int getHeatmapColor(int value) {
        // Normalize value between 0 and 1
        float normalized = value / 255.0f;

        int r, g, b;
        if (normalized < 0.5) {
            // Interpolate from Blue (0,0,255) to White (255,255,255)
            float ratio = normalized * 2; // Scale to 0-1 range
            r = (int) (ratio * 255);
            g = (int) (ratio * 255);
            b = 255;
        } else {
            // Interpolate from White (255,255,255) to Red (255,0,0)
            float ratio = (normalized - 0.5f) * 2; // Scale to 0-1 range
            r = 255;
            g = (int) ((1 - ratio) * 255);
            b = (int) ((1 - ratio) * 255);
        }

        return (r << 16) | (g << 8) | b;
    }


    // Get digits from CSV
    private DataSet parseDataSetFromCsv(String path) {
        File file = new File(path);
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (IOException e) {
            System.out.println("ERROR: Failed to load CSV file from: " + path);
            return null;
        }

        DataSet dataSet = new DataSet(1000);

        // Parse each line
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            int[] values = stringArrayToIntArray(line.split(","));

            int label = values[0];
            float[] pixels = new float[784];

            for (int i = 0; i < pixels.length; i++) {
                pixels[i] = (float) (values[i+1] / 255.0);
            }

            Digit digit = new Digit(pixels, label);

            dataSet.data.add(digit);
        }

        return dataSet;
    }

    private int[] stringArrayToIntArray(String[] array) {
        return Arrays.stream(array).mapToInt(Integer::parseInt).toArray();
    }
}