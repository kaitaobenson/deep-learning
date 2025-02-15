package data;

import java.io.Serializable;

public class LabeledImage implements Serializable {

    private static final String[] dispChars = {
            " ", ".", "`", ",", "-", "~", "+", ":", ";", "=", "*", "#", "%", "@", "█"
    };

    public final int width;
    public final int height;
    public int label;

    public final float[] pixels;

    public LabeledImage(int width, int height, int label, float[] pixels) {
        this.width = width;
        this.height = height;
        this.label = label;

        if (pixels.length != width * height) {
            System.err.println("Pixels array length does not match width and height");
        }
        this.pixels = pixels;
    }

    public void setPixel(int x, int y, float pixel) {
        int index = y * width + x;
        this.pixels[index] = pixel;
    }

    public float getPixel(int x, int y) {
        int index = y * width + x;
        return pixels[index];
    }

    public float[] getPixels() {
        return pixels;
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
