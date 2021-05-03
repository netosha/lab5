package network;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import commands.Clear;
import utils.*;
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
    CommandsManager cmdManager;
    Storage storage;

    public Server(CommandsManager cmd, Storage strg) throws IOException {
        cmdManager = cmd;
        storage = strg;
    }

    public void listen() throws IOException {
        // https://www.developer.com/java/data/what-is-non-blocking-socket-programming-in-java/
        CharsetEncoder enc = StandardCharsets.UTF_8.newEncoder();
        SelectionKey key = null;

        while (true) {
            try {
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
                        String rawString = new String(bb.array()).trim();

                        if (rawString.length() <= 0) {
//                            System.out.println(rawString);
                            sc.close();
                            System.out.println("Connection closed...");
                        } else {
                            bb.flip();
                            try {
                                XStream xstream = new XStream(new StaxDriver()); // does not require XPP3 library starting with Java 6
                                Message parsed = (Message) xstream.fromXML(rawString);
                                String payload = cmdManager.executeCommand(storage, parsed.command, parsed.data);
                                System.out.printf("Payload: %s\n", payload);
                                sc.write(ByteBuffer.wrap(payload.getBytes(StandardCharsets.UTF_8)));
                            } catch (Exception e) {
                                e.printStackTrace();
                                sc.write(enc.encode(CharBuffer.wrap("Command execution failed: " + e.toString())));
                            }
                            sc.write(enc.encode(CharBuffer.wrap("\n")));
                            System.out.println("msg: " + rawString + "\nlen: " + rawString.length());
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start(int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress("localhost", port));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.printf("server started on localhost:%s%n", port);
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

}
