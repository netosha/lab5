package commands;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import exceptions.InvalidInputException;
import exceptions.InvalidParamsCount;
import utils.CommandsManager;
import utils.Storage;
import utils.UserInterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;

public class RemoveKey extends Command{
    public RemoveKey(){
        command = "remove_key";
        helpText = "Removes StudyGroup from storage by provied key";
    }

    @XmlRootElement(name = "Request")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Request {
        private String key;

        Request(String k) {
            key = k;
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
    public void execute(UserInterface cli, Storage storage, String[] args) {
        if(args.length != 1){
            throw new InvalidParamsCount("");
        }
        String key = args[0];
        if(!storage.getStudyGroups().containsKey(key)){
            throw new InvalidInputException("Key doesn't exist");
        }

        storage.remove(key);
        cli.writeln("Study Group successfully removed");
    }

    @Override
    public String execute(Storage storage, Object data) throws IOException {
        XStream xstream = new XStream(new StaxDriver());
        Request parsed = (Request) data;
        String key = parsed.key;

        if(!storage.getStudyGroups().containsKey(key)){
            return xstream.toXML(new Response("Key doesn't exist"));
        }

        storage.remove(key);
        return xstream.toXML(new Response("Study Group successfully removed"));
    }
}