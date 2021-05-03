package commands;

import exceptions.InvalidInputException;
import exceptions.InvalidParamsCount;
import utils.CommandsManager;
import utils.Storage;
import utils.UserInterface;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ExecuteScript extends Command {
    public ExecuteScript() {
        command = "execute_script";
        helpText = "Execute commands from file";
    }

    @Override
    public void execute(UserInterface cli, Storage storage, String[] args) throws IOException {
        if (args.length != 1) {
            throw new InvalidParamsCount("");
        }
        File file = new File(args[0]);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        BufferedReader r;
        Scanner scanner;

        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            r = new BufferedReader(new InputStreamReader(bis, UTF_8));

            // Define scanner to prevent stuck scanning in readStudyGroup
            scanner = new Scanner(r);
            CommandsManager cmdManager = new CommandsManager();
            String line;
            UserInterface cmdCli = new UserInterface(
                    r,
                    new OutputStreamWriter(System.out, UTF_8),
                    scanner
            );

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                cmdManager.executeCommand(cmdCli, storage, line);
            }
        } catch (FileNotFoundException e) {
            throw new InvalidInputException("File not found");
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (fis != null && bis != null) {
                try {
                    fis.close();
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Only server-side command
    @Override
    public String execute(Storage storage, Object data) throws IOException {
        return null;
    }
}



