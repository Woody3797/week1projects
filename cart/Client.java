package cart;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args) throws UnknownHostException, IOException {

        String input = args[0];
        String user = input.trim().split("@", 0)[0];
        String host = (input.trim().split("@", 0)[1]).split(":", 0)[0];
        int port = Integer.parseInt(input.trim().split(":", 0)[1]);
        Socket socket = new Socket(host, port);
        Console cons = System.console();

        try (OutputStream os = socket.getOutputStream()) {
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            InputStream is = socket.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            String readInput = "";
            System.out.println("Connected to shopping cart server at " + host + " on " + user + " port " + port);
            System.out.println("""
                    You may input the following commands:
                    1. load - loads a user's cart
                    2. add - adds one or more items to the cart. Multiple items must be separated by a single white space
                    3. delete -  deletes an item using the item's index (from  the list command)
                    4. list - lists the contents of the cart
                    5. save - saves the contents of the shopping cart
                    6. exit - exits the shopping cart application """);

            while (!readInput.equalsIgnoreCase("exit")) {
                readInput = cons.readLine();
                dos.writeUTF(readInput);
                dos.flush();

                String line = dis.readUTF();
                System.out.println(line);
            }
            dis.close();
            bis.close();
            is.close();
            dos.close();
            bos.close();
            os.close();
        } catch (EOFException ex) {
            socket.close();
        }
    }
}
