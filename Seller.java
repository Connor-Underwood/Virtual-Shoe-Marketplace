import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;


/**
 * Connor Underwood, Zeyad Adham, Suhani Yadav, Neel Acharya
 * <p>
 * A Seller class
 */
public class Seller {
    public ArrayList<Store> stores; // stores for each seller -- can add a store but can never remove a store

    private String pin; // unique String pin for each seller -- 4 digits, CANNOT BE CHANGED!!!

    private String email; // String email for each seller -- can be changed

    private String password; // String password for each seller -- can be changed


    /**
     * @param pin      read from market.csv
     * @param email    read from market.csv
     * @param password read from market.csv
     */
    public Seller(String pin, String email, String password) {
        this.pin = pin;
        this.stores = new ArrayList<>();
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String newEmail) {
        if (true) {
            ArrayList<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("accounts.csv"))) {
                String line = "";
                while ((line = reader.readLine()) != null) {
                    String[] arr = line.split(",");
                    if (line.contains(this.email) && line.contains(this.pin)) {
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

        }
        if (true) {
            ArrayList<String> strings = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("market.csv"))) {
                String line = "";
                while ((line = reader.readLine()) != null) {
                    String[] arr = line.split(",");
                    if (arr[1].equalsIgnoreCase(this.email) && line.contains(this.pin)) {
                        if (arr.length > 3) {
                            line = arr[0] + "," + newEmail + "," + arr[2] + "," + arr[3] + ",";
                            if (arr.length > 4) {
                                for (int i = 4; i < arr.length; i++) {
                                    line += arr[i] + ",";
                                }
                            }
                        } else {
                            line = arr[0] + "," + newEmail + "," + arr[2] + ",";
                        }
                    }
                    strings.add(line);
                }
            } catch (IOException io) {
                System.out.println("Error reading to the market.csv file.");
            }
            try (PrintWriter writer = new PrintWriter(new FileWriter("market.csv"))) {
                for (int i = 0; i < strings.size(); i++) {
                    writer.println(strings.get(i));
                    writer.flush();
                }
            } catch (IOException io) {
                System.out.println("Error writing to the market.csv file.");
            }
        }
        if (true) {
            ArrayList<String> strings = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("stores.csv"))) {
                String line = "";
                while ((line = reader.readLine()) != null) {
                    String[] arr = line.split(",");
                    if (arr[4].equalsIgnoreCase(this.email) && line.contains(this.pin)) {
                        line = arr[0] + "," + arr[1] + "," + arr[2] + "," + arr[3] + "," + newEmail + ",";
                        for (int i = 5; i < arr.length; i++) {
                            line += arr[i] + ",";
                        }

                    }
                    strings.add(line);
                }
            } catch (IOException io) {
                System.out.println("Error reading to the stores.csv file.");
            }
            try (PrintWriter writer = new PrintWriter(new FileWriter("stores.csv"))) {
                for (int i = 0; i < strings.size(); i++) {
                    writer.println(strings.get(i));
                    writer.flush();
                }
            } catch (IOException io) {
                System.out.println("Error writing to the stores.csv file.");
            }
        }
        this.email = newEmail;

    } // in event of change email

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String newPassword) {
        if (true) {
            ArrayList<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("accounts.csv"))) {
                String line = "";
                while ((line = reader.readLine()) != null) {
                    String[] arr = line.split(",");
                    if (line.contains(this.email) && line.contains(this.pin)) {
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
                    writer.flush();
                }
            } catch (IOException io) {
                System.out.println("Error writing to the accounts.csv file.");
            }

        }
        if (true) {
            ArrayList<String> strings = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("market.csv"))) {
                String line = "";
                while ((line = reader.readLine()) != null) {
                    String[] arr = line.split(",");
                    if (arr[1].equalsIgnoreCase(this.email) && line.contains(this.pin)) {
                        if (arr.length > 3) {
                            line = arr[0] + "," + arr[1] + "," + newPassword + "," + arr[3] + ",";
                            if (arr.length > 4) {
                                for (int i = 4; i < arr.length; i++) {
                                    line += arr[i] + ",";
                                }
                            }
                        } else {
                            line = arr[0] + "," + arr[1] + "," + newPassword + ",";
                        }
                    }
                    strings.add(line);
                }
            } catch (IOException io) {
                System.out.println("Error reading to the market.csv file.");
            }
            try (PrintWriter writer = new PrintWriter(new FileWriter("market.csv"))) {
                for (int i = 0; i < strings.size(); i++) {
                    writer.println(strings.get(i));
                    writer.flush();
                }
            } catch (IOException io) {
                System.out.println("Error writing to the market.csv file.");
            }
        }
        if (true) {
            ArrayList<String> strings = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("stores.csv"))) {
                String line = "";
                while ((line = reader.readLine()) != null) {
                    String[] arr = line.split(",");
                    if (arr[4].equalsIgnoreCase(this.email) && line.contains(this.pin)) {
                        line = arr[0] + "," + arr[1] + "," + arr[2] + "," + arr[3] + "," + arr[4] + "," + newPassword + ",";
                        for (int i = 6; i < arr.length; i++) {
                            line += arr[i] + ",";
                        }

                    }
                    strings.add(line);
                }
            } catch (IOException io) {
                System.out.println("Error reading to the stores.csv file.");
            }
            try (PrintWriter writer = new PrintWriter(new FileWriter("stores.csv"))) {
                for (int i = 0; i < strings.size(); i++) {
                    writer.println(strings.get(i));
                    writer.flush();
                }
            } catch (IOException io) {
                System.out.println("Error writing to the stores.csv file.");
            }
        }
        this.password = newPassword;
    } // in event of change password

    public String getPin() {
        return this.pin;
    }

    /**
     * @param obj An object we use to check if this Seller object is equal to the object
     * @return Returns a boolean value determining if this Seller object is equal to the object passed as a
     * parameter
     */
    public boolean equalsSeller(Object obj) {
        if (obj instanceof Seller) {
            Seller seller = (Seller) obj;
            return ((seller.getStores().equals(this.stores)) &&
                    (seller.getEmail().equals(this.email)) && seller.getPin().equals(this.pin)
                    && (seller.getPassword().equals(this.password)));
        }
        return false;
    }

    /**
     * @param store A store object we use to check this Seller object's ArrayList of Stores
     * @return Returns a boolean value determining if this Seller object contains the Store object passed as a
     * parameter
     */
    public boolean checkStore(Store store) {
        for (Store s : stores) {
            if (s.getName().equals(store.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param store     The store object we want to add to this Seller object's ArrayList of Stores
     * @param writeFile A boolean determining if we want to write this data to the market.csv file or not
     * @return Returns a boolean value determining if the Store object was successfully added to
     * this Seller's ArrayList of Stores
     */
    public boolean addStore(Store store, boolean writeFile) {
        if (writeFile) {
            if (!checkStore(store)) {
                this.stores.add(store);
                try (PrintWriter writer = new PrintWriter(new FileWriter("market.csv", true))) {
                    writer.println(this.pin + "," + this.email + "," + this.password + "," + store.getName() + ",");
                    writer.flush();
                    return true;
                } catch (IOException ioException) {
                    System.out.println("Error writing to the market.csv file.");
                    return false;
                }
            } else {
                // seller already owns store
                return false;
            }
        } else {
            if (!checkStore(store)) {
                this.stores.add(store);
                return true;
            } else {
                return false;
            }
        }

    } // in event of adding a store

    /**
     * @param store     The Store object we want to add the Shoe to
     * @param shoe      The Shoe object we are adding to the specific Store object
     * @param writeFile A boolean value determining if we want to write this data to a file or not
     * @return Returns a boolean determining whether the Shoe object successfully added to the Store object
     */
    public boolean addShoe(Store store, Shoe shoe, boolean writeFile) {
        if (writeFile) { // if we want to write to market.csv file
            if (checkStore(store)) { // we need to check if this Seller object owns the Store object we pass through
                if (!store.checkShoe(shoe)) { // we need to check if this store already owns this Shoe object
                    int index = stores.indexOf(store);
                    Store s = stores.get(index);
                    if (s.addShoe(shoe)) {
                        stores.set(index, s);
                    } else {
                        return false;
                    }


                    ArrayList<String> updatedMarket = new ArrayList<>();
                    try (BufferedReader reader = new BufferedReader(new FileReader("market.csv"))) {
                        String line = "";
                        while ((line = reader.readLine()) != null) { // read from current market.csv
                            if (line.startsWith(this.pin) && line.contains(store.getName())) { // find Store object we want
                                // to add to
                                line += shoe.getName() + "," + shoe.getDescription() + "," + shoe.getPrice() + ","
                                        + shoe.getQuantity() + ",";
                                // adding shoe fields to line
                            }
                            updatedMarket.add(line); // this ArrayList should have all lines including the one
                            // we modificed
                        }
                    } catch (IOException ioException) {
                        System.out.println("Error reading to the market.csv file.");
                        return false;
                    }
                    try (PrintWriter writer = new PrintWriter(new FileWriter("market.csv"))) {
                        for (int i = 0; i < updatedMarket.size(); i++) {
                            writer.println(updatedMarket.get(i));
                            writer.flush();
                        }
                        writer.close();
                        return true;
                    } catch (IOException ioException) {
                        System.out.println("Error writing to the market.csv file.");
                        return false;
                    }
                } else {
                    // store already owns this Shoe object
                    System.out.println(store.getName() + " already owns this shoe!");
                    return false;
                }
            } else {
                // seller doesn't own store, can't add a shoe to a store they don't own
                System.out.println("You don't own this store!");
                return false;
            }
        } else {
            if (checkStore(store)) {
                if (!store.checkShoe(shoe)) {
                    int index = stores.indexOf(store);
                    Store s = stores.get(index);
                    s.addShoe(shoe);
                    stores.set(index, s);
                    return true;
                } else {
                    // store already owns this shoe object
                    System.out.println(store.getName() + " already owns this shoe!");
                    return false;
                }
            } else {
                // seller doesn't own store, can't add a shoe to a store they don't own
                System.out.println("You don't own this store!");
                return false;
            }
        }
    }

    /**
     * @param store     The store object that contains the shoe we want to remove
     * @param shoe      The Shoe object we want to remove this Seller object
     * @param writeFile Boolean value determining if we want to write this to the file or not
     * @return Returns a boolean value determining if the
     */
    public boolean removeShoe(Store store, Shoe shoe, boolean writeFile) {
        if (writeFile) { // if we want to write to File
            if (checkStore(store)) { // if this Seller object has the Store object
                if (store.checkShoe(shoe)) { // if the store object has the Shoe object
                    int index = this.stores.indexOf(store); // these 4 lines of code modifies this Seller object's Store arrayList DIRECTLY!!!!
                    Store tempStore = this.stores.get(index);
                    tempStore.removeShoe(shoe);
                    this.stores.set(index, tempStore);


                    ArrayList<String> updatedMarket = new ArrayList<>();
                    try (BufferedReader reader = new BufferedReader(new FileReader("market.csv"))) {
                        String line = "";
                        while ((line = reader.readLine()) != null) {
                            if (line.startsWith(this.pin) && line.toLowerCase().contains(store.getName().toLowerCase())
                                    && line.toLowerCase().contains(shoe.getName().toLowerCase())) {
                                String[] lineArray = line.split(",");
                                Store sto = new Store(lineArray[3], this);
                                for (int i = 4; i < lineArray.length; i += 4) {
                                    Shoe shoo = new Shoe(sto, lineArray[i], lineArray[i + 1], Double.parseDouble(lineArray[i + 2]),
                                            Integer.parseInt(lineArray[i + 3]));
                                    sto.addShoe(shoo);
                                }
                                for (Shoe shoeObj : sto.getShoes()) {
                                    if (shoeObj.equalsShoe(shoe)) {
                                        sto.removeShoe(shoeObj);
                                        line = this.pin + "," + this.email + "," + this.password + "," + sto.getName() + ",";
                                        for (int i = 0; i < sto.getShoes().size(); i++) {
                                            line += sto.getShoes().get(i).getName() + "," + sto.getShoes().get(i).getDescription() + ","
                                                    + sto.getShoes().get(i).getPrice() + "," + sto.getShoes().get(i).getQuantity() + ",";
                                        }
                                        break;
                                    }
                                }
                            }
                            updatedMarket.add(line);
                        }
                    } catch (IOException io) {
                        System.out.println("Error reading to the market.csv.");

                    }
                    try (PrintWriter writer = new PrintWriter(new FileWriter("market.csv"))) {
                        for (int i = 0; i < updatedMarket.size(); i++) {
                            writer.println(updatedMarket.get(i));
                            writer.flush();
                        }
                        writer.close();
                        return true;
                    } catch (IOException io) {
                        System.out.println("Error writing to the market.csv file.");
                        return false;
                    }
                } else {
                    // this store object does not own this shoe, therefore cannot remove shoe
                    return false;
                }
            } else {
                // This seller object does not own this store, therefore cannot remove a shoe
                return false;
            }
        } else {
            if (checkStore(store)) {
                if (store.checkShoe(shoe)) {
                    int index = this.stores.indexOf(store);
                    Store tempStore = this.stores.get(index);
                    tempStore.removeShoe(shoe);
                    this.stores.set(index, tempStore);
                    return true;
                } else {
                    // this store does not own this shoe object so cannot a remove a shoe from it
                    return false;
                }
            } else {
                // this seller does not own this store, which means we cannot remove a shoe from it
                return false;
            }
        }
    }

    /**
     * @return Returns the ArrayList of Store objects for this Seller object
     */
    public ArrayList<Store> getStores() {
        return this.stores;
    }


    public synchronized String viewStatistics(boolean sort, int sortBy) {

        // would you like to sort --> would you like to sort by
        // Customer1 == 50
        // Customer2 == 49
        // Customer3 == 25


        // 2nd way
        // Store 1 == 50 sales
        // product1,product2,product3
        // Store 2 == 25 sales
        if (sort) {
            ArrayList<Integer> customerSales = new ArrayList<>();
            ArrayList<Customer> customers = new ArrayList<>();
            if (sortBy == 1) {
                for (int i = 0; i < stores.size(); i++) {
                    customers.addAll(stores.get(i).getCustomers());
                }
                customers.sort(Comparator.comparingInt(Customer::getTotalAmount));
                if (customers.size() != 0) {
                    String s = "";
                    for (int i = customers.size() - 1; i >= 0; i--) {
                        s += customers.get(i).getEmail() + ": " + customers.get(i).getTotalAmount() + " Purchases Made." + "\n";
                    }
                    return s;
                } else {
                    return "You have no customers from any of your stores.";
                }
            } else {
                stores.sort(Comparator.comparingInt(Store::getSales));
                if (stores.size() != 0) {
                    String s = "";
                    for (int i = stores.size() - 1; i >= 0; i--) {
                        s += stores.get(i).getName() + ": " + stores.get(i).getSales() + " Sales Made." + "\n";
                    }
                    return s;
                } else {
                    return "You have no stores yet.";
                }
            }
        } else {
            // Data will include a list of customers with the number of
            // items that they have purchased and a list of products with the number of sales.
            // Store 1:
            // Customer 1: 50 sales --> product1, product2, product3
            String s = "";
            for (int i = 0; i < stores.size(); i++) {
                s += "Store " + (i + 1) + ": " + stores.get(i).getName() + " --> Sales: " + stores.get(i).getSales() + "\n";
                for (int j = 0; j < stores.get(i).getCustomers().size(); j++) {
                    s += "Customer " + (j + 1) + ": " + stores.get(i).getCustomers().get(j).getEmail() + "\n";
                    for (int k = 0; k < stores.get(i).getCustomers().get(j).getPurchaseHistory().size(); k++) {
                        s += "[" + stores.get(i).getCustomers().get(j).getPurchaseHistory().get(k).getName() + "] " + "\n";
                    }
                }
            }
            return s;
        }
    }

    public boolean importProducts(String filePath) {
        ArrayList<String> newArrayList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            PrintWriter writer = new PrintWriter(new FileWriter("market.csv", true));

            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                int index = -1;
                for (int i = 0; i < this.getStores().size(); i++) {
                    if (this.getStores().get(i).getName().equalsIgnoreCase(arr[0])) {
                        index = i;
                    }
                }
                if (index != -1) {
                    Store store = this.getStores().get(index);
                    Shoe shoe = new Shoe(store, arr[1], arr[2], Double.parseDouble(arr[3]), Integer.parseInt(arr[4]));
                    store.addShoe(shoe);
                    this.stores.set(index, store);
                    newArrayList.add(line);
                } else {
                    Store store = new Store(arr[0], this);
                    Shoe shoe = new Shoe(store, arr[1], arr[2], Double.parseDouble(arr[3]), Integer.parseInt(arr[4]));
                    store.addShoe(shoe);
                    writer.println(this.pin + "," + this.email + "," + this.password + "," + line);
                    writer.flush();
                    this.stores.add(store);
                }
            }
        } catch (IOException io) {
//                System.out.println("Error writing to the " + this.email + ".csv" + " file.");
            return false;
        }
        ArrayList<String> market = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("market.csv"))) {

            String line = "";
            while ((line = reader.readLine()) != null) {
                for (int i = 0; i < newArrayList.size(); i++) {
                    String[] arr = line.split(",");
                    String[] arr1 = newArrayList.get(i).split(",");
                    if (arr.length > 4) {
                        if (arr[3].equalsIgnoreCase(arr1[0])) {
                            line += arr1[1] + "," + arr1[2] + "," + arr1[3] + "," + arr1[4] + ",";
                        }
                    }
                }
                market.add(line);
            }
        } catch (IOException io) {
//            System.out.println("Error reading to the market.csv file.");
            return false;
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter("market.csv"))) {
            for (String line : market) {
                writer.println(line);
                writer.flush();
            }
            return true;
        } catch (IOException io) {
//            System.out.println("Error writing to the market.csv file");
            return false;
        }
    }

    public boolean exportProducts(String fileName) {
        File f = new File(fileName);
        try {
            boolean v = f.createNewFile();
        } catch (IOException ex) {
            System.out.println("Error writing to the file!");
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader("market.csv"));
            PrintWriter writer = new PrintWriter(new FileWriter(f));
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr[0].equals(this.pin)) {
                    if (arr.length > 4) {
                        writer.println("Store:" + arr[3]);
                        writer.flush();
                        int count = 1;
                        for (int i = 4; i < arr.length; i += 4) {
                            writer.println("Product " + count + ":" + arr[i] + "," + arr[i + 1] + "," + arr[i + 2] + "," + arr[i + 3]);
                            writer.flush();
                            count++;
                        }
                    } else if (arr.length == 4) {
                        writer.println("Store:" + arr[3]);
                        writer.println("No products yet");
                        writer.flush();
                    }
                }
            }
            return true;
        } catch (IOException e) {
//            System.out.println("Error writing to the " + fileName + " file");
            return false;
        }
    }
}
