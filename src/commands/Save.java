package commands;
import collection.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import utils.*;


import javax.xml.bind.*;
import java.io.*;

public class Save extends Command{
    public Save(){
        command = "save";
        helpText = "Save storage to dump.xml";
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
            printWriter.write(xml.getBytes("UTF-8"));
            cli.writeln("Saved storage dump file to "+file.getAbsolutePath());
        }catch (Exception e){
            e.printStackTrace();
            throw new IOException("Serialaize XML to Object failed");
        }
    }
}
