package utils;
import commands.*;
import exceptions.NoSuchCommandException;

import java.io.IOException;
import java.util.*;


public class CommandsManager {
    private Map<String, Command> commands = new LinkedHashMap<String, Command>();


    // Change
    public CommandsManager() {
        addCommand(new Help());
        addCommand(new Show());
        addCommand(new Info());
        addCommand(new Exit());
        addCommand(new Insert());
        addCommand(new Update());
        addCommand(new Save());
        addCommand(new RemoveKey());
        addCommand(new Clear());
        addCommand(new ExecuteScript());
        addCommand(new RemoveGreater());
        addCommand(new RemoveLower());
        addCommand(new RemoveGreaterKey());
        addCommand(new SumOfStudentsCount());
        addCommand(new MinById());
        addCommand(new FilterLessThanStudentsCount());

    }

    private void addCommand(Command cmd) {
        commands.put(cmd.getCommand(), cmd);
    }

    public void executeCommand(UserInterface cli, Storage storage,  String commandString) throws NoSuchCommandException, IOException {
        try{
            String[] parsedCommandString = commandString.split(" ");
            Command command = getCommand(parsedCommandString[0]);
            String[] args = Arrays.copyOfRange(parsedCommandString, 1, parsedCommandString.length);
            command.execute(cli, storage, args);
        }catch (NoSuchElementException e){
            cli.writeln("lox");
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

