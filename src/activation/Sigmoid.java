package Activation;


public class Sigmoid implements IActivationFunction {

    @Override
    public float output(float x) {
        return (float) (1 / (1 + Math.exp(-x)));
    }

    @Override
    public float outputDerivative(float x) {
        return (float) (output(x) * (1 - output(x)));
    }
}
