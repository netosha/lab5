package utils;

import commands.*;
import exceptions.NoSuchCommandException;
import network.Client;

import java.io.IOException;
import java.util.*;


public class CommandsManager {
    private Map<String, Command> commands = new LinkedHashMap<>();


    // Change
    public CommandsManager() {
        addCommand(new Help());
        addCommand(new Info());
        addCommand(new Clear());
        addCommand(new Insert());
        addCommand(new Show());
        addCommand(new Update());
        addCommand(new SumOfStudentsCount());
        addCommand(new FilterLessThanStudentsCount());
        addCommand(new MinById());
        addCommand(new RemoveGreater());
        addCommand(new RemoveLower());
        addCommand(new RemoveGreaterKey());
        addCommand(new RemoveKey());
        addCommand(new Exit());
        addCommand(new ExecuteScript());
    }

    private void addCommand(Command cmd) {
        commands.put(cmd.getCommand(), cmd);
    }

    public void executeCommand(UserInterface cli, Client client, String commandString) throws IOException, NoSuchCommandException {
        String[] parsedCommandString = commandString.split(" ");
        Command command = getCommand(parsedCommandString[0]);
        String[] args = Arrays.copyOfRange(parsedCommandString, 1, parsedCommandString.length);
        command.execute(cli, client, args);
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

