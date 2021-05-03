package commands;

import network.Client;
import utils.CommandsManager;
import utils.Storage;
import utils.UserInterface;

import java.io.IOException;

public class Exit extends Command {
    public Exit() {
        command = "exit";
        helpText = "Close program (without save in file)";
    }

    @Override
    public void execute(UserInterface cli, Client client, String[] args) throws IOException {
        System.exit(1);

    }
}