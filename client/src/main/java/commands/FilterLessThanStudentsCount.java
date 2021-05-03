package commands;

import collection.StudyGroup;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import exceptions.InvalidInputException;
import exceptions.InvalidParamsCount;
import network.Client;
import utils.Storage;
import utils.UserInterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.ArrayList;


public class FilterLessThanStudentsCount extends Command {
    public FilterLessThanStudentsCount() {
        command = "filter_less_than_students_count";
        helpText = "Shows study groups with student count lower, than provided key (long)";
    }

    @XmlRootElement(name = "Request")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Request {
        private Long value;

        Request(Long v) {
            value = v;
        }
    }

    @XmlRootElement(name = "Response")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Response {
        private ArrayList<StudyGroup> studyGroups;

        Response(ArrayList<StudyGroup> sgs) {
            studyGroups = sgs;
        }
    }


    @Override
    public void execute(UserInterface cli, Client client, String[] args) throws IOException {
        if (args.length != 1) {
            throw new InvalidParamsCount("");
        }
        Long value;
        try {
            value = Long.parseLong(args[0]);
        } catch (Exception e) {
            throw new InvalidInputException("Value should be long");
        }

        XStream xstream = new XStream(new StaxDriver()); // does not require XPP3 library starting with Java 6
        String resp = client.sendMessage("filter_less_than_students_count", new Request(value));
        Response response = (Response) xstream.fromXML(resp);
        if (response.studyGroups.size() == 0) {
            cli.writeln("No StudyGroups found");
        } else {
            response.studyGroups.forEach(x -> cli.writeln(x.toString()));
        }
    }
}