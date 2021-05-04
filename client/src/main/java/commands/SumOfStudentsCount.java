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

public class SumOfStudentsCount extends Command{
    public SumOfStudentsCount() {
        command = "sum_of_students_count";
        helpText = "Returns sum of all study groups students";
    }

    @XmlRootElement(name = "Response")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Response {
        private Long count;

        Response(Long c) {
            count = c;
        }
    }

    @Override
    public void execute(UserInterface cli, Client client, String[] args) throws IOException {
        XStream xstream = new XStream(new StaxDriver());
        xstream.addPermission(NoTypePermission.NONE);
        xstream.allowTypesByRegExp(new String[] { ".*" });

        String resp = client.sendMessage("sum_of_students_count");
        Response response = (Response) xstream.fromXML(resp);

        cli.writeln("Summary students count: " + response.count);
    }
}
