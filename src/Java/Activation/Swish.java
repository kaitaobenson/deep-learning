package Java.Activation;

public class Swish implements IActivationFunction {

    @Override
    public double output(double x) {
        return x * (1 / (1 + Math.exp(-x)));
    }

    @Override
    public double outputDerivative(double x) {
        return ((1 + Math.exp(-x)) + x * Math.exp(-x)) / Math.pow(1 + Math.exp(-x), 2);
    }
}
