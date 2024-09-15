package Java.ProgramFlow;

import javax.naming.NameNotFoundException;

public class CommandManager {

    private static final Command[] commands = {
            new Command("train", false),
            new Command("test", false),
            new Command("test", true),
            new Command("print-train-digit", true),
            new Command("print-test-digit", true),
            new Command("help", false),
    };

    public void doCommand(String commandString) {
        Command command;
        try {
            command = parseCommand(commandString);
        } catch (Exception e) {
            System.out.println("Invalid command.");
            System.out.println(e.getMessage());
            return;
        }

        switch (command.getName()) {
            case "train":
                break;
            case "test":
                if (command.takesData()) {

                } else {

                }
                break;
            case "print-train-digit":
                break;
            case "print-test-digit":
                break;
            case "help":
                printCommands();
                break;
        }
    }

    private Command parseCommand(String commandString) throws NameNotFoundException, IllegalArgumentException {
        String[] parts = commandString.split(" ");

        if (parts.length == 0 || parts.length > 2) {
            throw new IllegalArgumentException("Invalid command format. Expected: <command> [optional: data]");
        }

        String commandName = parts[0];
        boolean commandTakesData = parts.length == 2;
        int commandData = 0;

        if (commandTakesData) {
            try {
                commandData = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Data must be an integer.");
            }
        }

        Command parsedCommand = new Command(commandName, commandTakesData);

        // Search for the matching command
        for (Command cmd : commands) {
            if (cmd.equals(parsedCommand)) {
                if (commandTakesData) {
                    cmd.setData(commandData);
                }
                return cmd;
            }
        }

        throw new NameNotFoundException("Command \"" + commandName + "\" not found.");
    }

    public void printCommands() {
        System.out.println("---------------------------------------------");
        System.out.println("Type in a command:");
        System.out.println("train");
        System.out.println("test");
        System.out.println("test (int DigitIndex)");
        System.out.println("print-train-digit (int DigitIndex)");
        System.out.println("print-test-digit (int DigitIndex)");
        System.out.println("help");
    }
}
