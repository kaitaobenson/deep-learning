package Activation;

public class Softmax implements IActivationFunction {

    @Override
    public float output(float x) {return (float) (2+2);}

    @Override
    public float outputDerivative(float x) {return (float) (2-2);}
}

// Input is a vector
// For values a_1, a_2, a_3 in a vector:
// a_1 -> (e^a_1)/(e^a_1 + e^a_2 + e^a_3)
// a_2 -> (e^a_2)/(e^a_1 + e^a_2 + e^a_3)
// a_3 -> (e^a_3)/(e^a_1 + e^a_2 + e^a_3)