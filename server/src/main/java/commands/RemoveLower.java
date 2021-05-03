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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RemoveLower extends commands.Command {
    public RemoveLower() {
        command = "remove_lower";
        helpText = "Remove all StudyGroups where students count lower, than in StudyGroup provided by key";
    }

    @XmlRootElement(name = "Request")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Request {
        private String key;

        Request(String k) {
            key = k;
        }
    }

    @XmlRootElement(name = "Response")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Response {
        private String message;

        Response(String m) {
            message = m;
        }
    }


    @Override
    public void execute(UserInterface cli, Storage storage, String[] args) {
        if (args.length != 1) {
            throw new InvalidParamsCount("");
        }
        String key = args[0];
        if (!storage.getStudyGroups().containsKey(key)) {
            throw new InvalidInputException("StudyGroup with provided key doesn't exist");
        }

        StudyGroup toCompare = storage.getStudyGroups().get(key);

        ArrayList<StudyGroup> toDelete = storage
                .getStudyGroups()
                .values()
                .stream()
                .filter(x -> x.getStudentsCount() > toCompare.getStudentsCount())
                .collect(Collectors.toCollection(ArrayList::new));

        toDelete.forEach(storage::remove);
        cli.writeln("Successfully removed " + toDelete.size() + " study groups");
    }

    @Override
    public String execute(Storage storage, Object data) throws IOException {
        XStream xstream = new XStream(new StaxDriver());
        Request parsed = (Request) data;
        String key = parsed.key;

        if (!storage.getStudyGroups().containsKey(key)) {
            return xstream.toXML(new Response("StudyGroup with provided key doesn't exist"));
        }

        StudyGroup toCompare = storage.getStudyGroups().get(key);

        ArrayList<StudyGroup> toDelete = storage
                .getStudyGroups()
                .values()
                .stream()
                .filter(x -> x.getStudentsCount() < toCompare.getStudentsCount())
                .collect(Collectors.toCollection(ArrayList::new));

        toDelete.forEach(storage::remove);

        return xstream.toXML(new Response("Successfully removed " + toDelete.size() + " study groups"));

    }


}