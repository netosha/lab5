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

        StudyGroup studyGroup = cli.readStudyGroup();
        XStream xstream = new XStream(new StaxDriver()); // does not require XPP3 library starting with Java 6
        String resp = client.sendMessage("insert", new Request(key, studyGroup));
        Response response = (Response) xstream.fromXML(resp);
        cli.writeln(response.message);

    }

}