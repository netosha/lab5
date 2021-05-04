package commands;

import collection.StudyGroup;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import exceptions.InvalidInputException;
import exceptions.InvalidParamsCount;
import utils.Storage;
import utils.UserInterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RemoveGreaterKey extends Command{
    public RemoveGreaterKey(){
        command = "remove_greater_key";
        helpText = "Remove all StudyGroups where students count greater, that value (long)";
    }

    @XmlRootElement(name = "Request")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Request {
        private Long value;

        Request(Long v) {
            value = v;
        }
    }

    @XmlRootElement(name = "Response")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Response {
        private String message;

        Response(String m) {
            message = m;
        }
    }


    @Override
    public void execute(UserInterface cli, Storage storage, String[] args)
    {
        if(args.length != 1){
            throw new InvalidParamsCount("");
        }
        long value;

        try{
            value = Long.parseLong(args[0]);
        }catch (Exception e){
            throw new InvalidInputException("Value should be long");
        }

        ArrayList<StudyGroup> toDelete = storage
                .getStudyGroups()
                .values()
                .stream()
                .filter(x -> x.getStudentsCount() > value)
                .collect(Collectors.toCollection(ArrayList::new));

        toDelete.forEach(storage::remove);
        cli.writeln("Successfully removed "+toDelete.size()+" study groups");
    }

    @Override
    public String execute(Storage storage, Object data) throws IOException {
        XStream xstream = new XStream(new StaxDriver());
        xstream.addPermission(NoTypePermission.NONE);
        xstream.allowTypesByRegExp(new String[] { ".*" });

        Request parsed = (Request) data;
        Long value = parsed.value;


        ArrayList<StudyGroup> toDelete = storage
                .getStudyGroups()
                .values()
                .stream()
                .filter(x -> x.getStudentsCount() > value)
                .collect(Collectors.toCollection(ArrayList::new));

        toDelete.forEach(storage::remove);

        return xstream.toXML(new Response("Successfully removed "+toDelete.size()+" study groups"));

    }


}