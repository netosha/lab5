package commands;

import utils.CommandsManager;
import utils.Storage;
import utils.UserInterface;

public class Show extends Command{
    public Show(){
        command = "show";
        helpText = "Returns information about stored Objects in current storage";
    }

    @Override
    public void execute(UserInterface cli, Storage storage, String[] args) {
        cli.writeln(storage.toString());
    }
}
