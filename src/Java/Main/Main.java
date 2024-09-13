package Java.Main;

import Java.Digit.Digit;
import Java.Digit.DigitContainer;
import Java.Digit.MnistLoader;
import Java.Neuron.Neuron;

public class Main {

    public static void main(String[] args) {
        MnistLoader mnistLoader = new MnistLoader();
        
        DigitContainer trainingDigitContainer = mnistLoader.getTrainingDigits();
        
        Digit digitImage1 = trainingDigitContainer.getDigit(1);
        System.out.println(digitImage1);
        
        Digit digitImage2 = trainingDigitContainer.getDigit(2);
        System.out.println(digitImage2);
        
        Digit digitImage3 = trainingDigitContainer.getDigit(3);
        System.out.println(digitImage3);
        
        

        
    }
}
