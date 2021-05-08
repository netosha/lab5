package commands;

import collection.StudyGroup;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import exceptions.InvalidInputException;
import exceptions.InvalidParamsCount;
import network.Client;
import utils.Storage;
import utils.UserInterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.ArrayList;

public class Insert extends Command {
    public Insert() {
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
    public void execute(UserInterface cli, Client client, String[] args) throws InvalidParamsCount, InvalidInputException, IOException {
        if (args.length != 1) {
            throw new InvalidParamsCount("");
        }
        String key = args[0];

        XStream xstream = new XStream(new StaxDriver());
        xstream.addPermission(NoTypePermission.NONE);
        xstream.allowTypesByRegExp(new String[] { ".*" });

        // Know, that is most ineffective way to check if id are not exists
        // But i don't want to hardcode another way to get ID from server
        String rawStorage = client.sendMessage("show");
        Show.Response parsedStorage = (Show.Response) xstream.fromXML(rawStorage);
        Storage storage = parsedStorage.storage;

        if(storage.getStudyGroups().containsKey(key)){
            cli.writeln("Key already exists");
            return;
        }

        StudyGroup studyGroup = cli.readStudyGroup();
        String resp = client.sendMessage("insert", new Request(key, studyGroup));
        Response response = (Response) xstream.fromXML(resp);
        cli.writeln(response.message);

    }

}