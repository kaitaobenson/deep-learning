package program;

public class Command {

    public enum InputType {
        VOID, STRING, INT, FLOAT, BOOLEAN;
    }

    public final String name;
    public final InputType inputType;

    private String stringData;
    private int intData;
    private float floatData;
    private boolean booleanData;

    public Command(String name, InputType inputType) {
        this.name = name;
        this.inputType = inputType;
    }

    // STRING
    public void setData(String data) {
        if (inputType != InputType.STRING) {
            throw new IllegalStateException("InputType was not STRING");
        }
        stringData = data;
    }
    // INT
    public void setData(int data) {
        if (inputType != InputType.INT) {
            throw new IllegalStateException("InputType was not INT");
        }
        intData = data;
    }
    // FLOAT
    public void setData(float data) {
        if (inputType != InputType.FLOAT) {
            throw new IllegalStateException("InputType was not FLOAT");
        }
        floatData = data;
    }
    // BOOLEAN
    public void setData(boolean data) {
        if (inputType != InputType.BOOLEAN) {
            throw new IllegalStateException("InputType was not BOOLEAN");
        }
        booleanData = data;
    }

    public Object getData() {
        return switch (inputType) {
            case VOID -> null;
            case STRING -> stringData;
            case INT -> intData;
            case FLOAT -> floatData;
            case BOOLEAN -> booleanData;
            default -> throw new IllegalStateException("Unknown InputType");
        };
    }
}
