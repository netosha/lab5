package commands;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import exceptions.InvalidParamsCount;
import utils.Storage;
import utils.UserInterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.time.ZonedDateTime;


public class Clear extends Command{
    public Clear(){
        command = "clear";
        helpText = "Cleans storage";
    }

    @XmlRootElement(name="Data")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Data {
        private String message;

        Data(String m){
            message = m;

        }
    }

    @Override
    public String execute(Storage storage, Object data) throws IOException {
        storage.clear();
        Data payload = new Data("Storage successfully cleared");
        XStream xstream = new XStream(new StaxDriver());
        return xstream.toXML(payload);
    }


    @Override
    public void execute(UserInterface cli, Storage storage, String[] args) {
        storage.clear();
        cli.writeln("Storage successfully cleared");
    }


}