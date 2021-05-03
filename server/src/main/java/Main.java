import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import exceptions.*;
import network.*;
import utils.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {
        utils.CommandsManager cmdManager = new utils.CommandsManager();
        Storage storage = new Storage();

        UserInterface cli = new UserInterface(
                new InputStreamReader(System.in, StandardCharsets.UTF_8),
                new OutputStreamWriter(System.out, StandardCharsets.UTF_8)
        );

        Integer port = null;

        do {
            port = cli.readIntWithMessage("Provide port to run (0 < port < 65535)");
        } while (port < 0 && port > 65535);


        // Parse args
        if (args.length > 0) {
            try {
                File file = new File(args[0]);
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                BufferedReader r = new BufferedReader(new InputStreamReader(bis, StandardCharsets.UTF_8));
                String lines = r.lines().collect(Collectors.joining());
                XStream xstream = new XStream(new DomDriver());
                xstream.alias("storage", Storage.class);
                storage = (Storage) xstream.fromXML(lines);
                cli.writeln("Storage loaded from " + file.getAbsolutePath());
            } catch (FileNotFoundException e) {
                cli.writeln("Failed to load dump from file");
            }
        }

        Storage finalStorage = storage;
        Integer finalPort = port;
        Runnable task = () -> Server.main(cmdManager, finalStorage, finalPort);
        Thread thread = new Thread(task);
        thread.start();

        // On exit hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                cmdManager.executeCommand(cli, finalStorage, "save");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));



        while (true) {
            if (cli.hasNextLine()) {
                String cmd = cli.read();
                try {
                    cmdManager.executeCommand(cli, storage, cmd);
                } catch (java.util.NoSuchElementException e) {
                    cli.writeln("Invalid script");
                }
                catch (NoSuchCommandException e) {
                    cli.writeln(String.format("Command %s not found", e.getMessage()));
                }catch (InvalidInputException e) {
                    cli.writeln("Wrong data provided: " + e.getMessage());
                } catch (InvalidParamsCount e) {
                    cli.writeln("Invalid params count provided");
                } catch (FileNotFoundException e) {
                    cli.writeln("File not found (or you dont have permissions to read file)");
                } catch (AbortCommandException e) {
                    cli.writeln(e.getMessage());
                } catch (IOException e) {
                    cli.writeln("Unknown exception");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}