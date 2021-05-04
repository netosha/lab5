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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RemoveGreater extends Command{
    public RemoveGreater(){
        command = "remove_greater";
        helpText = "Remove all StudyGroups where students count greater, than in StudyGroup provided by key";
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
        XStream xstream = new XStream(new StaxDriver());
        xstream.addPermission(NoTypePermission.NONE);
        xstream.allowTypesByRegExp(new String[] { ".*" });

        if (args.length != 1) {
            throw new InvalidParamsCount("");
        }

        String key = args[0];
        String resp = client.sendMessage("remove_greater", new Request(key));
        Response response = (Response) xstream.fromXML(resp);
        cli.writeln(response.message);

    }
}