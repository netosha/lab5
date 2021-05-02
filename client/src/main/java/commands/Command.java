package commands;

import network.Client;
import utils.UserInterface;
import java.io.IOException;

public abstract class Command {
    protected String command;
    protected String helpText;
    protected int argsCount = 0;

    public abstract void execute(UserInterface cli, Client client,  String[] args) throws IOException;

    public String getCommand() {
        return command;
    }

    public String getHelpText() {
        return helpText;
    }
}