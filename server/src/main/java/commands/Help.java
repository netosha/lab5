
package commands;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
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
    public void execute(UserInterface cli, Storage storage, String[] args) throws IOException {
        new CommandsManager()
                .getAllCommands()
                .forEach(c -> cli.writeln(c.getCommand() +" - "+ c.getHelpText()));
    }

    // Command not available on client side
    @Override
    public String execute(Storage storage, Object data) throws IOException {
        return "";
    }
}