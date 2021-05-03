package commands;

import collection.StudyGroup;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import exceptions.InvalidInputException;
import exceptions.InvalidParamsCount;
import network.Client;
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

        Integer id = Integer.parseInt(args[0]);
        StudyGroup studyGroup = cli.readStudyGroup();
        XStream xstream = new XStream(new StaxDriver()); // does not require XPP3 library starting with Java 6
        String resp = client.sendMessage("update", new Request(id, studyGroup));
        Response response = (Response) xstream.fromXML(resp);
        cli.writeln(response.message);
    }

}