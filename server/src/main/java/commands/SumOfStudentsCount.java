package commands;

import collection.StudyGroup;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import exceptions.InvalidInputException;
import exceptions.InvalidParamsCount;
import utils.CommandsManager;
import utils.Storage;
import utils.UserInterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;

public class SumOfStudentsCount extends Command {
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
    public void execute(UserInterface cli, Storage storage, String[] args) {
        if (args.length != 0) {
            throw new InvalidParamsCount("");
        }
        long sum = storage.getStudyGroups().values().stream().mapToLong(StudyGroup::getStudentsCount).sum();
        cli.writeln("Summary students count: " + sum);
    }

    @Override
    public String execute(Storage storage, Object data) throws IOException {
        long sum = storage.getStudyGroups().values().stream().mapToLong(StudyGroup::getStudentsCount).sum();
        XStream xstream = new XStream(new StaxDriver());
        xstream.addPermission(NoTypePermission.NONE);
        xstream.allowTypesByRegExp(new String[] { ".*" });

        return xstream.toXML(new Response(sum));
    }
}