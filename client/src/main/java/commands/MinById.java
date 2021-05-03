package commands;

import collection.StudyGroup;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import exceptions.InvalidInputException;
import network.Client;
import utils.CommandsManager;
import utils.Storage;
import utils.UserInterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MinById extends Command{
    public MinById(){
        command = "min_by_id";
        helpText = "Show study group with lowest id";
    }


    @XmlRootElement(name = "Response")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Response {
        private StudyGroup studyGroup;

        Response(StudyGroup sg) {
            studyGroup = sg;
        }
    }

    @Override
    public void execute(UserInterface cli, Client client, String[] args) throws IOException {
        XStream xstream = new XStream(new StaxDriver()); // does not require XPP3 library starting with Java 6
        String resp = client.sendMessage("min_by_id");
        Response response = (Response) xstream.fromXML(resp);

        if(response.studyGroup == null){
            cli.writeln("Storage is empty");
        }else{
            cli.writeln(response.studyGroup.toString());
        }
    }
}