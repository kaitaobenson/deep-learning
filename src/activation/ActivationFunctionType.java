package activation;

public enum ActivationFunctionType {
    SIGMOID(new Sigmoid()),
    TANH(new Tanh()),
    SWISH(new Swish()),
    LEAKY_RELU(new LeakyReLu());

    private final IActivationFunction function;

    ActivationFunctionType(IActivationFunction function) {
        this.function = function;
    }

    public IActivationFunction getActivationFunction() {
        return function;
    }

}
