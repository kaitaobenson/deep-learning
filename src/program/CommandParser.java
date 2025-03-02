package program;

import javax.naming.NameNotFoundException;

public class CommandParser {

    private static final Command[] commands = {
            new Command("train", Command.InputType.INT),
            new Command("test", Command.InputType.VOID),
            new Command("test", Command.InputType.INT),
            new Command("test-png", Command.InputType.STRING),
            new Command("print-train-digit", Command.InputType.INT),
            new Command("print-test-digit", Command.InputType.INT),
            new Command("save-model", Command.InputType.STRING),
            new Command("load-model", Command.InputType.STRING),
            new Command("random-model", Command.InputType.VOID),
            new Command("help", Command.InputType.VOID),
            new Command("quit", Command.InputType.VOID),
    };

    public Command parseCommand(String commandString) throws NameNotFoundException, IllegalArgumentException {
        String[] parts = commandString.split(" ");

        if (parts.length == 0 || parts.length > 2) {
            throw new IllegalArgumentException("Invalid command format. Expected: <command> [optional: data]");
        }

        String commandName = parts[0];
        String commandData = parts.length == 2 ? parts[1] : null;

        // Search for the matching command
        for (Command cmd : commands) {
            if (cmd.name.equals(commandName)) {
                // If the command expects no input
                if (cmd.inputType == Command.InputType.VOID && commandData == null) {
                    return cmd;
                }
                // If the command expects input and we have provided data
                if (commandData != null) {
                    switch (cmd.inputType) {
                        case STRING:
                            cmd.setData(commandData);
                            return cmd;
                        case INT:
                            try {
                                int intData = Integer.parseInt(commandData);
                                cmd.setData(intData);
                                return cmd;
                            } catch (NumberFormatException e) {
                                throw new IllegalArgumentException("Expected an integer but got: " + commandData);
                            }
                        case FLOAT:
                            try {
                                float floatData = Float.parseFloat(commandData);
                                cmd.setData(floatData);
                                return cmd;
                            } catch (NumberFormatException e) {
                                throw new IllegalArgumentException("Expected a float but got: " + commandData);
                            }
                        case BOOLEAN:
                            boolean boolData = Boolean.parseBoolean(commandData);
                            cmd.setData(boolData);
                            return cmd;
                    }
                }
            }
        }

        throw new NameNotFoundException("Command \"" + commandName + "\" not found.");
    }

    public void printCommands() {
        System.out.println("----------------------------------");
        for (Command command : commands) {
            System.out.println(command.name + ", " + command.inputType);
        }
        System.out.println("----------------------------------");
    }
}
