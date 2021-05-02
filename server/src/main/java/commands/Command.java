package commands;

import utils.Storage;
import utils.UserInterface;

import java.io.IOException;

public abstract class Command {
    protected String command;
    protected String helpText;
    protected int argsCount = 0;

    public abstract void execute(UserInterface cli, Storage storage, String[] args) throws IOException;

    public abstract String execute(Storage storage, String[] args) throws IOException;

    public String getCommand() {
        return command;
    }

    public String getHelpText() {
        return helpText;
    }
}
