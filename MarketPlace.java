import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class MarketPlace {
    /**
     * Connor Underwood, Zeyad Adham, Suhani Yadav, Neel Acharya
     * <p>
     * This is our main method where input is received from Users
     */
    private static final String WELCOME_MESSAGE = "Welcome to the Shoe Marketplace!";
    public static final String INVALID_VALUE = "Please enter a valid value.";
    private static final String INVALID_EMAIL = "Please enter a valid email (Must contain @).";
    private static final String TAKEN_EMAIL = "This e-mail has already been taken!";
    private static final String INVALID_PASSWORD = "Please enter a valid password (Must contain more than 5 characters).";
    public static final String ACCOUNT_PROMPT = "1: Sign In\n2: Create an account.";
    public static final String ENTER_YOUR_EMAIL = "Please enter your e-mail.";
    public static final String LOGIN_PASSWORD_PROMPT = "Please enter your password.";
    public static final String CREATE_PASSWORD_PROMPT = "Create a password greater than 5 characters.";
    public static final String CUSTOMER_OR_SELLER = "Choose your User Type\n1: Seller\n2: Customer";
    public static final String LOGIN_SUCCESSFUL = "Login Successful!";
    public static final String WRONG_EMAIL = "The email you entered is incorrect. Please enter another email";
    public static final String WRONG_PASSWORD = "The password you entered is incorrect. Please enter another password";
    // Seller Prompts
    public static final String ADD_STORE = "1: Add a store.";
    public static final String ADD_SHOE = "2: Add a shoe.";
    public static final String REMOVE_SHOE = "3: Remove a shoe.";
    public static final String EDIT_SHOE = "4: Edit a shoe";
    public static final String VIEW_STATISTICS = "5: View your sales information.";
    public static final String CHANGE_SELLER_EMAIL = "6: Change e-mail.";
    public static final String CHANGE_SELLER_PASSWORD = "7: Change password.";
    public static final String IMPORT_PRODUCTS = "8: Import products from a file";
    public static final String EXPORT_PRODUCTS = "9: Export products to a file";
    // End of Seller Prompts

    // Customer Prompts
    public static final String VIEW_MARKET = "1: View Entire Market";
    public static final String SEARCH_MARKET = "2: Search Market";
    public static final String REVIEW_PURCHASE_HISTORY = "3: Review Purchase History";
    public static final String EXPORT_SHOE = "4: Export Purchase History as a file.";
    public static final String CHANGE_CUSTOMER_EMAIL = "5: Change e-mail.";
    public static final String CHANGE_CUSTOMER_PASSWORD = "6: Change password";
    public static final String PURCHASE_SHOE = "7: Purchase a shoe.";
    public static final String VIEW_MARKET_STATISTICS = "8: View Market Statistics";
    // End of Customer Prompts
    public static ArrayList<Seller> sellers = new ArrayList<>();
    public static ArrayList<Customer> customers = new ArrayList<>();
    private static Random random = new Random(); // use this to create pins
    public static void loadMarket() {
        File market = new File("market.csv");
        if (market.exists()) { // if market exists, this means a user has already logged in before
            try (BufferedReader reader = new BufferedReader(new FileReader("market.csv"))) {
                String line = "";
                while ((line = reader.readLine()) != null) {
                    String[] lineArray = line.split(",");
                    String cutComma = lineArray[0].replaceAll(",", "");
                    if (lineArray.length > 3) { // if the seller has either added a store or a shoe
                        boolean no = false;
                        int index = sellers.size();
                        for (int j = 0; j < sellers.size(); j++) {
                            if (cutComma.equals(sellers.get(j).getPin())) {
                                no = true;
                                index = j;
                            }
                        }
                        if (!no) { // if the for loop above doesn't execute that means the line
                            // found a unique pin, so we create a new seller
                            Seller seller = new Seller(cutComma, lineArray[1], lineArray[2]);
                            Store store = new Store(lineArray[3], seller);
                            for (int j = 4; j < lineArray.length; j += 4) {
                                Shoe shoe = new Shoe(store, lineArray[j], lineArray[j + 1], Double.parseDouble(lineArray[j + 2]),
                                        Integer.parseInt(lineArray[j + 3]));
                                store.addShoe(shoe);
                            }
                            seller.addStore(store, false); // false because we do not want to write to file
                            sellers.add(seller);
                        } else { // if the for loop did execute, that means we just need to add the stores and products,
                            // because this seller has already been added to our sellers ArrayList
                            String storeName = lineArray[3].replaceAll(",", "");
                            Store store = new Store(storeName, sellers.get(index));
                            for (int j = 4; j < lineArray.length; j += 4) {
                                Shoe shoe = new Shoe(store, lineArray[j], lineArray[j + 1],
                                        Double.parseDouble(lineArray[j + 2]), Integer.parseInt(lineArray[j + 3]));
                                store.addShoe(shoe);
                            }
                            sellers.get(index).addStore(store, false);
                            sellers.set(index, sellers.get(index));
                        }
                    } else { // if the seller hasn't added a store or product yet. we just initialize their pin, email, and password
                        boolean no = false;
                        int index = sellers.size();
                        for (int j = 0; j < sellers.size(); j++) {
                            if (cutComma.equals(sellers.get(j).getPin())) {
                                no = true;
                                index = j;
                            }
                        }
                        if (!no) {
                            Seller seller = new Seller(cutComma, lineArray[1], lineArray[2]);
                            sellers.add(seller);
                        } else {
                            /**
                             * do we need the code below??
                             */
//                    String storeName = lineArray[3].replaceAll(",", "");
//                    Store store = new Store(storeName, sellers.get(index));
//                    for (int j = 4; j < lineArray.length; j += 4) {
//                        Shoe shoe = new Shoe(store, lineArray[j ], lineArray[j+1],
//                                Double.parseDouble(lineArray[j + 2]),  Integer.parseInt(lineArray[j+3]));
//                        store.addShoe(shoe);
//                    }
//                    sellers.get(index).addStore(store);
                        }
                    }
                }
            } catch (IOException ioException) {
                System.out.println("Error reading to the market.csv file.");
            }
        } else { // create a new file called market.csv
            try {
                boolean b = market.createNewFile();
            } catch (IOException ioException) {
                System.out.println("Could not create market.csv");
            }
        }
        // seller portion of load market

        File stores = new File("stores.csv");
        if (stores.exists()) { // appending store sales and revenue to the specific store and seller in the sellers arrayList
            try (BufferedReader reader = new BufferedReader(new FileReader("stores.csv"))) {
                /**
                 * DO THIS LATER TONIGHT
                 */
                String line = "";
                Seller seller = new Seller("", "", "");
                boolean bool = false;
                int index = -1;
                while ((line = reader.readLine()) != null) {
                    String[] arr = line.split(",");
                    for (int i = 0; i < sellers.size(); i++) {
                        if (sellers.get(i).getPin().equals(arr[3])) {
                            index = i;
                            seller = sellers.get(i);
                            bool = true;
                            break;
                        }
                    }
                    if (bool) {
                        for (int i = 0; i < seller.getStores().size(); i++) {
                            if (seller.getStores().get(i).getName().equals(arr[6])) {
                                Store store = seller.stores.get(i);
                                store.setRevenue(Double.parseDouble(arr[8]));
                                store.setSales(Integer.parseInt(arr[7]));
                                seller.stores.set(i, store);
                            }
                        }
                        sellers.set(index, seller);
                    }
                }
            } catch (IOException io) {
                System.out.println("Error reading to the stores.csv file.");
            }
            try (BufferedReader reader = new BufferedReader(new FileReader("accounts.csv"))) {
                String line = "";
                while ((line = reader.readLine()) != null) {
                    String[] arr = line.split(",");
                    if (arr[0].length() > 4) {
                        Customer customer = new Customer(arr[0], arr[1], arr[2]);
                        customers.add(customer);
                    }
                }
            } catch (IOException io) {
                System.out.println("Error reading to the accounts.csv file.");
            }
            try (BufferedReader reader = new BufferedReader(new FileReader("stores.csv"))) {
                String line = "";
                while ((line = reader.readLine()) != null) {
                    String[] arr = line.split(",");
                    int index = -1;
                    boolean bool = false;
                    Customer customer = new Customer("", "", "");
                    for (int i = 0; i < customers.size(); i++) {
                        if (customers.get(i).getPin().equals(arr[0])) {
                            index = i;
                            bool = true;
                            customer = customers.get(i);
                            break;
                        }
                    }
                    if (bool) {
                        int sellerIndex = -1;
                        Seller seller = new Seller(arr[3], arr[4], arr[5]);
                        for (int i = 0; i < sellers.size(); i++) {
                            if (sellers.get(i).getPin().equals(arr[3])) {
                                sellerIndex = i;
                            }
                        }
                        if (sellerIndex != -1) {
                            seller = sellers.get(sellerIndex);
                            int storeIndex = -1;
                            for (int i = 0; i < seller.getStores().size(); i++) {
                                if (seller.getStores().get(i).getName().equalsIgnoreCase(arr[6])) {
                                    storeIndex = i;
                                }
                            }
                            if (storeIndex != -1) {
                                Store store = sellers.get(sellerIndex).getStores().get(storeIndex);
                                for (int i = 10; i < arr.length; i += 4) {
                                    Shoe shoe = new Shoe(store, arr[i], arr[i + 1], Double.parseDouble(arr[i + 2]), Integer.parseInt(arr[i + 3]));
                                    customer.addPurchaseHistory(shoe);
                                }
                                customer.addTotalAmount(Integer.parseInt(arr[9]));
                                customers.set(index, customer);
                            }

                        }
                    }
                }
            } catch (IOException io) {
                System.out.println("Error reading to the stores.csv file.");
            }

            try (BufferedReader reader = new BufferedReader(new FileReader("stores.csv"))) {
                /**
                 * DO THIS LATER TONIGHT
                 */
                String line = "";
                Seller seller = new Seller("", "", "");
                boolean bool = false;
                int index = -1;
                while ((line = reader.readLine()) != null) {
                    String[] arr = line.split(",");
                    for (int i = 0; i < sellers.size(); i++) {
                        if (sellers.get(i).getPin().equals(arr[3])) {
                            index = i;
                            seller = sellers.get(i);
                            bool = true;
                            break;
                        }
                    }
                    if (bool) {
                        for (int i = 0; i < seller.getStores().size(); i++) {
                            if (seller.getStores().get(i).getName().equals(arr[6])) {
                                Store store = seller.stores.get(i);
                                int customerIndex = -1;
                                for (int j = 0; j < customers.size(); j++) {
                                    if (customers.get(j).getPin().equals(arr[0])) {
                                        customerIndex = j;
                                        break;
                                    }
                                }
                                Customer customer = customers.get(customerIndex);
                                store.addCustomer(customer);
                                seller.stores.set(i, store);
                            }
                        }
                        /**
                         * LINES 228 - 242 may have to be fixed... advise
                         */
                        sellers.set(index, seller);
                    }
                }
            } catch (IOException io) {
                System.out.println("Error reading to the stores.csv file.");
            }


        } else {
            try {
                boolean b = stores.createNewFile();
            } catch (IOException io) {
                System.out.println("Could not create stores.csv");
            }
        }


    }
    public static boolean checkEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.csv"))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                String usedEmail = line.split(",")[1];
                if (usedEmail.equalsIgnoreCase(email)) {
                    return false;

                }
            }
            reader.close();
            return true;
        } catch (IOException io) {
            System.out.println("Error reading to the accounts.csv file.");
            return false;
        }
    }
    public static boolean checkPin(String pin) {
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.csv"))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                String usedPin = line.split(",")[0];
                if (usedPin.equals(pin)) {
                    return false;
                }
            }
            return true;
        } catch (IOException io) {
            System.out.println("Error reading to the accounts.csv file.");
            return false;
        }
    }

    public static String checkPinFromCredentials(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.csv"))) {
            String line = "";
            while((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr[1].equals(email) && arr[2].equals(password)) {
                    return (arr[0]);
                }
            }
            return "Neither";
        } catch (IOException io) {
            System.out.println("Error reading to the accounts.csv file");
            return "Neither";
        }
    }
    public static boolean checkPassword(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.csv"))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr[1].equalsIgnoreCase(email) && arr[2].equals(password)) {
                    return true;
                }
            }
            return false;
        } catch (IOException io) {
            System.out.println("Error reading to the accounts.csv file.");
            return false;
        }
    }

    public static void createAccountsFile() {
        File f = new File("accounts.csv"); // if it is the VERY FIRST USER, accounts.csv does NOT EXIST!!
        if (!f.exists()) {
            try {
                boolean b = f.createNewFile();
            } catch (IOException io) {
                System.out.println("Error creating the accounts.csv file.");
            }
        }
    }
    public static String viewStoreStatistics(boolean sort, int sortNum, String customerPin) {
        /**
         * Customers can view a dashboard with store and seller information.
         * Data will include a list of stores by number of products sold and
         * a list of stores by the products purchased by that particular customer.
         * Customers can choose to sort the dashboard.
         */


        if (sort) {
            if (sortNum == 1) {
                ArrayList<Store> stores = new ArrayList<>();
                for (int i = 0; i < sellers.size(); i++) {
                    stores.addAll(sellers.get(i).getStores());
                }
                stores.sort(Comparator.comparingInt(Store::getSales));
                int count = 1;
                String s = "";
                for (int i = stores.size() - 1; i >= 0; i--) {
                    s += "Store " + (count) + ": " + stores.get(i).getName() + " --> " + stores.get(i).getSales() + " sales." + "\n";
                    count++;
                }
                return s;
            }
            else {
                int index = -1;
                for (int i = 0; i < customers.size(); i++) {
                    if (customers.get(i).getPin().equals(customerPin)) {
                        index = i;
                    }
                }
                ArrayList<Store> stores = new ArrayList<>();
                Customer customer = customers.get(index);
                for (int i = 0; i < customer.getPurchaseHistory().size(); i++) {
                    stores.add(customer.getPurchaseHistory().get(i).getStore());
                }
                stores.sort(Comparator.comparingInt(Store::getSales));
                String s = "";
                if (stores.size() != 0) {
                    for (int i = stores.size() - 1; i >= 0; i--) {
                        s += "Seller " + stores.get(i).getSeller().getEmail() + ":" + "\n";
                        s += "Store " + stores.get(i).getName() + ": " + stores.get(i).getSales() + "\n";
                    }
                    return s;
                } else {
                    return "You have not purchased from any stores yet.";
                }
            }
        } else {
            String s = "";
            for (int i = 0; i < sellers.size(); i++) {
                s += "Seller " + (i+1) + ": "+ sellers.get(i).getEmail() + "\n";
                for (int j = 0; j < sellers.get(i).getStores().size(); j++) {
                    s += "Store " + (j+1) + ": " + sellers.get(i).getStores().get(j).getName()
                            + " --> " +sellers.get(i).getStores().get(j).getSales() + " sales."  + "\n";
                }
            }
            return s;
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadMarket(); // load all previous Customers and Sellers
        int pin = -1; // PIN to set user to
        String email = ""; // E-mail to set user to
        String password = ""; // Password to set user to
        int userType = -1; // Set to 1 if Seller, 2 if Customer
        /**
         * Start of Login Implementation.
         */
        System.out.println(WELCOME_MESSAGE);
        System.out.println(ACCOUNT_PROMPT);
        String choice = scanner.nextLine();

        while (!"1".equals(choice) && !"2".equals(choice)) {
            System.out.println(INVALID_VALUE);
            choice = scanner.nextLine();
        }
        /**
         * Sign-In Implementation
         */
        if ("1".equals(choice)) {
            System.out.println(ENTER_YOUR_EMAIL);
            email = scanner.nextLine();
            while (checkEmail(email)) { // if check e-mail is true, then the email they entered is not in the file, so keep asking for a valid email
                System.out.println(WRONG_EMAIL);
                email = scanner.nextLine();
            }
            System.out.println(LOGIN_PASSWORD_PROMPT);
            password = scanner.nextLine();
            while (!checkPassword(email, password)) { // while the password is not in the file, keep asking them to enter a new password
                System.out.println(WRONG_PASSWORD);
                password = scanner.nextLine();
            }
            System.out.println(LOGIN_SUCCESSFUL); // when the email and password are correct print the login successful prompt.

            try (BufferedReader reader = new BufferedReader(new FileReader("accounts.csv"))) {
                String line = "";
                while ((line = reader.readLine()) != null) {
                    String[] arr = line.split(",");
                    if (arr[1].equalsIgnoreCase(email) && arr[2].equals(password)) {
                        if (Integer.parseInt(arr[0]) < 10000) {
                            userType = 1;
                            pin = Integer.parseInt(arr[0]);
                        } else {
                            userType = 2;
                            pin = Integer.parseInt(arr[0]);
                        }
                    }
                }
            } catch (IOException io) {
                System.out.println("Error reading to the accounts.csv file.");
            }


        } else {
            /*
             * Create an Account Implementation
             */
            File f = new File("accounts.csv"); // if it is the VERY FIRST USER, accounts.csv does NOT EXIST!!
            if (!f.exists()) {
                try {
                    boolean b = f.createNewFile();
                } catch (IOException io) {
                    System.out.println("Error creating the accounts.csv file.");
                }
            }

            System.out.println(ENTER_YOUR_EMAIL);
            email = scanner.nextLine();
            while (!email.contains("@")) { // Case of Invalid E-mail
                System.out.println(INVALID_EMAIL);
                email = scanner.nextLine();
            }
            while (!checkEmail(email)) { // Case of taken e-mail
                System.out.println(TAKEN_EMAIL);
                System.out.println(ENTER_YOUR_EMAIL);
                email = scanner.nextLine();
            }

            System.out.println(CREATE_PASSWORD_PROMPT);
            password = scanner.nextLine();
            while (password.length() <= 5) { // Case of Invalid Password
                System.out.println(INVALID_PASSWORD);
                password = scanner.nextLine();
            }

            System.out.println(CUSTOMER_OR_SELLER);
            userType = scanner.nextInt();
            scanner.nextLine();
            while (userType != 1 && userType != 2) { // Case of invalid input
                System.out.println(INVALID_VALUE);
                userType = scanner.nextInt();
                scanner.nextLine();
            }
            if (userType == 1) { // Means they are a Seller
                pin = random.nextInt(1000, 9999);
                while (!checkPin(Integer.toString(pin))) { // make sure pin is not taken
                    pin = random.nextInt(1000, 9999);
                }
            } else { // Means they are a Customer
                pin = random.nextInt(10000, 99999);
                while (!checkPin(Integer.toString(pin))) { // make sure pin is not taken
                    pin = random.nextInt(10000, 99999);
                }
            }
            /*
             * At this point, they have created an account with completely valid credentials, so now we can write
             * to accounts.csv
             */
            try (PrintWriter writer = new PrintWriter(new FileWriter("accounts.csv", true))) {
                writer.println(pin + "," + email + "," + password + ",");
                writer.flush();
            } catch (IOException io) {
                System.out.println("Error writing to the accounts.csv file.");
            }

            if (userType == 1) { // if they are a seller, we must write their credentials to market.csv
                // ,so we can perform loadMarket()
                try (PrintWriter writer = new PrintWriter(new FileWriter("market.csv", true))) {
                    writer.println(pin + "," + email + "," + password + ",");
                    writer.flush();
                } catch (IOException io) {
                    System.out.println("Error writing to the market.csv file.");
                }
                Seller seller = new Seller(Integer.toString(pin), email, password); // make a new Seller object
                sellers.add(seller); // add it to the arrayList of Sellers because they just made an account
            } else {
                try (PrintWriter writer = new PrintWriter(new FileWriter("stores.csv", true))) {

                } catch (IOException io) {
                    System.out.println("Error reading to the stores.csv file.");
                }
                Customer customer = new Customer(Integer.toString(pin), email, password);
                customers.add(customer);
                // implement this later. means they are a customer, so add to customers arraylist
            }
        }


        if (userType == 1) { // They are a seller
            /**
             * Below is how we get our Seller object
             */
            int index = -1;
            for (Seller seller : sellers) {
                if (seller.getPin().equals(Integer.toString(pin))) {
                    index = sellers.indexOf(seller);
                }
            }

            Seller seller = sellers.get(index);


            System.out.println("Welcome Seller!");
            boolean keepGoing = true;
            do {
                String storeName = "";
                String shoeName = "";
                System.out.println(ADD_STORE);
                System.out.println(ADD_SHOE);
                System.out.println(REMOVE_SHOE);
                System.out.println(EDIT_SHOE);
                System.out.println(VIEW_STATISTICS);
                System.out.println(CHANGE_SELLER_EMAIL);
                System.out.println(CHANGE_SELLER_PASSWORD);
                System.out.println(IMPORT_PRODUCTS);
                System.out.println(EXPORT_PRODUCTS);
                choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        System.out.println("What is the name of the store you would like to add?");
                        storeName = scanner.nextLine();
                        Store tempStore = new Store(storeName, seller);
                        seller.addStore(tempStore, true);
                        sellers.set(index, seller);
                        break;
                    case "2":
                        System.out.println("What is the name of the shoe you would like to add?");
                        shoeName = scanner.nextLine();
                        System.out.println("What is the name of the store you would like to add the shoe to?");
                        storeName = scanner.nextLine();
                        int storeIndex = -1;
                        for (int i = 0; i < seller.getStores().size(); i++) {
                            if (seller.getStores().get(i).getName().equals(storeName)) {
                                storeIndex = i;
                            }
                        }
                        if (storeIndex == -1) { // means they didn't find a store in this Seller's arrayList of Stores
                            System.out.println("You don't own this store!");
                        } else {
                            Store store = seller.getStores().get(storeIndex); // find the specific store
                            System.out.println("What is the description of the shoe?");
                            String desc = scanner.nextLine();
                            System.out.println("What is the price of the shoe?");
                            double price = scanner.nextDouble();
                            scanner.nextLine();
                            System.out.println("What is the quantity of the shoe?");
                            int quantity = scanner.nextInt();
                            scanner.nextLine();
                            Shoe shoe = new Shoe(store, shoeName, desc, price, quantity);
                            if (seller.addShoe(store, shoe, true)) {
                                sellers.set(index, seller);
                            } else {
                                System.out.println("Could not add " + shoe.getName() + " to " + store.getName() + ".");
                            }
                        }
                        break;
                    case "3":
                        System.out.println("What is the name of the shoe you would like to remove?");
                        shoeName = scanner.nextLine();
                        System.out.println("What is the name of the store you would like to remove the shoe from?");
                        storeName = scanner.nextLine();
                        storeIndex = -1;
                        for (int i = 0; i < seller.getStores().size(); i++) {
                            if (seller.getStores().get(i).getName().equals(storeName)) {
                                storeIndex = i;
                            }
                        }
                        if (storeIndex == -1) {
                            System.out.println("You don't own this store!");
                        } else {
                            Store store = seller.getStores().get(storeIndex);
                            System.out.println("What is the description of the shoe you would like to remove?");
                            String desc = scanner.nextLine();
                            System.out.println("What is the price of the shoe you would like to remove?");
                            double price = scanner.nextDouble();
                            scanner.nextLine();
                            System.out.println("What is the quantity of the shoe you would like to remove");
                            int quantity = scanner.nextInt();
                            scanner.nextLine();
                            Shoe shoe = new Shoe(store, shoeName, desc, price, quantity);
                            if (seller.removeShoe(store, shoe, true)) {
                                sellers.set(index, seller);
                            } else {
                                System.out.println("Could not remove " + shoe.getName() + " from " + store.getName() + ".");
                            }
                        }
                        break;
                    case "4":
                        System.out.println("What shoe do you want to edit");
                        shoeName = scanner.nextLine();
                        System.out.println("What store does the shoe belong in?");
                        storeName = scanner.nextLine();
                        storeIndex = -1;
                        for (int i = 0; i < seller.getStores().size(); i++) {
                            if (seller.getStores().get(i).getName().equalsIgnoreCase(storeName)) {
                                storeIndex = i;
                            }
                        }
                        if (storeIndex == -1) {
                            System.out.println("You don't own this store!");
                        } else {
                            Store store = seller.getStores().get(storeIndex);
                            int shoeIndex = -1;
                            for (int i = 0; i < store.getShoes().size(); i++) {
                                if (store.getShoes().get(i).getName().equalsIgnoreCase(shoeName)) {
                                    shoeIndex = i;
                                }
                            }
                            if (shoeIndex == -1) {
                                System.out.println(store.getName() + " does not own this shoe!");
                            } else {
                                Shoe shoe = store.getShoes().get(shoeIndex);
                                seller.removeShoe(store, shoe, true); // lets change this to true if we run into an error

                                String newShoeName = shoe.getName();
                                String newShoeDescription = shoe.getDescription();
                                double newPrice = shoe.getPrice();
                                int newQuantity = shoe.getQuantity();

                                System.out.println("Do you want to change the name of the Shoe? (y/n)");
                                String response = scanner.nextLine();
                                if (response.equalsIgnoreCase("y")) {
                                    System.out.println("What do you want the name of the shoe to be?");
                                    newShoeName = scanner.nextLine();
                                }
                                System.out.println("Do you want to change the description?(y/n)");
                                response = scanner.nextLine();
                                if (response.equalsIgnoreCase("y")) {
                                    System.out.println("What do want the description of the shoe to be?");
                                    newShoeDescription = scanner.nextLine();
                                }
                                System.out.println("Do want to change the price?(y/n)");
                                response = scanner.nextLine();
                                if (response.equalsIgnoreCase("y")) {
                                    System.out.println("What do want the price of the shoe to be?");
                                    newPrice = scanner.nextDouble();
                                    scanner.nextLine();
                                }
                                System.out.println("Do you want to change the quantity?(y/n)");
                                response = scanner.nextLine();
                                if (response.equalsIgnoreCase("y")) {
                                    System.out.println("What do you want the quantity of the shoe to be?");
                                    newQuantity = scanner.nextInt();
                                    scanner.nextLine();
                                }
                                seller.addShoe(store, new Shoe(store, newShoeName, newShoeDescription, newPrice, newQuantity), true);
                                sellers.set(index, seller);
                            }
                        }
                        break;
                    case "5":
                        System.out.println("Would you like to sort the statistics dash board?(y/n)");
                        String response = scanner.nextLine();
                        if ("y".equalsIgnoreCase(response) || "yes".equalsIgnoreCase(response)) {
                            System.out.println("1: Sort by Customer Sales\n2: Sort by Stores Sales");
                            response = scanner.nextLine();
                            if ("1".equals(response)) {
                                seller.viewStatistics(true, 1);
                            } else {
                                seller.viewStatistics(true, 2);
                            }
                        } else {
                            seller.viewStatistics(false, -1);
                        }
                        break;
                    case "6":
                        System.out.println("What do you want your new e-mail to be?");
                        email = scanner.nextLine();
                        while (!checkEmail(email)) {
                            System.out.println(TAKEN_EMAIL);
                            System.out.println(ENTER_YOUR_EMAIL);
                            email = scanner.nextLine();
                        }
                        seller.setEmail(email);
                        sellers.set(index, seller);
                        break;
                    case "7":
                        System.out.println("What do you want your new password to be?");
                        password = scanner.nextLine();
                        while (password.length() <= 5) {
                            System.out.println(INVALID_PASSWORD);
                            password = scanner.nextLine();
                        }
                        seller.setPassword(password);
                        sellers.set(index, seller);
                        break;
                    case "8":
                        System.out.println("Enter the file path.");
                        response = scanner.nextLine();
                        seller.importProducts(response);
                        break;
                    case "9":
                        System.out.println("Enter the name of the file you would like to see your products in:");
                        response = scanner.nextLine();
                        seller.exportProducts(response);
                        System.out.println("Your products have been printed in " + response);

                        break;
                    default:
                        System.out.println(INVALID_VALUE);
                } // end of Switch for choice variable!!!!

                System.out.println("Would you like to perform any other activity? (y/n)");
                String response = scanner.nextLine();
                if ("no".equalsIgnoreCase(response) || "n".equalsIgnoreCase(response)) {
                    keepGoing = false;
                }
            } while (keepGoing);
        } else { // They are a Customer
            int index = -1;
            for (Customer customer : customers) {
                if (customer.getPin().equals(Integer.toString(pin))) {
                    index = customers.indexOf(customer);
                }
            }
            Customer customer = customers.get(index);
            System.out.println("Welcome Customer!");
            boolean keepGoing = true;
            do {
                System.out.println(VIEW_MARKET);
                System.out.println(SEARCH_MARKET);
                System.out.println(REVIEW_PURCHASE_HISTORY);
                System.out.println(EXPORT_SHOE);
                System.out.println(CHANGE_CUSTOMER_EMAIL);
                System.out.println(CHANGE_CUSTOMER_PASSWORD);
                System.out.println(PURCHASE_SHOE);
                System.out.println(VIEW_MARKET_STATISTICS);
                String response = scanner.nextLine();
                switch (response) {
                    case "1":
                        customer.viewMarket(false, "", -1);
                        System.out.println("Would you like to purchase a shoe?(y/n)");
                        response = scanner.nextLine();
                        if ("y".equalsIgnoreCase(response) || "yes".equalsIgnoreCase(response)) {
                            System.out.println("Enter the name of the shoe.");
                            String shoeName = scanner.nextLine();
                            System.out.println("Enter the store of the shoe.");
                            String storeName = scanner.nextLine();
                            System.out.println("Enter the seller email of the shoe.");
                            String sellerEmail = scanner.nextLine();
                            int someIndex = -1;
                            for (int i = 0; i < sellers.size(); i++) {
                                if (sellers.get(i).getEmail().equalsIgnoreCase(sellerEmail)) {
                                    someIndex = i;
                                    break;
                                }
                            }
                            Seller seller = sellers.get(someIndex); // find SPECIFIC SELLER IN OUR ARRAYLIST
                            int storeIndex = -1;
                            for (int i = 0; i < seller.getStores().size(); i++) {
                                if (seller.getStores().get(i).getName().equalsIgnoreCase(storeName)) {
                                    storeIndex = i;
                                }
                            }
                            Store store = sellers.get(someIndex).getStores().get(storeIndex); // FIND SPECIFIC STORE
                            Shoe shoe = customer.findShoe(shoeName, storeName);
                            if (shoe != null) {
                                System.out.println("Store: " + shoe.getStore().getName());
                                System.out.println("Name: " + shoe.getName());
                                System.out.println("Description: " + shoe.getDescription());
                                System.out.println("Price: " + shoe.getPrice());
                                System.out.println("Quantity: " + shoe.getQuantity());
                                System.out.println("1: Checkout\n2: Exit");
                                response = scanner.nextLine();
                                if ("1".equals(response)) {
                                    System.out.println("How many pairs would you like to purchase?");
                                    response = scanner.nextLine();
                                    if (Integer.parseInt(response) > shoe.getQuantity()) {
                                        System.out.println("Sorry, we do not have that many pairs on stock.");
                                    } else {
                                        customer.purchase(shoe, store, Integer.parseInt(response));
                                        customers.set(index, customer);
                                    }

                                }
                            } else {
                                System.out.println("Could not find a shoe based on that information.");
                            }
                        }
                        break;
                    case "2":
                        System.out.println("1: Search by Store Name.");
                        System.out.println("2: Search by Shoe Name.");
                        System.out.println("3: Search by Shoe Description.");
                        System.out.println("4: Sort by Price.");
                        System.out.println("5: Sort by Quantity");
                        response = scanner.nextLine();
                        switch (response) {
                            case "1":
                                System.out.println("What is the name of the Store?");
                                response = scanner.nextLine();
                                customer.viewMarket(true, response, 1);
                                break;
                            case "2":
                                System.out.println("What is the name of the Shoe?");
                                response = scanner.nextLine();
                                customer.viewMarket(true, response, 2);
                                break;
                            case "3":
                                System.out.println("What is the description of the Shoe?");
                                response = scanner.nextLine();
                                customer.viewMarket(true, response, 3);
                                break;
                            case "4":
                                boolean validResponse = false;
                                System.out.println("What is the price of the Shoe?");
                                response = scanner.nextLine();
                                do {
                                    try {
                                        Double.parseDouble(response);
                                        validResponse = true;
                                    } catch (NumberFormatException n) {
                                        System.out.println(INVALID_VALUE);
                                        response = scanner.nextLine();
                                    }
                                } while (!validResponse);
                                customer.viewMarket(true, response, 4);
                                break;
                            case "5":
                                validResponse = false;
                                System.out.println("What is the quantity of the Shoe?");
                                response = scanner.nextLine();
                                do {
                                    try {
                                        Integer.parseInt(response);
                                        validResponse = true;
                                    } catch (NumberFormatException n) {
                                        System.out.println(INVALID_VALUE);
                                        response = scanner.nextLine();
                                    }
                                } while (!validResponse);
                                customer.viewMarket(true, response, 5);
                                break;
                            default:
                                System.out.println(INVALID_VALUE);
                        } // end of switch response
                        System.out.println("Would you like to purchase a shoe?(y/n)");
                        response = scanner.nextLine();
                        if ("y".equalsIgnoreCase(response) || "yes".equalsIgnoreCase(response)) {
                            System.out.println("Enter the name of the shoe.");
                            String shoeName = scanner.nextLine();
                            System.out.println("Enter the store of the shoe.");
                            String storeName = scanner.nextLine();
                            System.out.println("Enter the seller email of the shoe.");
                            String sellerEmail = scanner.nextLine();
                            int someIndex = -1;
                            for (int i = 0; i < sellers.size(); i++) {
                                if (sellers.get(i).getEmail().equalsIgnoreCase(sellerEmail)) {
                                    someIndex = i;
                                    break;
                                }
                            }
                            if (someIndex != -1) {
                                Seller seller = sellers.get(someIndex); // find SPECIFIC SELLER IN OUR ARRAYLIST
                                int storeIndex = -1;
                                for (int i = 0; i < seller.getStores().size(); i++) {
                                    if (seller.getStores().get(i).getName().equalsIgnoreCase(storeName)) {
                                        storeIndex = i;
                                    }
                                }
                                if (storeIndex != -1) {
                                    Store store = sellers.get(someIndex).getStores().get(storeIndex); // FIND SPECIFIC STORE
                                    Shoe shoe = customer.findShoe(shoeName, storeName);
                                    if (shoe != null) {
                                        System.out.println("Store: " + shoe.getStore().getName());
                                        System.out.println("Name: " + shoe.getName());
                                        System.out.println("Description: " + shoe.getDescription());
                                        System.out.println("Price: " + shoe.getPrice());
                                        System.out.println("Quantity: " + shoe.getQuantity());
                                        System.out.println("1: Checkout\n2: Exit");
                                        response = scanner.nextLine();
                                        if ("1".equals(response)) {
                                            System.out.println("How many pairs would you like to purchase?");
                                            response = scanner.nextLine();
                                            if (Integer.parseInt(response) > shoe.getQuantity()) {
                                                System.out.println("Sorry, we do not have that many pairs on stock.");
                                            } else {
                                                customer.purchase(shoe, store, Integer.parseInt(response));
                                                customers.set(index, customer);
                                            }

                                        }
                                    } else {
                                        System.out.println("Could not find a shoe based on that information.");
                                    }
                                } else {
                                    System.out.println("Could not find a shoe based on that information.");
                                }
                            } else {
                                System.out.println("Could not find a shoe based on that information.");
                            }
                        }
                        break;
                    case "3":
                        customer.viewPurchaseHistory(false);
                        break;
                    case "4":
                        customer.viewPurchaseHistory(true);
                        break;
                    case "5":
                        System.out.println("What do you want your new e-mail to be?");
                        email = scanner.nextLine();
                        while (!checkEmail(email)) {
                            System.out.println(TAKEN_EMAIL);
                            System.out.println(ENTER_YOUR_EMAIL);
                            email = scanner.nextLine();
                        }
                        customer.setEmail(email);
                        customers.set(index, customer);
                        break;
                    case "6":
                        System.out.println("What do you want your new password to be?");
                        password = scanner.nextLine();
                        while (password.length() <= 5) {
                            System.out.println(INVALID_PASSWORD);
                            password = scanner.nextLine();
                        }
                        customer.setPassword(password);
                        customers.set(index, customer);
                        break;
                    case "7":
                        System.out.println("Enter the name of the shoe.");
                        String shoeName = scanner.nextLine();
                        System.out.println("Enter the store of the shoe.");
                        String storeName = scanner.nextLine();
                        System.out.println("Enter the seller email of the shoe.");
                        String sellerEmail = scanner.nextLine();
                        int someIndex = -1;
                        for (int i = 0; i < sellers.size(); i++) {
                            if (sellers.get(i).getEmail().equalsIgnoreCase(sellerEmail)) {
                                someIndex = i;
                                break;
                            }
                        }
                        if (someIndex == -1) {
                            System.out.println("We could not find a shoe based on that information.");
                        } else {
                            Seller seller = sellers.get(someIndex); // find SPECIFIC SELLER IN OUR ARRAYLIST
                            int storeIndex = -1;
                            for (int i = 0; i < seller.getStores().size(); i++) {
                                if (seller.getStores().get(i).getName().equalsIgnoreCase(storeName)) {
                                    storeIndex = i;
                                }
                            }
                            if (storeIndex == -1) {
                                System.out.println("We could not find a shoe based on that information.");
                            } else {
                                Store store = sellers.get(someIndex).getStores().get(storeIndex); // FIND SPECIFIC STORE
                                Shoe shoe = customer.findShoe(shoeName, storeName);
                                if (shoe != null) {
                                    System.out.println("Store: " + shoe.getStore().getName());
                                    System.out.println("Name: " + shoe.getName());
                                    System.out.println("Description: " + shoe.getDescription());
                                    System.out.println("Price: " + shoe.getPrice());
                                    System.out.println("Quantity: " + shoe.getQuantity());
                                    System.out.println("1: Checkout\n2: Exit");
                                    response = scanner.nextLine();
                                    if ("1".equals(response)) {
                                        System.out.println("How many pairs would you like to purchase?");
                                        response = scanner.nextLine();
                                        if (Integer.parseInt(response) > shoe.getQuantity()) {
                                            System.out.println("Sorry, we do not have that many pairs on stock.");
                                        } else {
                                            customer.purchase(shoe, store, Integer.parseInt(response));
                                            customers.set(index, customer);
                                        }

                                    }
                                } else {
                                    System.out.println("Could not find a shoe based on that information.");
                                }
                            }
                        }
                        break;
                    case "8":
                        System.out.println("Would you like to sort the dashboard?(y/n)");
                        response = scanner.nextLine();
                        if ("y".equalsIgnoreCase(response) || "yes".equalsIgnoreCase(response)) {
                            System.out.println("1: Sort by number of products sold in every store\n2: Sort" +
                                    " by number of products sold in stores you have purchased from.");
                            response = scanner.nextLine();
                            if ("1".equals(response)) {
                                viewStoreStatistics(true, 1, "");
                            } else {
                                viewStoreStatistics(true, 2, Integer.toString(pin));
                            }
                        } else {
                            viewStoreStatistics(false, -1, "");
                        }
                        break;
                    default:
                        System.out.println(INVALID_VALUE);
                }
                System.out.println("Would you like to perform any other activity? (y/n)");
                response = scanner.nextLine();
                if ("no".equalsIgnoreCase(response) || "n".equalsIgnoreCase(response)) {
                    keepGoing = false;
                }
            } while (keepGoing);
        }
    }
}
