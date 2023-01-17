package cart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cart2 {

    private String customerName;
    private boolean login;
    private List<String> items = new ArrayList<>();

    public Cart2(String customerName) {
        this.customerName = customerName;
        this.login = false;
    }

    public Cart2() {
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

    public String listItems() {
        String temp = "";
        int count = 1;
        for (String s : this.items) {
            temp += count + ". " + s.toString() + "\n";
            count++;
        }
        return temp.trim();
    }

    public String addItems(String input) {
        String temp = "";
        String[] itemList = input.trim().split(" ");
        for (int i = 0; i < itemList.length; i++) {
            items.add(itemList[i]);
        }
        temp = temp + String.join(", ", itemList) + " added to the cart.";
        return temp;
    }

    public String delItems(String input) {
        String temp = "";
        String[] str = input.trim().split(" ");
        int[] arr = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            int num = Integer.parseInt(str[i]);
            arr[i] = num;
            try {
                temp = items.get(arr[i] - 1);
                items.remove(arr[i] - 1);
                return (temp + " removed from the cart");
            } catch (Exception e) {
                return ("Item number " + num + " does not exist! Try again.");
            }
        }
        return temp;
    }

    public String loadCart(String username, String dir) throws IOException {
        File file = new File(dir + "/" + username + ".cart");
        this.customerName = username;

        if (file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
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
            return (username + " shopping cart loaded.");
        } else {
            items.clear();
            file.createNewFile();
            if (file.createNewFile()) {
                return ("New shopping cart for " + username + " created.");
            } else {
                return "";
            }
        }
    }

    public String saveCart(String dir) throws IOException {
        FileWriter fw = new FileWriter(dir + "/" + customerName + ".cart");
        for (String s : this.items) {
            fw.write(s + " ");
        }
        fw.close();
        return ("cart contents saved to " + customerName);
    }

}
