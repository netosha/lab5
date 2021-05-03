package commands;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import utils.Storage;
import utils.UserInterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.time.ZonedDateTime;


public class Info extends Command {
    public Info() {
        command = "info";
        helpText = "Returns information about current storage (creation time, elements count, storage type and etc.)";
    }

    @XmlRootElement(name = "Response")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Response {
        private String type;
        private Integer count;
        private ZonedDateTime date;

        Response(String t, Integer c, ZonedDateTime d) {
            type = t;
            count = c;
            date = d;
        }
    }

    @Override
    public String execute(Storage storage, Object data) throws IOException {
        XStream xstream = new XStream(new StaxDriver());
        return xstream.toXML(
                new Response(
                        storage.getStudyGroups().getClass().toString(),
                        storage.getStudyGroups().size(),
                        storage.getCreationDate()
                )
        );
    }

    @Override
    public void execute(UserInterface cli, Storage storage, String[] args) {
        cli.writeln("Storage type: " + storage.getStudyGroups().getClass());
        cli.writeln("Elements count: " + storage.getStudyGroups().size());
        cli.writeln("Creation date: " + storage.getCreationDate());
    }
}
