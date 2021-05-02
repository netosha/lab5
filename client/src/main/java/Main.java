import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        GreetClient client = new GreetClient();
        String host = "localhost";
        Integer port = 8080;

        do{
            try{
                client.startConnection(host, port);
                System.out.printf("Connection established (%s:%d)%n", host, port);
                break;
            }catch (java.net.ConnectException e){
                System.out.println("Connection refused. Trying again...");
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }while (true);

        Writer writer = new OutputStreamWriter(System.out, StandardCharsets.UTF_8);
        Scanner scanner = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        while (true) {
            if(scanner.hasNextLine()){
                String msg = client.sendMessage(scanner.nextLine());
                System.out.println(msg);
            }
        }
    }
}