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

public class Update extends Command {
    public Update() {
        command = "update";
        helpText = "Updates storage StudyGroup by provied id";
    }

    @XmlRootElement(name = "Request")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Request {
        private Integer id;
        private StudyGroup studyGroup;

        Request(Integer i, StudyGroup sg) {
            id = i;
            studyGroup = sg;
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
    public void execute(UserInterface cli, Client client, String[] args) throws InvalidParamsCount, InvalidInputException, IOException {
        if (args.length != 1) {
            throw new InvalidParamsCount("");
        }

        XStream xstream = new XStream(new StaxDriver());
        xstream.addPermission(NoTypePermission.NONE);
        xstream.allowTypesByRegExp(new String[] { ".*" });


        // Know, that is most ineffective way to check if id are not exists
        // But i don't want to hardcode another way to get ID from server
        String rawStorage = client.sendMessage("show");
        Show.Response parsedStorage = (Show.Response) xstream.fromXML(rawStorage);
        Storage storage = parsedStorage.storage;

        StudyGroup updatingStudyGroup = storage
                .getStudyGroups()
                .values()
                .stream()
                .filter(sg -> sg.getId().equals(Integer.parseInt(args[0])))
                .findFirst()
                .orElse(null);

        if (updatingStudyGroup == null) {
            cli.writeln("StudyGroup with provided Id not found");
            return;
        }


        Integer id = Integer.parseInt(args[0]);
        StudyGroup studyGroup = cli.readStudyGroup();
        String resp = client.sendMessage("update", new Request(id, studyGroup));
        Response response = (Response) xstream.fromXML(resp);
        cli.writeln(response.message);
    }

}