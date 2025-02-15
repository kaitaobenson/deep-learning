package data;

import java.io.Serializable;

public class Digit implements DataSample, Serializable {

    private static final String[] dispChars = {
            " ", ".", "`", ",", "-", "~", "+", ":", ";", "=", "*", "#", "%", "@", "█"
    };

    public static final int width = 28;
    public static final int height = 28;

    public float[] pixels;
    public int label;

    public Digit(float[] pixels, int label) {
        // Pixels array must match width & height
        assert(pixels.length == width * height);
        // Label must be 0-9
        assert(label >= 0 && label <= 9 );

        this.pixels = pixels;
        this.label = label;
    }

    public float getPixel(int x, int y) {
        int index = y * width + x;
        return pixels[index];
    }

    public float[] getPixels() {
        return pixels;
    }

    @Override
    public float[] getInputs() {
        return getPixels();
    }

    @Override
    public float[] getTargets() {
        float[] targets = new float[10];
        // Labeled number should be 1, all other numbers 0
        targets[label] = 1.0f;

        return targets;
    }


    // Display image in ASCII
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                float pixel = getPixel(i, j);
                stringBuilder.append(getDispChar(pixel));
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    private String getDispChar(float value) {
        int index = Math.round(value * (dispChars.length - 1));

        index = Math.max(0, Math.min(index, dispChars.length - 1));

        return dispChars[index];
    }
}
