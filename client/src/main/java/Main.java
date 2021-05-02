import exceptions.*;
import utils.*;

import java.io.*;
import java.nio.charset.StandardCharsets;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();
        client.connect();
        CommandsManager cmdManager = new CommandsManager();
        UserInterface cli = new UserInterface(
                new InputStreamReader(System.in, StandardCharsets.UTF_8),
                new OutputStreamWriter(System.out, StandardCharsets.UTF_8)
        );

        while (true) {
            if (cli.hasNextLine()) {
                String cmd = cli.read();
                try {
                    cmdManager.executeCommand(cli, client, cmd);
                } catch (java.util.NoSuchElementException e) {
                    cli.writeln("Invalid script");
                } catch (InvalidInputException e) {
                    cli.writeln("Wrong data provided: " + e.getMessage());
                } catch (InvalidParamsCount e) {
                    cli.writeln("Invalid params count provided");
                } catch (FileNotFoundException e) {
                    cli.writeln("File not found (or you dont have permisions to read file)");
                } catch (AbortCommandException e) {
                    cli.writeln(e.getMessage());
                } catch (IOException e) {
                    cli.writeln("Unknown exception");
                }
            }
        }
//        while (true) {
//            if(scanner.hasNextLine()){
//                String msg = client.sendMessage(scanner.nextLine());
//                System.out.println(msg);
//            }
    }
}