package Digit;

import Util.GeneralUtil;

import java.io.Serializable;

public class Digit implements Serializable {

    private static final int IMAGE_WIDTH = 28;
    private static final int IMAGE_HEIGHT = 28;
    private static final int PIXELS_AMOUNT = IMAGE_WIDTH * IMAGE_HEIGHT;

    private static final String[] dispChars = {
            " ", ".", "`", ",", "-", "~", "+", ":", ";", "=", "*", "#", "%", "@", "█"
    };

    private float[] pixels = new float[PIXELS_AMOUNT];
    private int label;

    public Digit() {}

    // Display number onto screen
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(GeneralUtil.lineBreak).append(label).append("\n");

        for (int j = 0; j < IMAGE_HEIGHT; j++) {
            for (int i = 0; i < IMAGE_WIDTH; i++) {
                float pixel = getPixel(i, j);
                stringBuilder.append(getDispChar(pixel));
            }
            stringBuilder.append("\n");
        }

        stringBuilder.append(GeneralUtil.lineBreak);
        return stringBuilder.toString();
    }

    private String getDispChar(float value) {
        // Use Math.round to round the result to the nearest integer
        int index = Math.round(value * (dispChars.length - 1));

        // Ensure the index is within bounds
        index = Math.max(0, Math.min(index, dispChars.length - 1));

        return dispChars[index];
    }

    // Setters / Getters
    public void setPixels(float[] pixels) {
        if (pixels.length != PIXELS_AMOUNT) {
            throw new IllegalArgumentException("Pixels array must have " + PIXELS_AMOUNT + " elements");
        }
        this.pixels = pixels.clone();
    }
    public float[] getPixels() {
        return this.pixels.clone();
    }
    public float getPixel(int x, int y) {
        int index = y * IMAGE_WIDTH + x;
        return pixels[index];
    }
    public float getPixel(int index) {
        return pixels[index];
    }

    public void setLabel(int label) {
        this.label = label;
    }
    public int getLabel() {
        return label;
    }
}
