package commands;

import utils.CommandsManager;
import utils.Storage;
import utils.UserInterface;

public class Help extends Command{
    public Help(){
        command = "help";
        helpText = "Returns all avabile commands (wtih short description)";
    }

    @Override
    public void execute(UserInterface cli, Storage storage, String[] args) {
        for(Command command : new CommandsManager().getAllCommands()){
            cli.writeln(command.getCommand() +" - "+ command.getHelpText());
        }
    }
}
