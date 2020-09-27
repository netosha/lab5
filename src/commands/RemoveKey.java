package commands;

import exceptions.InvalidInputException;
import exceptions.InvalidParamsCount;
import utils.CommandsManager;
import utils.Storage;
import utils.UserInterface;

public class RemoveKey extends Command{
    public RemoveKey(){
        command = "remove_key";
        helpText = "Removes StudyGroup from storage by provied key";
    }

    @Override
    public void execute(UserInterface cli, Storage storage, String[] args) {
        if(args.length != 1){
            throw new InvalidParamsCount("");
        }
        String key = args[0];
        if(!storage.getStudyGroups().containsKey(key)){
            throw new InvalidInputException("Key doesn't exist");
        }

        storage.remove(key);
        cli.writeln("Study Group successfuly removed");
    }
}
