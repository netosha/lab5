import network.*;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Server.main(8080);

        Writer writer = new OutputStreamWriter(System.out, StandardCharsets.UTF_8);
        Scanner scanner = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        while (true) {
            if(scanner.hasNextLine()){
                System.out.println(scanner.nextLine());
            }
        }

    }

}