package Java.Activation;

public enum ActivationFunctionType {
    SIGMOID(0), TANH(1), SWISH(2), LEAKY_RELU(3);

    //TODO: How do I get rid of this and use the activationFUnctionType instead
    private int type = 0;

    private ActivationFunctionType(int type) {
        this.type = type;
    }

    public IActivationFunction getActivationFunction() {
        switch (type) {
            case 0:
                return new Sigmoid();
            case 1:
                return new Tanh();
            case 2:
                return new Swish();
            case 3:
                return new LeakyReLu();
        }
        return new Sigmoid();
    }
}
