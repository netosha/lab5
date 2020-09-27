package commands;

import exceptions.InvalidParamsCount;
import utils.Storage;
import utils.UserInterface;


public class Clear extends Command{
    public Clear(){
        command = "clear";
        helpText = "Cleans storage";
    }

    @Override
    public void execute(UserInterface cli, Storage storage, String[] args) {
        if(args.length != 0){
            throw new InvalidParamsCount("");
        }

        storage.clear();

        cli.writeln("Storage successfuly cleared");
    }
}
