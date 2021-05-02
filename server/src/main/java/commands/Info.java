package commands;

import com.thoughtworks.xstream.XStream;
import utils.Storage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;


public class Info extends Command {
    public Info() {
        command = "info";
        helpText = "Returns information about current storage (creation time, elements count, storage type and etc.)";
    }

    @XmlRootElement(name="Payload")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Payload {
        private String type;
        private Integer count;
        private ZonedDateTime date;

        Payload(String t, Integer c, ZonedDateTime d){
            type = t;
            count = c;
            date = d;
        }
    }



    @Override
    public String execute(Storage storage, String[] args) throws IOException {
        Payload payload = new Payload(storage.getStudyGroups().getClass().toString(), storage.getStudyGroups().size(), storage.getCreationDate());
        XStream xstream = new XStream();
        String xml = xstream.toXML(payload);
        return xml;
    }
}
