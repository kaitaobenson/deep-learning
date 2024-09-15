package Java.ProgramFlow;

public class Command {

    private String name;
    private boolean takesData;
    private int data;

    public Command(String name, boolean takesData) {
        this.name = name;
        this.takesData = takesData;
    }

    public String getName() {
        return name;
    }

    public boolean takesData() {
        return takesData;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Command command = (Command) obj;
        return takesData == command.takesData && name.equals(command.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + (takesData ? 1 : 0);
    }
}
