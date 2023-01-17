package cart;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
    
    public static void main(String[] args) throws UnknownHostException, IOException {
        
        int port = Integer.parseInt(args[1]);
        String dir = args[0];
        System.out.println("Using " + dir + " directory for persistence\n Connection received...");

        ServerSocket ss = new ServerSocket(port);
        System.out.println("Starting shopping cart server on port " + port);

        File newDirectory = new File(dir);
        Cart2 user = new Cart2();
        boolean exit = false;

        if(newDirectory.exists()) {
            System.out.println("Using " + dir + " directory for persistence");
        } else {
            newDirectory.mkdir();
        }

        Socket s = ss.accept(); // establish connection and wait for client to connect

        try (InputStream is = s.getInputStream()) {
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            OutputStream os = s.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);
            String input = "";

            while (!exit) {
                input = dis.readUTF();
                String selection = input.trim().split(" ", 2)[0];
                String options = "";
                try {
                    options = input.trim().split(" ", 2)[1];
                } catch (Exception e) {
                }
                
                switch (selection.trim()) {
                    case "load": dos.writeUTF(user.loadCart(options, dir));
                    dos.flush();
                        break;
                    case "add": dos.writeUTF(user.addItems(options)); 
                    dos.flush();
                        break;
                    case "list": dos.writeUTF(user.listItems());
                    dos.flush();
                        break;
                    case "delete": dos.writeUTF(user.delItems(options));
                    dos.flush();
                        break;
                    case "save": dos.writeUTF(user.saveCart(dir));
                    dos.flush();
                        break;
                    case "exit":
                        dos.writeUTF("App is exiting. Closing connection.");
                        dos.flush();
                        exit = true;
                        break;
                    default:
                        dos.writeUTF("Use one of the valid commands instead.");
                        dos.flush();
                        break;
                }
            }

            dos.close();
            bos.close();
            os.close();
            dis.close();
            bis.close();
            is.close();
        } catch (EOFException ex) {
            s.close();
            ss.close();
        }
    }
}
