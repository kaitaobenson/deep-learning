package Java.Digit;// Used for a single image of a handwritten digit
// Stored as an array of pixels from 0 to 255 in brightness.

public class Digit {
	
	// Characters to display digit (dark to bright)
	private final String[] dispChars = {
		" ", ".", "`", ",", "-", "~", "+", ":", ";", "=", "*", "#", "%", "@", "█"
	};

	// MNIST data set images are 28x28 pixels
	private final int IMAGE_WIDTH = 28;
	private final int IMAGE_HEIGHT = 28;
	
	// Resulting in 784 pixels per image
	private final int PIXELS_AMOUNT = IMAGE_WIDTH * IMAGE_HEIGHT;
	
	
    private int[] pixels = new int[PIXELS_AMOUNT];
    private int label;
    
    
    public Digit() {
    	
    }
    
    
    // Pixel array setter / getter
    public void setPixels(int[] pixels) {
        if (pixels.length != PIXELS_AMOUNT) {
            throw new IllegalArgumentException("Pixels array must have " + PIXELS_AMOUNT + " elements");
        }
        this.pixels = pixels.clone();
    }
    
    public int[] getPixels() {
        return this.pixels.clone();
    }
    
    
    // Single pixel getter
    public int getPixel(int x, int y) {
    	int min_row = y * IMAGE_WIDTH;
    	return pixels[min_row + x];
    	
    }
    
    public int getPixel(int index) {
    	return pixels[index];
    }
    
    
    // Java.Digit.Java.Digit value setter / getter
    public void setLabel(int label) {
    	this.label = label;
    }
    
    public int getLabel() {
    	return label;
    }
    
    
    // Display number onto screen
    public String toString() {
    	StringBuilder stringBuilder = new StringBuilder();
    	
    	stringBuilder.append(label);
    	stringBuilder.append("--------------------------------");
    	
    	for (int j = 0; j < IMAGE_HEIGHT; j++) {
    		for (int i = 0; i < IMAGE_WIDTH; i++) {
    			int pixel = getPixel(i, j);
    			stringBuilder.append(getDispChar(pixel));
    		}
    		stringBuilder.append("\n");
    	}

    	stringBuilder.append("--------------------------------");
    	
    	return stringBuilder.toString();
    }
    
    private String getDispChar(int value) {
        // Map the brightness value (0-255) to an index in the dispChars array
        int index = (int) Math.floor((value / 256.0) * dispChars.length);
        
        // Ensure the index is within bounds
        if (index < 0) {
            index = 0;
        } else if (index >= dispChars.length) {
            index = dispChars.length - 1;
        }
        
        return dispChars[index];
    }

}