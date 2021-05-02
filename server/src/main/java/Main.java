import exceptions.*;
import network.*;
import utils.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {


        utils.CommandsManager cmdManager = new utils.CommandsManager();
        Storage storage = new Storage();

        // Init user cli
        UserInterface cli = new UserInterface(
                new InputStreamReader(System.in, StandardCharsets.UTF_8),
                new OutputStreamWriter(System.out, StandardCharsets.UTF_8)
        );

        Runnable task = () -> {
            Server.main(cmdManager, storage, 8080);
        };

        Thread thread = new Thread(task);
        thread.start();

        Writer writer = new OutputStreamWriter(System.out, StandardCharsets.UTF_8);
        Scanner scanner = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        System.out.println("Console unlocked");

        while (true) {
            if (cli.hasNextLine()) {
                String cmd = cli.read();
                try {
                    cmdManager.executeCommand(cli, storage, cmd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}