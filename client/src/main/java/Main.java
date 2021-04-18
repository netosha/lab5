import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;



class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.startConnection("localhost", 8080);

        Scanner scanner = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        Writer out = new OutputStreamWriter(System.out, StandardCharsets.UTF_8);
        while (scanner.hasNext()) {
            String response = client.sendMessage(scanner.nextLine());
            System.out.println(response);
        }
    }
}