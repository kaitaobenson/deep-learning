package Java.Digit;

import java.util.ArrayList;

// Used for storing a group of handwritten Java.Digit.Java.Digit images.

public class DigitContainer {
	
	private ArrayList<Digit> digits = new ArrayList<Digit>();
	
	public DigitContainer() {
		
	}
	
	
	public void addDigit(Digit digit) {
		digits.add(digit);
	}
	
	public Digit getDigit(int idx) {
		if (idx > digits.size()) {
			throw new IllegalArgumentException("Index out of bounds");
		}
		return digits.get(idx);
	}
	
	public Digit[] getDigits() {
		Digit[] digitArray = new Digit[digits.size()];
		for (int i = 0; i < digits.size(); i++) {
			digitArray[i] = digits.get(i);
		}
		return digitArray;
	}

	public int getDigitAmount() {
		return digits.size();
	}
	
}
