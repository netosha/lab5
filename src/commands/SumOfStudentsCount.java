package commands;

import collection.StudyGroup;
import exceptions.InvalidInputException;
import exceptions.InvalidParamsCount;
import utils.CommandsManager;
import utils.Storage;
import utils.UserInterface;

public class SumOfStudentsCount extends Command{
    public SumOfStudentsCount(){
        command = "sum_of_students_count";
        helpText = "Returns sum of all study groups students";
    }

    @Override
    public void execute(UserInterface cli, Storage storage, String[] args) {
        if(args.length != 0){
            throw new InvalidParamsCount("");
        }
        long sum = (long) 0;
        for(StudyGroup sg : storage.getStudyGroups().values()){
            sum += sg.getStudentsCount();
        }
        cli.writeln("Summary students count: "+sum);
    }
}
