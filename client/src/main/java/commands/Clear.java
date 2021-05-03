package commands;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import network.Client;
import utils.UserInterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;


public class Clear extends Command {
    public Clear() {
        command = "clear";
        helpText = "Cleans storage";
    }

    @XmlRootElement(name = "Response")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Response {
        private String message;

        Response(String m) {
            message = m;
        }
    }

    public void execute(UserInterface cli, Client client, String[] args) throws IOException {
        String resp = client.sendMessage("clear");
        XStream xstream = new XStream(new StaxDriver()); // does not require XPP3 library starting with Java 6
        Response response = (Response) xstream.fromXML(resp);
        cli.writeln(response.message);
    }


}