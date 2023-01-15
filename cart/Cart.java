package cart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private String customerName;
    private boolean login;
    private List<String> items = new ArrayList<>();

    public Cart(String customerName) {
        this.customerName = customerName;
        this.login = false;
    }

    public Cart() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public void listItems() {
        int count = 1;
        for (String s : this.items) {
            System.out.println(count + ". " + s.toString());
            count++;
        }
    }

    public void addItems(String input) {
        String[] itemList = input.trim().split(" ");
        for (int i = 0; i < itemList.length; i++) {
            items.add(itemList[i]);
        }
        System.out.println(String.join(", ", itemList) + " added to the cart.");
    }

    public void delItems(String input) {
        String[] str = input.trim().split(" ");
        int[] arr = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            int num = Integer.parseInt(str[i]);
            arr[i] = num;
            try {
                items.remove(arr[i] - 1);
            } catch (Exception e) {
                System.out.println("Item number " + num + " does not exist! Try again.");
            }
        }
    }

    public void loadCart(String username, String dir) throws IOException {
        File file = new File(dir + "/" + username + ".cart");
        this.customerName = username;

        if (file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            System.out.println(username + " shopping cart loaded.");
            login = true;
            if (line == null) {
                System.out.println(username + " shopping cart is empty.");
                items.clear();
            } else {
                items.clear();
                String[] itemList = line.trim().split(" ");
                for (int i = 0; i < itemList.length; i++) {
                    items.add(itemList[i]);
                }
            }
            br.close();
        } else {
            file.createNewFile();
            if (file.createNewFile()) {
                System.out.println("New shopping cart for " + username + " created.");
            }
        }
    }

    public void saveCart(String dir) throws IOException {
        FileWriter fw = new FileWriter(dir + "/" + customerName + ".cart");
        for (String s : this.items) {
            fw.write(s + " ");
        }
        fw.close();
        System.out.println("cart contents saved to " + customerName);
    }

}
