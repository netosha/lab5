package commands;

import collection.StudyGroup;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import utils.CommandsManager;
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
        private Storage storage;

        Response(Storage s) {
            storage = s;
        }
    }

    @Override
    public void execute(UserInterface cli, Storage storage, String[] args) {
        cli.writeln(storage.toString());
    }

    @Override
    public String execute(Storage storage, Object data) throws IOException {
        XStream xstream = new XStream(new StaxDriver());
        return xstream.toXML(new Response(storage));
    }
}
