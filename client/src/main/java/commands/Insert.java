package commands;

import collection.StudyGroup;
import exceptions.InvalidInputException;
import exceptions.InvalidParamsCount;
import utils.CommandsManager;
import utils.Storage;
import utils.UserInterface;

import java.util.LinkedHashMap;
import java.util.Map;

public class Insert extends Command{
    public Insert(){
        command = "insert";
        helpText = "Adds new StudyGroup to storage by provied key";
    }

    @Override
    public void execute(UserInterface cli, Storage storage, String[] args) throws InvalidParamsCount, InvalidInputException {
        try{
            if(args.length != 1){
                throw new InvalidParamsCount("");
            }
            String key = args[0];
            if(storage.getStudyGroups().containsKey(key)){
                throw new InvalidInputException("Key already exists");
            }
            // Checks maxID to prevent collision
            Integer id = Integer.MIN_VALUE;
            for(StudyGroup sg : storage.getStudyGroups().values()){
                id = Math.max(id, sg.getId());
            }
            // In case if storage are empty;
            if(id == Integer.MIN_VALUE){
                id=0;
            }else{
                id+=1;
            }

            StudyGroup studyGroup = cli.readStudyGroup();
            studyGroup.setId(id);
            storage.put(key, studyGroup);
            cli.writeln("Item inserted successfully");
        }catch (Exception e){
            throw e;
        }
    }
}
