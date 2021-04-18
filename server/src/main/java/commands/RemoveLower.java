package commands;

import collection.StudyGroup;
import exceptions.InvalidInputException;
import exceptions.InvalidParamsCount;
import utils.Storage;
import utils.UserInterface;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RemoveLower extends commands.Command {
    public RemoveLower(){
        command = "remove_lower";
        helpText = "Remove all StudyGroups where students count lower, than in StudyGroup provided by key";
    }

    @Override
    public void execute(UserInterface cli, Storage storage, String[] args)
    {
        if(args.length != 1){
            throw new InvalidParamsCount("");
        }
        String key = args[0];
        if(!storage.getStudyGroups().containsKey(key)){
            throw new InvalidInputException("Key doesn't exist");
        }
        StudyGroup toCompare = storage.getStudyGroups().get(key);
        Iterator<StudyGroup> toDelete = storage.getStudyGroups().values().stream().filter(x -> x.getStudentsCount() < toCompare.getStudentsCount()).collect(Collectors.toList()).iterator();

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
