package persistence;

import data.DataSet;
import data.Digit;
import util.GeneralUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;

public class MnistLoader {

    private static final String TRAINING_DATA_PATH = "../MNIST_CSV/mnist_train.csv";
    private static final String TESTING_DATA_PATH = "../MNIST_CSV/mnist_test.csv";

    // Load and return training digits
    public DataSet getTrainingDigits() {
        DataSet dataset = parseDataSetFromCsv(TRAINING_DATA_PATH);
        assert dataset != null;
        System.out.println("Training Digits Loaded: " + dataset.getSampleAmount());
        return dataset;
    }

    // Load and return testing digits
    public DataSet getTestingDigits() {
        DataSet dataset = parseDataSetFromCsv(TESTING_DATA_PATH);
        assert dataset != null;
        System.out.println("Testing Digits Loaded: " + dataset.getSampleAmount());
        return dataset;
    }

    public Digit parseDigitFromPng(String path) {
        File file = new File(path);

        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("Error loading PNG file from: " + path);
            return null;
        }

        assert(img.getHeight() == 28 && img.getWidth() == 28);

        float[] pixels = new float[784];  // 28*28

        for (int y = 0; y < 28; y++) {
            for (int x = 0; x < 28; x++) {
                int rgb = img.getRGB(x, y);

                // Convert to float normalized + flipped (black = 1, white = 0)
                float alpha = ((rgb >> 24) & 0xFF) / 255.0f;
                float red = ((rgb >> 16) & 0xFF) / 255.0f;
                float green = ((rgb >> 8) & 0xFF) / 255.0f;
                float blue = ((rgb) & 0xFF) / 255.0f;

                float grayscale = (1 - (0.299f * red + 0.587f * green + 0.114f * blue)) * alpha;

                pixels[x + y*28] = grayscale;
            }
        }

        return new Digit(pixels, 0);  // No label
    }


    private DataSet parseDataSetFromCsv(String path) {
        File file = new File(path);
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (IOException e) {
            System.out.println("Error loading CSV file from: " + path);
            return null;
        }

        DataSet dataSet = new DataSet(1000);

        // Parse each line
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            int[] values = GeneralUtil.stringArrayToIntArray(line.split(","));

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
}