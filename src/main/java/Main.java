import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import exceptions.AbortCommandException;
import exceptions.InvalidInputException;
import exceptions.InvalidParamsCount;
import exceptions.NoSuchCommandException;
import utils.Storage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import commands.*;
import commands.*;
import utils.*;

import static java.nio.charset.StandardCharsets.UTF_8;


public class Main {
    public static void main(String[] args) {
        // Init command manager
        utils.CommandsManager cmdManager = new utils.CommandsManager();
        Storage storage = new Storage();

        // Init user cli
        UserInterface cli = new UserInterface(
                new InputStreamReader(System.in, StandardCharsets.UTF_8),
                new OutputStreamWriter(System.out, StandardCharsets.UTF_8)
        );

        if(args.length > 0){
            try{
                File file = new File(args[0]);
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                BufferedReader r = null;
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                r = new BufferedReader(new InputStreamReader(bis, UTF_8));
                String lines = r.lines().collect(Collectors.joining());

                XStream xstream = new XStream(new DomDriver()); // does not require XPP3 library
                xstream.alias("storage", Storage.class);
                storage = (Storage) xstream.fromXML(lines);
            }
            catch (FileNotFoundException e)
            {
//                e.printStackTrace();
                cli.writeln("Failed to load dump from file");
            }
        }
        while (true){
            if(cli.hasNextLine()){
                String cmd = cli.read();
                try{
                    cmdManager.executeCommand(cli, storage, cmd);
                }catch (java.util.NoSuchElementException e){
                    cli.writeln("Invalid script");
                }catch (NoSuchCommandException e){
                    cli.writeln("Command "+e.getMessage()+" not found");
                }catch (InvalidInputException e){
                    cli.writeln("Wrong data provided: "+e.getMessage());
                }catch (InvalidParamsCount e){
                    cli.writeln("Invalid params count provided");
                }catch (FileNotFoundException e){
                    cli.writeln("File not found (or you dont have permisions to read file)");
                }catch (AbortCommandException e){
                    cli.writeln(e.getMessage());
                }
                catch (IOException e){
                    cli.writeln("Unknown exception");
                }
            }
        }

    }

}