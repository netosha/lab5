package commands;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import commands.Command;
import commands.Info;
import network.Client;
import utils.Storage;
import utils.UserInterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;

public class Show extends Command {
    public Show() {
        command = "show";
        helpText = "Returns information about stored Objects in current storage";
    }

    @XmlRootElement(name = "Response")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Response {
        public Storage storage;

        Response(Storage s) {
            storage = s;
        }
    }

    @Override
    public void execute(UserInterface cli, Client client, String[] args) throws IOException {
        XStream xstream = new XStream(new StaxDriver());
        xstream.addPermission(NoTypePermission.NONE);
        xstream.allowTypesByRegExp(new String[] { ".*" });

        String resp = client.sendMessage("show");
        Response response = (Response) xstream.fromXML(resp);

        cli.writeln(response.storage.toString());

    }
}
