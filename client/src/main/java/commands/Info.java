package commands;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import utils.Client;
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


    @XmlRootElement(name="Data")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Data {
        private String type;
        private Integer count;
        private ZonedDateTime date;

        Data(String t, Integer c, ZonedDateTime d){
            type = t;
            count = c;
            date = d;
        }
    }

    @Override
    public void execute(UserInterface cli, Client client, String[] args) throws IOException {
        String resp = client.sendMessage("info");
        XStream xstream = new XStream(new StaxDriver()); // does not require XPP3 library starting with Java 6
        Data parsed = (Data) xstream.fromXML(resp);
        cli.writeln("Storage type: "+parsed.type);
        cli.writeln("Elements count: "+parsed.count);
        cli.writeln("Creation date: "+parsed.date);
    }
}
