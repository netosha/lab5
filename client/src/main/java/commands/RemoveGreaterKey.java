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
    public void execute(UserInterface cli, Client client, String[] args) throws IOException {
        XStream xstream = new XStream(new StaxDriver());
        xstream.addPermission(NoTypePermission.NONE);
        xstream.allowTypesByRegExp(new String[] { ".*" });

        if (args.length != 1) {
            throw new InvalidParamsCount("");
        }

        long value;

        try{
            value = Long.parseLong(args[0]);
        }catch (Exception e){
            throw new InvalidInputException("Value should be long");
        }

        String resp = client.sendMessage("remove_greater_key", new Request(value));
        Response response = (Response) xstream.fromXML(resp);
        cli.writeln(response.message);

    }
}