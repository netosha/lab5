package commands;

import collection.StudyGroup;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import exceptions.InvalidInputException;
import exceptions.InvalidParamsCount;
import utils.Storage;
import utils.UserInterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterLessThanStudentsCount extends Command{
    public FilterLessThanStudentsCount(){
        command = "filter_less_than_students_count";
        helpText = "Shows study groups with student count lower, than provided value (long)";
    }

    @XmlRootElement(name="Request")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Request {
        private Long value;

        Request(Long v){
            value = v;
        }
    }

    @XmlRootElement(name="Response")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Response {
        private ArrayList<StudyGroup> studyGroups;
        Response(ArrayList<StudyGroup> sgs){
            studyGroups = sgs;
        }
    }

    @Override
    public void execute(UserInterface cli, Storage storage, String[] args)
    {
        if(args.length != 1){
            throw new InvalidParamsCount("");
        }

        Long value;

        try{
            value = Long.parseLong(args[0]);
        }catch (Exception e){
            throw new InvalidInputException("Key should be long");
        }


        ArrayList<StudyGroup> filteredStudyGroups = storage
                .getStudyGroups()
                .values()
                .stream()
                .filter(sg -> sg.getStudentsCount() > value)
                .collect(Collectors.toCollection(ArrayList::new));


        filteredStudyGroups.forEach(x -> cli.writeln(x.toString()));
    }

    @Override
    public String execute(Storage storage, Object data) throws IOException {
        XStream xstream = new XStream(new StaxDriver()); // does not require XPP3 library starting with Java 6
        Request parsed = (Request) data;

        ArrayList<StudyGroup> filteredStudyGroups = storage
                .getStudyGroups()
                .values()
                .stream()
                .filter(sg -> sg.getStudentsCount() > parsed.value)
                .collect(Collectors.toCollection(ArrayList::new));

        return xstream.toXML(new Response(filteredStudyGroups));
    }


}