package commands;

import utils.Storage;

import java.io.IOException;

public abstract class Command {
    protected String command;
    protected String helpText;
    protected int argsCount = 0;

    public abstract String execute(Storage storage, String[] args) throws IOException;

    public String getCommand() {
        return command;
    }

    public String getHelpText() {
        return helpText;
    }
}
