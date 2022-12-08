import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.*;

/**
 * Connor Underwood, Zeyad Adham, Suhani Yadav, Neel Acharya
 *
 * A customer class
 */
public class Customer {

    private String pin; // -> every customer pin is 5 digits!

    private String email; // can change

    private String password; // can change

    private ArrayList<Shoe> purchaseHistory; // this will be used for this Customer object's purchase history

    int totalAmountPurchased; // num of shoes this customer has purchased


    public Customer(String pin, String email, String password) {
        this.pin = pin;
        this.email = email;
        this.password = password;
        this.purchaseHistory = new ArrayList<>();
        this.totalAmountPurchased = 0;
    }

    public void addPurchaseHistory(Shoe shoe) {
        this.purchaseHistory.add(shoe);
    }
    public void addTotalAmount(int amount) {
        this.totalAmountPurchased += amount;
    }
    public int getTotalAmount() {
        return this.totalAmountPurchased;
    }
    public ArrayList<Shoe> getPurchaseHistory() {
        return this.purchaseHistory;
    }

    public String getPin() {
        return this.pin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        // Task 1 --> read and write from accounts.csv
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.csv"))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                if (line.contains(this.email)) {
                    line = arr[0] + "," + newEmail + "," + arr[2] + ",";
                }
                lines.add(line);
            }
        } catch (IOException io) {
            System.out.println("Error reading to the accounts.csv file.");
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter("accounts.csv"))) {
            for (int i = 0; i < lines.size(); i++) {
                writer.println(lines.get(i));
                writer.flush();
            }
        } catch (IOException io) {
            System.out.println("Error writing to the accounts.csv file.");
        }
        // Task 2 --> read and write from stores.csv
        ArrayList<String> customerInfo = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("stores.csv"))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                if (line.contains(this.email)) {
                    line = arr[0] + "," + newEmail + ",";
                    for (int i = 2; i < arr.length; i++) {
                        line += arr[i] + ",";
                    }
                }
                customerInfo.add(line);
            }
        } catch (IOException io) {
            System.out.println("Error reading to the stores.csv file.");
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter("stores.csv"))) {
            for (int i = 0;i < customerInfo.size(); i++) {
                writer.println(customerInfo.get(i));
                writer.flush();
            }
        } catch (IOException io) {
            System.out.println("Error writing to the stores.csv file.");
        }
        this.email = newEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
// Task 1 --> read and write from accounts.csv
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.csv"))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                if (line.contains(this.password) && line.contains(this.pin)) {
                    line = arr[0] + "," + arr[1] + "," + newPassword + ",";
                }
                lines.add(line);
            }
        } catch (IOException io) {
            System.out.println("Error reading to the accounts.csv file.");
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter("accounts.csv"))) {
            for (int i = 0; i < lines.size(); i++) {
                writer.println(lines.get(i));
            }
        } catch (IOException io) {
            System.out.println("Error writing to the accounts.csv file.");
        }
        // Task 2 --> read and write from stores.csv
        ArrayList<String> customerInfo = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("stores.csv"))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                if (line.contains(this.password) && line.contains(this.pin)) {
                    line = arr[0] + "," + arr[1] + "," + newPassword + ",";
                    for (int i = 3; i < arr.length; i++) {
                        line += arr[i] + ",";
                    }
                }
                customerInfo.add(line);
            }
        } catch (IOException io) {
            System.out.println("Error reading to the stores.csv file.");
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter("stores.csv"))) {
            for (int i = 0;i < customerInfo.size(); i++) {
                writer.println(customerInfo.get(i));
                writer.flush();
            }
        } catch (IOException io) {
            System.out.println("Error writing to the stores.csv file.");
        }
        this.password = newPassword;
    }

    public String viewPurchaseHistory(boolean export, String filePath) {
        if (export) {
            File f = new File(filePath);
            if (f.exists()) {
                if (purchaseHistory.size() == 0) {
                    return "You have no shoes in your purchase history.";
                } else {
                    try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                        writer.println("Total Shoes Purchased: " + totalAmountPurchased);
                        writer.flush();
                        for (int i = 0; i < purchaseHistory.size(); i++) {
                            writer.println("Shoe: " + purchaseHistory.get(i).getName());
                            writer.flush();
                            writer.println("Price: " + purchaseHistory.get(i).getPrice());
                            writer.flush();
                        }
                        return "Success";
                    } catch (IOException io) {
                        return "Error writing to the " + this.email + ".txt file.";
                    }
                }
            } else {
                try{
                    boolean b = f.createNewFile();
                    return "";
                } catch (IOException io) {
                    return "Error creating " + this.email + ".txt.";
                }
            }
        } else {
            if (purchaseHistory.size() == 0) {
                return "You have no shoes in your purchase history.";
            } else {
                String history = "Total Shoes Purchased: " + totalAmountPurchased + "\n";
                for (int i = 0; i < purchaseHistory.size(); i++) {
                    history += "Shoe: " + purchaseHistory.get(i).getName() + "\n";
                    history += "Price: " + purchaseHistory.get(i).getPrice() + "\n";
                }
                return history;
            }
        }
    }


    /**
     * @param search Boolean value. Will ask in main if user would like to search
     * @param searchString If search is true, set searchString to their input. Otherwise, set searchString to "" empty string
     */
    public String viewMarket(boolean search, String searchString, int searchNum) {
        if (search) {
//             1: search by store name
//             2: search by name
//             3: search by description
//             4: sort by price
//             5: sort by quantity
            if (searchNum == 1) { // SEARCH BY STORE
                ArrayList<String> storeStrings = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader("market.csv"))) {
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        String[] arr = line.split(",");
                        if (arr.length > 4) {
                            String storeName = arr[3].toLowerCase();
                            if (storeName.contains(searchString.toLowerCase())) {
                                line = "Seller: " + arr[1]+ "\nStore: " + arr[3] + "\n";
                                for (int i = 4; i < arr.length; i+=4) {
                                    line += "Shoe Name: " + arr[i] + "\n" + "Price: $" +arr[i+2] + "\n";
                                }
                                storeStrings.add(line);
                            }
                        }
                    }
                } catch (IOException io) {
                    return "Error reading to the market.csv file.";
                }
                if (storeStrings.size() == 0) {
                    return "Sorry, we could find any shoes based on that search";
                } else {
                    String s = "";
                    for (int i = 0; i < storeStrings.size(); i++) {
                        s += storeStrings.get(i);
                    }
                    return s;
                }
            }
            else if (searchNum == 2) {
                ArrayList<String> shoeStrings = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader("market.csv"))) {
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        String[] arr = line.split(",");
                        if (arr.length > 4) {
                            String shoeName = arr[4].toLowerCase();
                            if (shoeName.contains(searchString.toLowerCase())) {
                                line = "Seller: " + arr[1]+ "\nStore: " + arr[3] + "\n";
                                for (int i = 4; i < arr.length; i+=4) {
                                    if (arr[i].toLowerCase().contains(searchString.toLowerCase())) {
                                        line += "Shoe Name: " + arr[i] + "\n" + "Price: $" +arr[i+2] + "\n";
                                    }
                                }
                                shoeStrings.add(line);
                            }
                        }
                    }
                } catch (IOException io) {
                    return "Error reading to the market.csv file.";
                }
                if (shoeStrings.size() == 0) {
                    return "Sorry, we could find any shoes based on that search";
                } else {
                    String s = "";
                    for (int i = 0; i < shoeStrings.size(); i++) {
                        s += shoeStrings.get(i) + "\n";
                    }
                    return s;
                }
            }
            else if (searchNum == 3) {
                ArrayList<String> descStrings = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader("market.csv"))) {
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        String[] arr = line.split(",");
                        if (arr.length > 4) {
                            String descName = arr[5].toLowerCase();
                            if (descName.contains(searchString.toLowerCase())) {
                                line = "Seller: " + arr[1]+ "\nStore: " + arr[3] + "\n";
                                for (int i = 4; i < arr.length; i+=4) {
                                    if (arr[i+1].toLowerCase().contains(searchString.toLowerCase())) {
                                        line += "Shoe Name: " + arr[i] + "\n" + "Price: $" +arr[i+2] + "\n";
                                    }
                                }
                                descStrings.add(line);
                            }
                        }
                    }
                } catch (IOException io) {
                    return "Error reading to the market.csv file.";
                }
                if (descStrings.size() == 0) {
                    return "Sorry, we could find any shoes based on that search";
                } else {
                    String s = "";
                    for (int i = 0; i < descStrings.size(); i++) {
                        s += descStrings.get(i);
                    }
                    return s;
                }
            }
            else if (searchNum == 4) {
                ArrayList<String> priceStrings = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader("market.csv"))) {
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        String[] arr = line.split(",");
                        if (arr.length > 4) {
                            double price = Double.parseDouble(arr[6]);
                            if (price <= Double.parseDouble(searchString)) {
                                line = "Seller: " + arr[1] + "\nStore: " + arr[3] + "\n";
                                for (int i = 4; i < arr.length; i+=4) {
                                    if (Double.parseDouble(arr[i+2]) <= Double.parseDouble(searchString)) {
                                        line += "Shoe Name: " + arr[i] + "\n" + "Price: $" + arr[i+2] + "\n";
                                    }
                                }
                                priceStrings.add(line);
                            }
                        }
                    }
                } catch (IOException io) {
                    return "Error reading to the market.csv file.";
                }
                if (priceStrings.size() == 0) {
                    return "Sorry, we could find any shoes based on that search";
                } else {
                    String s = "";
                    for (int i = 0; i < priceStrings.size(); i++) {
                        s += priceStrings.get(i);
                    }
                    return s;
                }
            }
            else {
                ArrayList<String> quantityStrings = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader("market.csv"))) {
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        String[] arr = line.split(",");
                        if (arr.length > 4) {
                            int quantity = Integer.parseInt(arr[7]);
                            if (quantity >= Integer.parseInt(searchString)) {
                                line = "Seller: " + arr[1]+ "\nStore: " + arr[3] + "\n";
                                for (int i = 4; i < arr.length; i+=4) {
                                    if (Integer.parseInt(arr[i+3]) >= Integer.parseInt(searchString)) {
                                        line += "Shoe Name: " + arr[i] + "\n" + "Price: $" + arr[i+2] + "\n";
                                    }
                                }
                                quantityStrings.add(line);
                            }
                        }
                    }
                } catch (IOException io) {
                    return "Error reading to the market.csv file.";
                }
                if (quantityStrings.size() == 0) {
                    return "Sorry, we could find any shoes based on that search";
                } else {
                    String s = "";
                    for (int i = 0; i < quantityStrings.size(); i++) {
                        s += quantityStrings.get(i);
                    }
                    return s;
                }
            }
        }
        else {
            ArrayList<String> market = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("market.csv"))) {
                String line = "";
                while ((line = reader.readLine()) != null) {
                    String[] arr = line.split(",");
                    if (arr.length > 4) {
                        String email = arr[1];
                        String store = arr[3];
                        line = "Seller: " + email + "\nStore: " + store + "\n";
                        for (int i = 4; i < arr.length; i+=4) {
                            line += "Shoe Name: " + arr[i] + "\nPrice: $" + arr[i+2] + "\n";
                        }
                        market.add(line);
                    }

                }
            } catch (IOException io) {
                return "Error reading to the market.csv file.";
            }
            File f = new File(this.email + ".txt");
            try {
                boolean b = f.createNewFile();
            } catch (IOException io) {
                return "Error creating new file.";
            }
            try (PrintWriter writer = new PrintWriter(new FileWriter(f))) {
                String s = "";
                for (int i = 0; i < market.size(); i++) {
                    writer.println(market.get(i));
                    s += market.get(i);
                }
                return s;
            } catch (IOException io) {
                return "Error writing to file.";
            }
        }
    } // use to search and view the market in market.csv





    /**
     * @param shoe Shoe object this Customer object is purchasing
     * @param store Store object from which the Shoe object originates
     * @param amount Amount of Shoe object we are purchasing
     */
    public void purchase(Shoe shoe, Store store, int amount) {
        // Task 1 --> change quantity of shoe in market.csv by reading and writing back --> COMPLETE!
        ArrayList<String> updatedPurchase = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("market.csv"))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr.length > 4) {
                    if (arr[3].equalsIgnoreCase(store.getName()) && arr[0].equals(store.getSeller().getPin())) {
                        for (int i = 4; i < arr.length; i+=4) {
                            Shoe tempShoe = new Shoe(store, arr[i], arr[i+1], Double.parseDouble(arr[i+2]), Integer.parseInt(arr[i+3]));
                            if (tempShoe.equalsShoe(shoe)) {
                                store.removeShoe(tempShoe);
                                store.addShoe(new Shoe(tempShoe.getStore(), tempShoe.getName(), tempShoe.getDescription(), tempShoe.getPrice(),
                                        (tempShoe.getQuantity() - amount)));
                            } else {
                                store.addShoe(tempShoe); // this may be a problem, might have to put in else block
                            }

                        }
                        line = store.getSeller().getPin() + "," + store.getSeller().getEmail() + "," +
                                store.getSeller().getPassword() + "," + store.getName() + ",";
                        for (Shoe shoo : store.getShoes()) {
                            line += shoo.getName() + "," +  shoo.getDescription() + "," + shoo.getPrice() + "," + shoo.getQuantity() + ",";
                        }
                    }
                }
                updatedPurchase.add(line);

            }
        } catch (IOException io) {
            System.out.println("Error reading to the market.csv file.");
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter("market.csv"))) {
            for (int i = 0; i < updatedPurchase.size(); i++) {
                writer.println(updatedPurchase.get(i));
                writer.flush();
            }
        } catch (IOException io) {
            System.out.println("Error writing to the market.csv file");
        }
        // Task 2 --> write to stores.csv in the specific format
        shoe.setQuantity(shoe.getQuantity() - amount); // now we can set it because we wrote to the file
        ArrayList<String> updatedStores = new ArrayList<>();
        boolean append = true;
        try (BufferedReader reader = new BufferedReader(new FileReader("stores.csv"))) {
            String line = "";
            store.setSales(store.getSales() + amount);
            store.setRevenue(store.getRevenue() + (amount * shoe.getPrice()));
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                if (line.contains(this.pin) && line.contains(store.getSeller().getPin()) &&
                        line.contains(store.getName()) && !line.contains(shoe.getName())) {
                    line = this.pin + "," + this.email + "," + this.password + "," + store.getSeller().getPin() + "," +
                            store.getSeller().getEmail() + "," + store.getSeller().getPassword() + "," + store.getName() + "," + store.getSales() + "," +
                            store.getRevenue() + "," + (Integer.parseInt(arr[9]) + amount)+ ",";
                    for (int i = 10; i < arr.length; i++) {
                        line += arr[i] + ",";
                    }
                    line += shoe.getName() + "," + shoe.getDescription() + "," + shoe.getPrice()
                            + "," + shoe.getQuantity() + ",";
                    append = false;
                }
                else if (line.contains(this.pin) && line.contains(store.getSeller().getPin()) &&
                        line.contains(store.getName()) && line.contains(shoe.getName())) {
                    line = this.pin + "," + this.email + "," + this.password + "," + store.getSeller().getPin() + "," +
                            store.getSeller().getEmail() + "," + store.getSeller().getPassword() + "," + store.getName() + "," + store.getSales() + "," +
                            store.getRevenue() + "," + (Integer.parseInt(arr[9]) + amount)+ ",";
                    Store tempStore = new Store(arr[6], new Seller(arr[3], arr[4], arr[5]));
                    for (int i = 10; i < arr.length; i+=4) {
                        tempStore = new Store(arr[6], new Seller(arr[3], arr[4], arr[5]));
                        tempStore.addShoe(new Shoe(tempStore, arr[i], arr[i+1], Double.parseDouble(arr[i+2]), Integer.parseInt(arr[i+3])));
                    }
                    for (int i = 0; i < tempStore.getShoes().size(); i++) {
                        if (tempStore.getShoes().get(i).getName().equalsIgnoreCase(shoe.getName())) {
                            Shoe shoe1 = tempStore.getShoes().get(i);
                            shoe1.setQuantity(shoe1.getQuantity() - amount);
                            tempStore.shoes.set(i, shoe1);
                            break;
                        }
                    }
                    for (int i = 0; i < tempStore.getShoes().size(); i++) {
                        line += tempStore.getShoes().get(i).getName() + "," + tempStore.getShoes().get(i).getDescription() + ","
                                + tempStore.getShoes().get(i).getPrice() + "," + tempStore.getShoes().get(i).getQuantity();
                    }
                    append = false;
                }
                updatedStores.add(line);
            }
        } catch (IOException io) {
            System.out.println("Error reading to the market.csv file.");
        }
        if (append) {
            try (PrintWriter writer = new PrintWriter(new FileWriter("stores.csv", true))) {
                writer.println(this.pin + "," + this.email + "," + this.password + "," + store.getSeller().getPin() + "," +
                        store.getSeller().getEmail() + "," + store.getSeller().getPassword() + "," + store.getName() + "," + store.getSales() + "," +
                        store.getRevenue() + "," + amount + "," + shoe.getName() + "," + shoe.getDescription() + "," + shoe.getPrice() + "," + shoe.getQuantity() + ",");
                writer.flush();
            } catch (IOException io) {
                System.out.println("Error writing to the stores.csv file.");
            }
        }
        else {
            try (PrintWriter writer = new PrintWriter(new FileWriter("stores.csv"))) {
                for (int i = 0; i < updatedStores.size(); i++) {
                    writer.println(updatedStores.get(i));
                    writer.flush();
                }
            } catch (IOException io) {
                System.out.println("Error writing to the stores.csv file.");
            }
        }
        this.totalAmountPurchased += amount;
        this.purchaseHistory.add(shoe);

        // add to this Customer object's arrayList of previously purchased
    }

    public Seller findSeller(String sellerEmail) {
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.csv"))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr[1].equalsIgnoreCase(sellerEmail)) {
                    return new Seller(arr[0], arr[1], arr[2]);
                }
            }
        } catch (IOException io) {
            System.out.println("Error reading to the accounts.csv file.");
        }
        return null;
    } // finds and returns a Seller object for us
    // we use this in main when a customer is wanting to purchase a Shoe

    /**
     * @param shoeName shoeName we are looking for
     * @param storeName storeName we are looking for
     * @return returns Shoe object we are looking for
     */
    public Shoe findShoe(String shoeName, String storeName) {
        try (BufferedReader reader = new BufferedReader(new FileReader("market.csv"))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr.length > 4) {
                    if (arr[3].equalsIgnoreCase(storeName)) {
                        for (int i = 4; i < arr.length; i+=4) {
                            if (arr[i].equalsIgnoreCase(shoeName)) {
                                Seller seller = new Seller(arr[0], arr[1], arr[2]);
                                Store store = new Store(arr[3], seller);
                                return new Shoe(store, arr[i], arr[i+1], Double.parseDouble(arr[i+2]),
                                        Integer.parseInt(arr[i+3]));
                            }
                        }
                    }
                }
            }
        } catch (IOException io) {
            System.out.println("Error reading to the market.csv file.");
            return null;
        }
        return null;
    } // used to find a shoe when looking to purchase

}
