package commands;
import collection.*;
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
            PrintWriter printWriter = new PrintWriter(file);
            StringWriter writer = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(Storage.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(storage, printWriter);
            cli.writeln("Saved storage dump file to "+file.getAbsolutePath());
        }catch (JAXBException e){
            e.printStackTrace();
            throw new IOException("Serialaize XML to Object failed");
        }
    }
}
