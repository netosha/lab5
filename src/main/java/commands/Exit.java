package commands;

import utils.CommandsManager;
import utils.Storage;
import utils.UserInterface;

public class Exit extends Command{
    public Exit(){
        command = "exit";
        helpText = "Close program (without save in file)";
    }

    @Override
    public void execute(UserInterface cli, Storage storage, String[] args) {System.exit(1);}
}
