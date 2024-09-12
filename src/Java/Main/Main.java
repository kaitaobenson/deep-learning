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
        
        
        Neuron neuron = new Neuron();
        neuron.randomizeWeights(784);
        neuron.randomizeBias();
        
        //double output = neuron.computeOutput(digitImage1.getPixels());
        System.out.println("Bias: " + neuron.getBias());
        System.out.println("Weight0: " + neuron.getWeights()[0]);
        System.out.println("Weight1: " + neuron.getWeights()[1]);
        System.out.println("Weight2: " + neuron.getWeights()[2]);
        System.out.println("Weight3: " + neuron.getWeights()[3]);
        //System.out.println("Output: " + output);
        
        
        System.out.println(Util.sigmoid(-1));
        
    }
}
