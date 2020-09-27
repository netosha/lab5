package commands;

import collection.StudyGroup;
import exceptions.InvalidInputException;
import exceptions.InvalidParamsCount;
import utils.Storage;
import utils.UserInterface;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RemoveGreaterKey extends Command{
    public RemoveGreaterKey(){
        command = "remove_greater_key";
        helpText = "Remove all StudyGroups where students count greater, that key (long)";
    }

    @Override
    public void execute(UserInterface cli, Storage storage, String[] args)
    {
        if(args.length != 1){
            throw new InvalidParamsCount("");
        }
        Long key;
        try{
            key = Long.parseLong(args[0]);
        }catch (Exception e){
            throw new InvalidInputException("Key should be long");
        }
        Iterator<StudyGroup> toDelete = storage.getStudyGroups().values().stream().filter(x -> x.getStudentsCount() > key).collect(Collectors.toList()).iterator();

        StudyGroup next;
        Integer it = 0;
        while (toDelete.hasNext()){
            next = toDelete.next();
            storage.remove(next);
            it++;
        }
        cli.writeln("Succesfuly removed "+it+" study groups");
    }


}
