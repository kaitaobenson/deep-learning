package Activation;


public class Sigmoid implements IActivationFunction {

    @Override
    public float output(float x) {
        return (float) (1 / (1 + Math.exp(-x)));
    }

    @Override
    public float outputDerivative(float x) {
        return (float) (x * (1 - x));
    }
}
