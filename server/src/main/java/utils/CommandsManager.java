package utils;

import commands.*;
import exceptions.NoSuchCommandException;

import java.io.IOException;
import java.util.*;


public class CommandsManager {
    private Map<String, Command> commands = new LinkedHashMap<>();


    public CommandsManager() {
        addCommand(new Help());
        addCommand(new Info());
        addCommand(new Clear());
        addCommand(new Show());
        addCommand(new ExecuteScript());
        addCommand(new Insert());
        addCommand(new Update());
        addCommand(new Save());
        addCommand(new SumOfStudentsCount());
        addCommand(new FilterLessThanStudentsCount());
        addCommand(new MinById());
        addCommand(new RemoveGreater());
        addCommand(new RemoveLower());
        addCommand(new RemoveGreaterKey());
        addCommand(new RemoveKey());
    }

    private void addCommand(Command cmd) {
        commands.put(cmd.getCommand(), cmd);
    }


    public String executeCommand(Storage storage, String commandString, Object data) throws NoSuchCommandException, IOException {
        try {
            String[] parsedCommandString = commandString.split(" ");
            Command command = getCommand(parsedCommandString[0]);
            return command.execute(storage, data);
        } catch (NoSuchElementException e) {
            System.out.println("No such command");
            return "No such command";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void executeCommand(UserInterface cli, Storage storage, String commandString) throws IOException {
        String[] parsedCommandString = commandString.trim().split(" ");
        Command command = getCommand(parsedCommandString[0]);
        String[] args = Arrays.copyOfRange(parsedCommandString, 1, parsedCommandString.length);
        command.execute(cli, storage, args);
    }

    /**
     * Return string by name
     *
     * @param cmd string
     * @return Command
     * @throws NoSuchCommandException if command not found
     */
    public Command getCommand(String cmd) throws NoSuchCommandException {
        if (!commands.containsKey(cmd)) {
            throw new NoSuchCommandException(cmd);
        }
        return commands.getOrDefault(cmd, null);
    }

    public List<Command> getAllCommands() {
        return new ArrayList<>(commands.values());
    }
}

