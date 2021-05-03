import exceptions.*;
import network.Client;
import utils.*;

import java.io.*;
import java.nio.charset.StandardCharsets;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        CommandsManager cmdManager = new CommandsManager();
        UserInterface cli = new UserInterface(
                new InputStreamReader(System.in, StandardCharsets.UTF_8),
                new OutputStreamWriter(System.out, StandardCharsets.UTF_8)
        );
        Integer port = null;
        do {
            port = cli.readIntWithMessage("Provide port to connect (0 < port < 65535)");
        } while (port < 0 && port > 65535);

        Client client = new Client();
        client.connect(port);

        while (true) {
            if (cli.hasNextLine()) {
                String cmd = cli.read();
                try {
                    cmdManager.executeCommand(cli, client, cmd);
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
                }
            }
        }
    }
}