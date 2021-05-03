
package commands;

import network.Client;
import utils.CommandsManager;
import utils.Storage;
import utils.UserInterface;

import java.io.IOException;

public class Help extends Command{
    public Help(){
        command = "help";
        helpText = "Returns all avail commands (with short description)";
    }


    @Override
    public void execute(UserInterface cli, Client client, String[] args) throws IOException {
        new CommandsManager()
                .getAllCommands()
                .forEach(c -> cli.writeln(c.getCommand() +" - "+ c.getHelpText()));
    }
}