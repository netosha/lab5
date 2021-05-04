package commands;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import network.Client;
import utils.UserInterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.time.ZonedDateTime;

public class Info extends Command{
    public Info(){
        command = "info";
        helpText = "Returns information about current storage (creation time, elements count, storage type and etc.)";
    }


    @XmlRootElement(name="Response")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Response {
        private String type;
        private Integer count;
        private ZonedDateTime date;

        Response(String t, Integer c, ZonedDateTime d){
            type = t;
            count = c;
            date = d;
        }
    }

    @Override
    public void execute(UserInterface cli, Client client, String[] args) throws IOException {
        XStream xstream = new XStream(new StaxDriver());
        xstream.addPermission(NoTypePermission.NONE);
        xstream.allowTypesByRegExp(new String[] { ".*" });

        String resp = client.sendMessage("info");
        Response response = (Response) xstream.fromXML(resp);

        cli.writeln("Storage type: "+response.type);
        cli.writeln("Elements count: "+response.count);
        cli.writeln("Creation date: "+response.date);
    }
}
