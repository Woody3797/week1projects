package cart;

import java.io.Console;
import java.io.IOException;

public class Main {
    
    public static void main(String[] args) throws IOException {
        
        Console cons = System.console();
        Cart user = new Cart();
        boolean exit = false;
        String dir = args[0];

        System.out.println("""
            You may input the following commands:
            1. load - loads a user's cart
            2. add - add one or more items to the cart. Multiple  items are separated by a single white space
            3. delete -  delete an item using the item's index (from  the list command)
            4. list - list the contents of the cart
            5. save - save the contents of the shopping cart
            6. exit - exits the shopping cart application """);

        while (!exit) {
            String input = cons.readLine("> ");
            String selection = input.trim().split(" ", 2)[0];
            String options = "";
            try {
                options = input.trim().split(" ", 2)[1];
            } catch (Exception e) {
            }
            
            switch (selection.trim()) {
                case "load": user.loadCart(options, dir);
                    break;
                case "add": user.addItems(options);
                    break;
                case "list": user.listItems();
                    break;
                case "delete": user.delItems(options);
                    break;
                case "save": user.saveCart(dir);
                    break;
                case "exit":
                    System.out.println("App is exiting."); 
                    exit = true;
                    break;
                default:
                    System.out.println("Use one of the valid commands instead.");
                    break;
            }
        }
    }
}
