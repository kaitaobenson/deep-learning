package Java.Activation;

public class Swish implements IActivationFunction {

    @Override
    public float output(float x) {
        return (float) (x * (1 / (1 + Math.exp(-x))));
    }

    @Override
    public float outputDerivative(float x) {
        return (float) (((1 + Math.exp(-x)) + x * Math.exp(-x)) / Math.pow(1 + Math.exp(-x), 2));
    }
}
