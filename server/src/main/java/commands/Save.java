package commands;
import collection.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import utils.*;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Save extends Command{
    public Save(){
        command = "save";
        helpText = "Save storage to dump.xml";
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
            File file = new File("dump.xml");
            FileOutputStream printWriter = new FileOutputStream(file);
            StringWriter writer = new StringWriter();
            XStream xstream = new XStream(new DomDriver()); // does not require XPP3 library
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
        return xstream.toXML(new Response("This command can be executed only on server side"));
    }
}