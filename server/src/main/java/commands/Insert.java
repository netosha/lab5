package commands;

import collection.StudyGroup;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import exceptions.InvalidInputException;
import exceptions.InvalidParamsCount;
import utils.Storage;
import utils.UserInterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;


public class Insert extends Command{
    public Insert(){
        command = "insert";
        helpText = "Adds new StudyGroup to storage by provied key";
    }

    @XmlRootElement(name="Request")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Request {
        private String key;
        private StudyGroup studyGroup;
        Request(String k, StudyGroup sg){
            key = k;
            studyGroup = sg;
        }
    }

    @XmlRootElement(name="Response")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Response {
        private String message;
        Response(String m){
            message = m;
        }
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

    @Override
    public String execute(Storage storage, Object data) throws IOException {
        XStream xstream = new XStream(new StaxDriver()); // does not require XPP3 library starting with Java 6
        Request parsed = (Request) data;
        String key = parsed.key;
        StudyGroup studyGroup = parsed.studyGroup;

        if(storage.getStudyGroups().containsKey(key)){
            return xstream.toXML( new Response("Key already exists"));
        }

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

        studyGroup.setId(id);
        storage.put(key, studyGroup);

        return xstream.toXML(new Response("Item added successfully"));
    }
}