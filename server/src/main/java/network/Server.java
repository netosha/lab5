package network;

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

    public void start(int port) throws IOException {
        // https://www.developer.com/java/data/what-is-non-blocking-socket-programming-in-java/

        CharsetEncoder enc = StandardCharsets.UTF_8.newEncoder();

        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8080));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

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
                    bb.flip();
                    sc.write(enc.encode(CharBuffer.wrap("IDI NAHOOY\n")));
                    sc.write(bb);
                    System.out.println("Message received: "
                            + result
                            + " Message length= " + result.length());
                    if (result.length() <= 0) {
                        sc.close();
                        System.out.println("Connection closed...");
                    }
                }
            }
        }
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
