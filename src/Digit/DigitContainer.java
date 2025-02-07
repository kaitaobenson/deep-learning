package Digit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		ArrayList<Digit> shuffledDigits = digits;
		Collections.shuffle(shuffledDigits);

		Digit[] digitArray = new Digit[shuffledDigits.size()];
		for (int i = 0; i < shuffledDigits.size(); i++) {
			digitArray[i] = shuffledDigits.get(i);
		}
		return digitArray;
	}
	public int getDigitAmount() {
		return digits.size();
	}
}
