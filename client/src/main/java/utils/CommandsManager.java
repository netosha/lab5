package utils;
import commands.*;
import exceptions.NoSuchCommandException;
import network.Client;

import java.io.IOException;
import java.util.*;


public class CommandsManager {
    private Map<String, Command> commands = new LinkedHashMap<String, Command>();


    // Change
    public CommandsManager() {
        addCommand(new Info());
        addCommand(new Clear());
        addCommand(new Insert());
        addCommand(new Show());
        addCommand(new Update());
        addCommand(new SumOfStudentsCount());
        addCommand(new FilterLessThanStudentsCount());
        addCommand(new MinById());
    }

    private void addCommand(Command cmd) {
        commands.put(cmd.getCommand(), cmd);
    }

    public void executeCommand(UserInterface cli, Client client, String commandString) throws IOException {
        try{
            String[] parsedCommandString = commandString.split(" ");
            Command command = getCommand(parsedCommandString[0]);
            String[] args = Arrays.copyOfRange(parsedCommandString, 1, parsedCommandString.length);
            command.execute(cli, client, args);
        }catch (NoSuchCommandException e){
            System.out.println("No behaviour found for this command. Here raw server response");
        }catch (Exception e){
            throw e;
        }

    }

    /**
     * Return string by name
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
        return new ArrayList<Command>(commands.values());
    }
}

