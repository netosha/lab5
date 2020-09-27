package commands;

import collection.StudyGroup;
import exceptions.InvalidInputException;
import utils.CommandsManager;
import utils.Storage;
import utils.UserInterface;

import java.util.Iterator;

public class MinById extends Command{
    public MinById(){
        command = "min_by_id";
        helpText = "Show Study Group with minimal id";
    }

    @Override
    public void execute(UserInterface cli, Storage storage, String[] args) {
        if(storage.getStudyGroups().values().size() == 0){
            throw new InvalidInputException("Storage is empty");
        }
        int id = Integer.MAX_VALUE;
        // Select minimal ID
        StudyGroup studyGroup = new StudyGroup();
        for(StudyGroup sg : storage.getStudyGroups().values()){
            id = Math.min(sg.getId(), id);
        }

        // Get StudyGroup by ID
        for(StudyGroup sg : storage.getStudyGroups().values()){
            if(sg.getId() == id){
                studyGroup = sg;
                break;
            }
        }
        cli.writeln("Min Study Group ID: "+studyGroup.toString());
    }
}
