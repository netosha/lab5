package commands;

import collection.StudyGroup;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import exceptions.InvalidInputException;
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
        helpText = "Show Study Group with lowest id";
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
    public void execute(UserInterface cli, Storage storage, String[] args) {
        if(storage.getStudyGroups().values().size() == 0){
            throw new InvalidInputException("Storage is empty");
        }

        StudyGroup studyGroup = storage.getStudyGroups()
                .values()
                .stream()
                .min(Comparator.comparing(StudyGroup::getId))
                .orElseThrow(NoSuchElementException::new);

        cli.writeln("Min Study Group ID: "+studyGroup.toString());
    }

    @Override
    public String execute(Storage storage, Object data) throws IOException {
        XStream xstream = new XStream(new StaxDriver());
        xstream.addPermission(NoTypePermission.NONE);
        xstream.allowTypesByRegExp(new String[] { ".*" });

        if(storage.getStudyGroups().values().size() == 0){
            return xstream.toXML(new Response(null));
        }

        StudyGroup studyGroup = storage.getStudyGroups()
                .values()
                .stream()
                .min(Comparator.comparing(StudyGroup::getId))
                .orElseThrow(NoSuchElementException::new);

        return xstream.toXML(new Response(studyGroup));
    }
}