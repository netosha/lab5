package network;

import com.thoughtworks.xstream.XStream;
import utils.Storage;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private ServerSocketChannel serverSocketChannel;
    private final Selector selector = Selector.open();
    utils.CommandsManager cmdManager = new utils.CommandsManager();
    Storage storage = new Storage();

    public Server() throws IOException {
    }

    public void send(SocketChannel sc){

    }

    public void listen() throws IOException {
        // https://www.developer.com/java/data/what-is-non-blocking-socket-programming-in-java/
        CharsetEncoder enc = StandardCharsets.UTF_8.newEncoder();
        SelectionKey key = null;

        while (true) {
            if (selector.select() <= 0)
                continue;
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            while (iterator.hasNext()) {
                key = (SelectionKey) iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    SocketChannel sc = serverSocketChannel.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.
                            OP_READ);
                    System.out.println("Connection Accepted: " + sc.getLocalAddress());
                }
                if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();

                    sc.configureBlocking(false);
                    ByteBuffer bb = ByteBuffer.allocate(1024);
                    sc.read(bb);
                    String result = new String(bb.array()).trim();
                    if (result.length() <= 0) {
                        System.out.println(result);
                        sc.close();
                        System.out.println("Connection closed...");
                    } else {
                        bb.flip();
                        try {
                            String payload = cmdManager.executeCommand(storage, result);
                            System.out.printf("Payload: %s", payload);
                            sc.write(ByteBuffer.wrap(payload.getBytes(StandardCharsets.UTF_8)));
                        } catch (Exception e) {
                            e.printStackTrace();
                            sc.write(enc.encode(CharBuffer.wrap("Command execution failed: " + e.toString())));
                        }
                        sc.write(enc.encode(CharBuffer.wrap("\n")));
                        System.out.println("msg: " + result + "\nlen: " + result.length());
                    }
                }
            }
        }
    }

    public void start(int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8080));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("server started on localhost:8080");
    }

    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(int port) {
        try {
            Server server = new Server();
            server.start(port);
            server.listen();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
