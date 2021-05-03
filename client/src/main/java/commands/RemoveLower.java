package commands;

import collection.StudyGroup;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import exceptions.InvalidInputException;
import exceptions.InvalidParamsCount;
import network.Client;
import utils.Storage;
import utils.UserInterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RemoveLower extends Command{
    public RemoveLower(){
        command = "remove_lower";
        helpText = "Remove all StudyGroups where students count lower, than in StudyGroup provided by key";
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
    public void execute(UserInterface cli, Client client, String[] args) throws IOException {
        XStream xstream = new XStream(new StaxDriver()); // does not require XPP3 library starting with Java 6

        if (args.length != 1) {
            throw new InvalidParamsCount("");
        }

        String key = args[0];
        String resp = client.sendMessage("remove_lower", new Request(key));
        Response response = (Response) xstream.fromXML(resp);
        cli.writeln(response.message);

    }
}