package commands;

import utils.CommandsManager;
import utils.Storage;
import utils.UserInterface;

public class Info extends Command{
    public Info(){
        command = "info";
        helpText = "Returns information about current storage (creation time, elements count, storage type and etc.)";
    }

    @Override
    public void execute(UserInterface cli, Storage storage, String[] args) {
        StringBuilder st = new StringBuilder();
        cli.writeln("Storage type : "+storage.getStudyGroups().getClass());
        cli.writeln("Elements count : "+storage.getStudyGroups().size());
        cli.writeln("Creation date : "+storage.getCreationDate());
    }
}
