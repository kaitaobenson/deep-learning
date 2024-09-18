package Java.ProgramFlow;

import Java.Util.Util;

import javax.naming.NameNotFoundException;

public class CommandParser {

    private static final Command[] commands = {
            new Command("train", false),
            new Command("test", false),
            new Command("test", true),
            new Command("print-train-digit", true),
            new Command("print-test-digit", true),
            new Command("help", false),
            new Command("quit", false),
    };

    public Command parseCommand(String commandString) throws NameNotFoundException, IllegalArgumentException {
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
        System.out.println(Util.getLine());
        System.out.println("Type in a command:");
        System.out.println("train");
        System.out.println("test");
        System.out.println("test (int DigitIndex)");
        System.out.println("print-train-digit (int DigitIndex)");
        System.out.println("print-test-digit (int DigitIndex)");
        System.out.println("help");
        System.out.println("quit");
        System.out.println(Util.getLine());
    }
}
