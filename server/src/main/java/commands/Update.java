package commands;

import collection.StudyGroup;
import exceptions.InvalidInputException;
import exceptions.InvalidParamsCount;
import utils.Storage;
import utils.UserInterface;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

public class Update extends Command{
    public Update(){
        command = "update";
        helpText = "Updates storage StudyGroup by provied id";
    }

    @Override
    public void execute(UserInterface cli, Storage storage, String[] args) {
        if(args.length != 1){
            throw new InvalidParamsCount("");
        }
        Integer id = Integer.parseInt(args[0]);
        StudyGroup studyGroup = null;
        String key = null;

        // Define StudyGroup by provided ID
        for(StudyGroup sg : storage.getStudyGroups().values()){
            if(sg.getId() == id){
                studyGroup = sg;
            }
        }

        if(studyGroup == null){
            throw new InvalidInputException("Id not found");
        }

        // Define StudyGroup key by provided ID
        // It needs to put update new value by key in storage
        for(String k : storage.getStudyGroups().keySet()){
            if(storage.getStudyGroups().get(k) == studyGroup){
                key = k;
            }
        }
        studyGroup = cli.readStudyGroup();
        studyGroup.setId(id);
        storage.put(key, studyGroup);
        cli.writeln("Item inserted successfully");
    }
}
