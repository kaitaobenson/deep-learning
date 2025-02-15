package activation;

public class LeakyReLu implements IActivationFunction {

    @Override
    public float output(float x) {
        return (float) (x >= 0 ? x : x * 0.01);
    }

    @Override
    public float outputDerivative(float x) {
        return (float) (x >= 0 ? 1 : 0.01);
    }
}
