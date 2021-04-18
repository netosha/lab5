package commands;

import collection.StudyGroup;
import exceptions.InvalidInputException;
import exceptions.InvalidParamsCount;
import utils.Storage;
import utils.UserInterface;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterLessThanStudentsCount extends Command{
    public FilterLessThanStudentsCount(){
        command = "filter_less_than_students_count";
        helpText = "Shows study groups with student count lower, than provided key (long)";
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
        cli.writeln("[");
        for(String ks : storage.getStudyGroups().keySet()){
            StudyGroup sg = storage.getStudyGroups().get(ks);
            if(sg.getStudentsCount() > key){
                cli.writeln(ks+" "+sg.toString());
            }
        }
        cli.writeln("]");

    }


}
