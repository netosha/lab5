import collection.*;
import commands.*;
import exceptions.AbortCommandException;
import exceptions.InvalidInputException;
import exceptions.InvalidParamsCount;
import exceptions.NoSuchCommandException;
import utils.*;
import utils.Storage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        // Init command manager
        CommandsManager cmdManager = new CommandsManager();
        Storage storage = new Storage();

        // Init user cli
        UserInterface cli = new UserInterface(
                new InputStreamReader(System.in, StandardCharsets.UTF_8),
                new OutputStreamWriter(System.out, StandardCharsets.UTF_8)
        );

        Person admin = new Person("admin", LocalDateTime.now(), 5.0, Color.BLUE);
        StudyGroup sg = new StudyGroup(0, "name", new Coordinates(5, (long) 5), (long) 1, FormOfEducation.DISTANCE_EDUCATION, Semester.FOURTH, admin);
        storage.put("1",sg);

        while (true){
            if(cli.hasNextLine()){
                String cmd = cli.read();
                try{
                    cmdManager.executeCommand(cli, storage, cmd);
                }catch (NoSuchCommandException e){
                    cli.writeln("Command not found");
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