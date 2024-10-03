package Digit;

import java.io.Serializable;
import java.util.ArrayList;

public class DigitContainer implements Serializable {
	
	private final ArrayList<Digit> digits = new ArrayList<Digit>();
	
	public DigitContainer() {}

	// Setters / Getters
	public void addDigit(Digit digit) {
		digits.add(digit);
	}

	public Digit getDigit(int index) {
		if (index > digits.size()) {
			throw new IllegalArgumentException("Index out of bounds");
		}
		return digits.get(index);
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
