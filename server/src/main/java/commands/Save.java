package commands;
import collection.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import utils.*;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Save extends Command{
    public Save(){
        command = "save";
        helpText = "Save storage to file (default: ./dump.xml)";
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
    public void execute(UserInterface cli, Storage storage, String[] args) throws IOException {
        try{
            String pathname = "dump.xml";
            if(args.length == 1){
                pathname = args[0];
            }

            File file = new File(pathname);
            FileOutputStream printWriter = new FileOutputStream(file);
            StringWriter writer = new StringWriter();

            XStream xstream = new XStream(new StaxDriver());
            xstream.addPermission(NoTypePermission.NONE);
            xstream.allowTypesByRegExp(new String[] { ".*" });

            xstream.alias("storage", Storage.class);
            String xml = xstream.toXML(storage);
            printWriter.write(xml.getBytes(StandardCharsets.UTF_8));
            cli.writeln("Saved storage dump file to "+file.getAbsolutePath());
        }catch (Exception e){
            e.printStackTrace();
            throw new IOException("Serialize XML to Object failed");
        }
    }

    // Command not available on client side
    @Override
    public String execute(Storage storage, Object data) throws IOException {
        XStream xstream = new XStream(new StaxDriver());
        xstream.addPermission(NoTypePermission.NONE);
        xstream.allowTypesByRegExp(new String[] { ".*" });

        return xstream.toXML(new Response("This command can be executed only on server side"));
    }
}