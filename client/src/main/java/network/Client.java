package network;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String command) throws IOException {
        Message message = new Message(command, "");
        XStream xstream = new XStream(new StaxDriver());
        String req = xstream.toXML(message);
//        System.out.println(req);
        out.println(req);
        String resp = in.readLine();
        return resp;

    }

    public String sendMessage(String command, Object data) throws IOException {
        Message message = new Message(command, data);
        XStream xstream = new XStream(new StaxDriver());
        String req = xstream.toXML(message);
//        System.out.println(req);
        out.println(req);
        String resp = in.readLine();
        return resp;

    }

    public void connect(Integer port) throws InterruptedException {
        String host = "localhost";
        do{
            try{
                startConnection(host, port);
                System.out.printf("Connection established (%s:%d)%n", host, port);
                break;
            }catch (java.net.ConnectException e){
                System.out.println("Connection refused. Trying again...");
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }while (true);
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
