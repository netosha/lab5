package commands;

import collection.StudyGroup;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import exceptions.InvalidInputException;
import exceptions.InvalidParamsCount;
import utils.Storage;
import utils.UserInterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;

public class Update extends Command {
    public Update() {
        command = "update";
        helpText = "Updates storage StudyGroup by provided id";
    }

    @XmlRootElement(name = "Request")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Request {
        private Integer id;
        private StudyGroup studyGroup;

        Request(Integer i, StudyGroup sg) {
            id = i;
            studyGroup = sg;
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
        Integer id = Integer.parseInt(args[0]);

        StudyGroup studyGroup = storage
                .getStudyGroups()
                .values()
                .stream()
                .filter(sg -> sg.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (studyGroup == null) {
            throw new InvalidInputException("StudyGroup with provided Id not found");
        }

        String key = storage.getStudyGroups()
                .keySet()
                .stream()
                .filter(k -> storage.getStudyGroups().get(k).equals(studyGroup))
                .findFirst()
                .orElse(null);

        StudyGroup newStudyGroup = cli.readStudyGroup();
        newStudyGroup.setId(id);
        storage.put(key, newStudyGroup);
        cli.writeln("Item updated successfully");
    }

    @Override
    public String execute(Storage storage, Object data) throws IOException {
        XStream xstream = new XStream(new StaxDriver());
        xstream.addPermission(NoTypePermission.NONE);
        xstream.allowTypesByRegExp(new String[] { ".*" });

        Request parsed = (Request) data;
        Integer id = parsed.id;
        StudyGroup newStudyGroup = parsed.studyGroup;

        StudyGroup studyGroup = storage
                .getStudyGroups()
                .values()
                .stream()
                .filter(sg -> sg.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (studyGroup == null) {
            return xstream.toXML(new Response("StudyGroup with provided Id not found"));
        }

        String key = storage.getStudyGroups()
                .keySet()
                .stream()
                .filter(k -> storage.getStudyGroups().get(k).equals(studyGroup))
                .findFirst()
                .orElse(null);

        newStudyGroup.setId(id);
        storage.put(key, newStudyGroup);


        return xstream.toXML(new Response("Item updated successfully"));
    }
}