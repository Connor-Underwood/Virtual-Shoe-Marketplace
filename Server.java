import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

// Server class
class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);

                Thread t = new Thread(clientHandler);
                t.start();
            }
        } catch (Exception e) {
            closeServer();
        }
    }

    public void closeServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (Exception e) {
            int num;
        }
    }


    // ClientHandler class
    private static class ClientHandler implements Runnable {
        public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

        public static final Object GATEKEEPER = new Object();

        private Socket clientSocket;
        private BufferedReader reader;
        private PrintWriter writer;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;

        // Constructor
        public ClientHandler(Socket socket) throws IOException {
            this.clientSocket = socket;
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
            clientHandlers.add(this);
            this.oos = new ObjectOutputStream(clientSocket.getOutputStream());
            this.ois = new ObjectInputStream(clientSocket.getInputStream());
        }

        public void run() {
            int typeOfUser = -1;
            int userPin = -1;
            MarketPlace.loadMarket(); // loads Market for all clients
            try {
                // Login/Create Account Implementation ----------
                String start = reader.readLine(); // Login or Create Account
                if (start.equals("Login")) { // Client is trying to log in
                    String email = reader.readLine(); // Get E-Mail Input
                    while (MarketPlace.checkEmail(email)) { // Run a loop if email is doesn't exist within accounts.csv
                        writer.println("Invalid E-Mail");
                        email = reader.readLine();
                    }
                    writer.println(email); // send email back to client
                    String password = reader.readLine(); // Get Password Input
                    while (!MarketPlace.checkPassword(email, password)) { // Run a loop to make sure password matches e-mail
                        writer.println("Invalid Password");
                        password = reader.readLine();
                    }
                    writer.println(password); // send password back to client

                    if (MarketPlace.checkPinFromCredentials(email, password).length() == 4) {
                        userPin = Integer.parseInt(MarketPlace.checkPinFromCredentials(email, password));
                        typeOfUser = 1;
                        writer.println("Seller");
                    } else if (MarketPlace.checkPinFromCredentials(email, password).length() == 5) {
                        userPin = Integer.parseInt(MarketPlace.checkPinFromCredentials(email, password));
                        typeOfUser = 2;
                        writer.println("Customer");
                    }
                } else if (start.equals("Create")) { // Client is trying to Create An Account
                    Random random = new Random(); // Used to create the pin value for the client
                    MarketPlace.createAccountsFile(); // if accounts.csv doesn't exist, we create it here
                    String email = reader.readLine(); // receive E-Mail from Client
                    while (!MarketPlace.checkEmail(email)) { // Verify it hasn't been taken inside a loop
                        writer.println("Taken");
                        email = reader.readLine();
                    }
                    writer.println(email); // Send valid E-Mail back to Client

                    String password = reader.readLine(); // Does not have to verify, this is a new Password
                    String userType = reader.readLine(); // Receive the typeOfUser of the created account, Seller/Customer


                    if (userType.equals("Seller")) { // We create the Pin for that Seller
                        typeOfUser = 1;
                        userPin = random.nextInt(1000, 9999);
                        while (!MarketPlace.checkPin(Integer.toString(userPin))) { // make sure pin is not taken
                            userPin = random.nextInt(1000, 9999);
                        }
                    } else {
                        typeOfUser = 2;
                        userPin = random.nextInt(10000, 99999); // We create the Pin for that Customer
                        while (!MarketPlace.checkPin(Integer.toString(userPin))) { // make sure pin is not taken
                            userPin = random.nextInt(10000, 99999);
                        }
                    }
                    synchronized (GATEKEEPER) { // THIS MUST BE SYNCHRONIZED BECAUSE WE ARE ACCESSING SHARED INFORMATION
                        // FOR ALL CLIENTS. A.K.A., ALL CLIENTS ARE READING FROM THIS FILE, MAKE SURE ONLY ONE CLIENT WRITES
                        // TO IT AT A TIME
                        try (PrintWriter writer = new PrintWriter(new FileWriter("accounts.csv", true))) {
                            writer.println(userPin + "," + email + "," + password + ",");
                            writer.flush();
                        } catch (IOException io) {
                            int num;
                        }
                    }

                    if (userType.equals("Seller")) {
                        synchronized (GATEKEEPER) { // THIS MUST BE SYNCHRONIZED BECAUSE WE ARE ACCESSING SHARED INFORMATION
                            // FOR ALL CLIENTS. A.K.A., ALL CLIENTS ARE READING FROM THIS FILE, MAKE SURE ONLY ONE CLIENT WRITES
                            // TO IT AT A TIME
                            try (PrintWriter writer = new PrintWriter(new FileWriter("market.csv", true))) {
                                writer.println(userPin + "," + email + "," + password + ",");
                                writer.flush();
                            } catch (IOException io) {
                                int num;
                            }
                            Seller seller = new Seller(Integer.toString(userPin), email, password); // make a new Seller object
                            MarketPlace.sellers.add(seller); // add it to the arrayList of Sellers because they just made an account
                        }
                    } else {
                        synchronized (GATEKEEPER) { // THIS MUST BE SYNCHRONIZED BECAUSE WE ARE ACCESSING SHARED INFORMATION
                            // FOR ALL CLIENTS. A.K.A., ALL CLIENTS OBTAIN INFO FROM THE STATIC ARRAYLIST OF CUSTOMERS
                            // IN MARKETPLACE, IF WE MODIFY IT LIKE WE ARE DOING HERE, IT MUST BE SYNCHRONIZED
                            Customer customer = new Customer(Integer.toString(userPin), email, password);
                            MarketPlace.customers.add(customer);
                        }
                    }
                    // End of Login/Create Account Implementation ----------
                }

                // START OF SELLER IMPLEMENTATION;
                if (typeOfUser == 1) {
                    int index;
                    Seller seller;
                    synchronized (GATEKEEPER) { // SYNCHRONIZED BECAUSE ACCESSING SHARED DATA
                        index = -1;
                        for (Seller s : MarketPlace.sellers) {
                            if (s.getPin().equals(Integer.toString(userPin))) {
                                index = MarketPlace.sellers.indexOf(s);
                            }
                        }
                        seller = MarketPlace.sellers.get(index); // GETS SPECIFIC SELLER
                    }
                    String performAnotherActivity = "";
                    do {
                        String sellerSelectedOption = reader.readLine(); // RECEIVES SELECTED OPTION FROM THE CLIENT END

                        if (sellerSelectedOption.equalsIgnoreCase("Add a store")) {
                            String storeName = reader.readLine(); // RECIEVES STORENAME FROM CLIENT
                            if (!storeName.startsWith("Y")) {
                                Store tempStore;
                                synchronized (GATEKEEPER) { // SYNCHRONIZED BECAUSE METHOD CALL INSIDE MODIFIES THE FILES
                                    tempStore = new Store(storeName, seller); // PLACEHOLDER TO ADD TO THE SELLERS' ARRAYLIST OF STORES
                                    if (seller.addStore(tempStore, true)) { // CHECKS IF STORE HAS BEEN ADDED SUCCESSFULLY
                                        writer.println("Store added");
                                    } else {
                                        writer.println("You already own this store!");
                                    }
                                    MarketPlace.sellers.set(index, seller); // RESETS SELLER AFTER MAKING CHANGES
                                }
                            }
                        }
                        if (sellerSelectedOption.equalsIgnoreCase("Add a New shoe")) {
                            for (int i = 0; i < seller.getStores().size(); i++) {
                                writer.println(seller.getStores().get(i).getName());
                                if (i == seller.getStores().size() -1) {
                                    writer.println("Done");
                                } else {
                                    writer.println("YOO");
                                }
                            }
                            String storeName = reader.readLine(); // RECEIVES STORENAME FROM CLIENT
                            int storeIndex = -1;
                            synchronized (GATEKEEPER) { // CHECKS IF STORE IS OWNED BY SELLER
                                for (int i = 0; i < seller.getStores().size(); i++) {  // SHOULD WE SYNCHRONIZE THIS??????
                                    if (seller.getStores().get(i).getName().equals(storeName)) {
                                        storeIndex = i;
                                    }
                                }
                                writer.println(storeIndex);
                                // writer.flush();
                            }
                            if (storeIndex != -1) {
                                Store store = seller.getStores().get(storeIndex); // GETS STORE THAT THE SHOE NEEDS TO BE ADDED TO
                                // GETS ALL SHOE DETAILS FROM CLIENT
                                String shoeName = reader.readLine();
                                String shoeDesc = reader.readLine();
                                double price = Double.parseDouble(reader.readLine());
                                int quantity = Integer.parseInt(reader.readLine());
                                synchronized (GATEKEEPER) { // SYNCHRONIZED BECAUSE METHOD CALL INSIDE MODIFIES THE FILES
                                    Shoe shoe = new Shoe(store, shoeName, shoeDesc, price, quantity); // PLACEHOLDER TO ADD TO THE STORE'S ARRAYLIST OF SHOES
                                    if (seller.addShoe(store, shoe, true)) { // CHECKS IF SHOE HAS BEEN ADDED SUCCESSFULLY
                                        MarketPlace.sellers.set(index, seller);
                                        writer.println("Shoe added");
                                    } else {
                                        writer.println("Shoe could not be added");
                                    }
                                }
                            }
                        }
                        if (sellerSelectedOption.equalsIgnoreCase("Remove a shoe")) {
                            String shoeName = reader.readLine();
                            for (int i =0; i < seller.getStores().size(); i++) {
                                writer.println(seller.getStores().get(i).getName());
                                if (i != seller.getStores().size() -1) {
                                    writer.println("YOO");
                                } else {
                                    writer.println("Done");
                                }
                            }
                            String storeName = reader.readLine();
                            int storeIndex = -1;
                            synchronized (GATEKEEPER) { // CHECKS IF STORE IS OWNED BY SELLER
                                for (int i = 0; i < seller.getStores().size(); i++) {  // SHOULD WE SYNCHRONIZE THIS??????
                                    if (seller.getStores().get(i).getName().equals(storeName)) {
                                        storeIndex = i;
                                    }
                                }
                                writer.println(storeIndex);
                            }
                            if (storeIndex != -1) {
                                Store store = seller.getStores().get(storeIndex); // GETS STORE THAT THE SHOE NEEDS TO BE ADDED TO
                                // GETS ALL SHOE DETAILS FROM CLIENT
                                String shoeDesc = reader.readLine();
                                double price = Double.parseDouble(reader.readLine());
                                int quantity = Integer.parseInt(reader.readLine());
                                synchronized (GATEKEEPER) { // SYNCHRONIZED BECAUSE METHOD CALL INSIDE MODIFIES THE FILES
                                    Shoe shoe = new Shoe(store, shoeName, shoeDesc, price, quantity); // PLACEHOLDER TO ADD TO THE STORE'S ARRAYLIST OF SHOES
                                    if (seller.removeShoe(store, shoe, true)) { // CHECKS IF SHOE HAS BEEN ADDED SUCCESSFULLY
                                        MarketPlace.sellers.set(index, seller);
                                        writer.println("Shoe removed!");
                                    } else {
                                        writer.println(storeName + " does not own " + shoeName  + "'s!");
                                    }
                                }
                            }

                        }
                        if (sellerSelectedOption.equalsIgnoreCase("Edit a Shoe")) {
                            String shoeName = reader.readLine();
                            String storeName = reader.readLine();
                            int storeIndex = -1;
                            synchronized (GATEKEEPER) { // CHECKS IF STORE IS OWNED BY SELLER
                                for (int i = 0; i < seller.getStores().size(); i++) {  // SHOULD WE SYNCHRONIZE THIS??????
                                    if (seller.getStores().get(i).getName().equals(storeName)) {
                                        storeIndex = i;
                                    }
                                }
                                writer.println(storeIndex);
                            }
                            if (storeIndex != -1) {
                                Store store = seller.getStores().get(storeIndex);
                                int shoeIndex = -1;
                                synchronized (GATEKEEPER) {
                                    for (int i = 0; i < store.getShoes().size(); i++) {
                                        if (store.getShoes().get(i).getName().equalsIgnoreCase(shoeName)) {
                                            shoeIndex = i;
                                        }
                                    }
                                    writer.println(shoeIndex);
                                }
                                if (shoeIndex != -1) {
                                    Shoe shoe = store.getShoes().get(shoeIndex);
                                    String newShoeName = shoe.getName();
                                    String newShoeDescription = shoe.getDescription();
                                    String newPrice = String.valueOf(shoe.getPrice());
                                    String newQuantity = Integer.toString(shoe.getQuantity());
                                    String changeShoeName = reader.readLine();
                                    if (changeShoeName.equals("Change Shoe Name")) {
                                        newShoeName = reader.readLine();
                                    }
                                    String changeShoeDesc = reader.readLine();
                                    if (changeShoeDesc.equals("Change Shoe Description")) {
                                        newShoeDescription = reader.readLine();
                                    }
                                    String changeShoePrice = reader.readLine();
                                    if (changeShoePrice.equals("Change Shoe Price")) {
                                        newPrice = reader.readLine();
                                    }
                                    String changeShoeQuantity = reader.readLine();
                                    if (changeShoeQuantity.equals("Change Shoe Quantity")) {
                                        newQuantity = reader.readLine();
                                    }
                                    synchronized (GATEKEEPER) {
                                        seller.removeShoe(store, shoe, true);
                                        seller.addShoe(store, new Shoe(store, newShoeName, newShoeDescription, Double.parseDouble(newPrice),
                                                Integer.parseInt(newQuantity)), true);
                                        MarketPlace.sellers.set(index, seller);
                                    }
                                    writer.println("Y");
                                }
                            }
                        }
                        if (sellerSelectedOption.equalsIgnoreCase("View your sales information")) {
                            String option = reader.readLine();
                            if (option.startsWith("c")) {
                                oos.writeObject(seller.viewStatistics(true, 1));
                                oos.flush();
                            } else if (option.startsWith("s")) {
                                oos.writeObject(seller.viewStatistics(true ,2));
                                oos.flush();
                            } else if (option.startsWith("n")) {
                                oos.writeObject(seller.viewStatistics(false, -1));
                                oos.flush();
                            }
                        }
                        if(sellerSelectedOption.equalsIgnoreCase("Change Email")){
                            String newEmail = reader.readLine();
                            synchronized (GATEKEEPER) {
                                seller.setEmail(newEmail);
                                MarketPlace.sellers.set(index, seller);
                            }
                        }

                        if(sellerSelectedOption.equalsIgnoreCase("Change Password")){
                            String newPass = reader.readLine();
                            synchronized (GATEKEEPER) {
                                seller.setPassword(newPass);
                                MarketPlace.sellers.set(index, seller);
                            }
                        }
                        if (sellerSelectedOption.equalsIgnoreCase("Import products from a file")) {
                            String filepath = reader.readLine();
                            synchronized (GATEKEEPER) {
                                writer.println(seller.importProducts(filepath));
                                MarketPlace.sellers.set(index,seller);
                            }

                        }
                        if (sellerSelectedOption.equalsIgnoreCase("Export products to a file")) {
                            String fileName = reader.readLine();
                            synchronized (GATEKEEPER) {
                                writer.println(seller.exportProducts(fileName));
                                MarketPlace.sellers.set(index, seller);
                            }
                        }
                        try {
                            performAnotherActivity = reader.readLine();
                        } catch (NullPointerException n) {
                            // if client disconnects
                            int num;
                        }
                    } while (performAnotherActivity.equals("0"));
                } else {
                    int index;
                    Customer customer;
                    synchronized (GATEKEEPER) { // SYNCHRONIZED BECAUSE ACCESSING SHARED DATA
                        index = -1;
                        for (Customer c : MarketPlace.customers) {
                            if (c.getPin().equals(Integer.toString(userPin))) {
                                index = MarketPlace.customers.indexOf(c);
                            }
                        }
                        customer = MarketPlace.customers.get(index); // GETS SPECIFIC SELLER
                    }
                    String performAnotherActivity = "";

                    do  {
                        String customerChosenOption = reader.readLine();
                        if (customerChosenOption.equalsIgnoreCase(MarketPlace.VIEW_MARKET)) {
                            ArrayList<ArrayList<String>> marketList = new ArrayList<>();
                            ArrayList<String> shoeInTheMarket;
                            for (Seller seller : MarketPlace.sellers) {
                                for (Store store : seller.getStores()) {
                                    for (Shoe shoe : store.getShoes()) {
                                        shoeInTheMarket = new ArrayList<>();
                                        shoeInTheMarket.add(seller.getEmail());
                                        shoeInTheMarket.add(store.getName());
                                        shoeInTheMarket.add(shoe.getName());
                                        shoeInTheMarket.add(String.valueOf(shoe.getPrice()));
                                        marketList.add(shoeInTheMarket);
                                    }
                                }
                            }
                            for (int i = 0; i <= marketList.size(); i++) {
                                if (i == marketList.size()) {
                                    writer.println("done writing");
                                } else {
                                    writer.println("continue");
                                    for (int j = 0; j <= 4; j++) {
                                        if (j == 4) {
                                            writer.println("done");
                                        } else {
                                            writer.println(marketList.get(i).get(j));
                                        }
                                    }
                                }
                            }
                            String purchase = reader.readLine();
                            if (purchase.equalsIgnoreCase("wants to purchase")) {
                                String sellerEmail = reader.readLine();
                                String storeName = reader.readLine();
                                String shoeName = reader.readLine();
                                int sellerIndex = -1;
                                for (int i = 0; i < MarketPlace.sellers.size(); i++) {
                                    if (MarketPlace.sellers.get(i).getEmail().equalsIgnoreCase(sellerEmail)) {
                                        sellerIndex = i;
                                        break;
                                    }
                                }
                                Seller seller = MarketPlace.sellers.get(sellerIndex);
                                int storeIndex = -1;
                                for (int i = 0; i < seller.getStores().size(); i++) {
                                    if (seller.getStores().get(i).getName().equalsIgnoreCase(storeName)) {
                                        storeIndex = i;
                                        break;
                                    }
                                }
                                Store store = MarketPlace.sellers.get(sellerIndex).getStores().get(storeIndex);
                                Shoe shoe;
                                synchronized (GATEKEEPER) {
                                    shoe = customer.findShoe(shoeName, storeName);
                                    oos.writeObject("Store: " + shoe.getStore().getName() + "\n" + "Name: " + shoe.getName() + "\n" +
                                            "Description: " + shoe.getDescription() + "\n" + "Price: " + shoe.getPrice() + "\n" +
                                            "Quantity: " + shoe.getQuantity());
                                }
                                String quantity = reader.readLine();
                                synchronized (GATEKEEPER) {
                                    customer.purchase(shoe, store, Integer.parseInt(quantity));
                                    writer.println(shoe.getName() + " successfully purchased!");
                                }
                            }
                        }
                        else if (customerChosenOption.equalsIgnoreCase(MarketPlace.SEARCH_MARKET)) {
                            String searchChoice = reader.readLine();
                            ArrayList<ArrayList<String>> marketList;
                            if (searchChoice.equalsIgnoreCase("Search by store name.")) {
                                String userChoiceStore = reader.readLine();
                                marketList = new ArrayList<>();
                                ArrayList<String> shoeInTheMarket = new ArrayList<>();
                                for(Seller seller: MarketPlace.sellers) {
                                    for (Store store : seller.getStores()) {
                                        for (Shoe shoe : store.getShoes()) {
                                            if (shoe.getStore().getName().toLowerCase().contains(userChoiceStore.toLowerCase())) {
                                                shoeInTheMarket.add(seller.getEmail());
                                                shoeInTheMarket.add(store.getName());
                                                shoeInTheMarket.add(shoe.getName());
                                                shoeInTheMarket.add(String.valueOf(shoe.getPrice()));
                                                marketList.add(shoeInTheMarket);
                                            }
                                            shoeInTheMarket = new ArrayList<>();
                                        }
                                    }
                                }
                                for (int i = 0; i <= marketList.size(); i++) {
                                    if (i == marketList.size()) {
                                        writer.println("done writing");
                                    } else {
                                        writer.println("continue");
                                        for (int j = 0; j <= 4; j++) {
                                            if (j == 4) {
                                                writer.println("done");
                                            } else {
                                                writer.println(marketList.get(i).get(j));
                                            }
                                        }
                                    }
                                }
                            } else if (searchChoice.equalsIgnoreCase("Search by Shoe Name.")) {
                                String userChoiceShoe = reader.readLine();
                                marketList = new ArrayList<>();
                                ArrayList<String> shoeInTheMarket = new ArrayList<>();
                                for(Seller seller: MarketPlace.sellers) {
                                    for (Store store : seller.getStores()) {
                                        for (Shoe shoe : store.getShoes()) {
                                            if (shoe.getName().toLowerCase().contains(userChoiceShoe.toLowerCase())) {
                                                shoeInTheMarket.add(seller.getEmail());
                                                shoeInTheMarket.add(store.getName());
                                                shoeInTheMarket.add(shoe.getName());
                                                shoeInTheMarket.add(String.valueOf(shoe.getPrice()));
                                                marketList.add(shoeInTheMarket);
                                            }
                                            shoeInTheMarket = new ArrayList<>();
                                        }
                                    }
                                }
                                for (int i = 0; i <= marketList.size(); i++) {
                                    if (i == marketList.size()) {
                                        writer.println("done writing");
                                    } else {
                                        writer.println("continue");
                                        for (int j = 0; j <= 4; j++) {
                                            if (j == 4) {
                                                writer.println("done");
                                            } else {
                                                writer.println(marketList.get(i).get(j));
                                            }
                                        }
                                    }
                                }
                            } else if (searchChoice.equalsIgnoreCase("Search by Shoe Description.")) {
                                String userChoiceDescription = reader.readLine();
                                marketList = new ArrayList<>();
                                ArrayList<String> shoeInTheMarket = new ArrayList<>();
                                for(Seller seller: MarketPlace.sellers) {
                                    for (Store store : seller.getStores()) {
                                        for (Shoe shoe : store.getShoes()) {
                                            if (shoe.getDescription().toLowerCase().contains(userChoiceDescription.toLowerCase())) {
                                                shoeInTheMarket.add(seller.getEmail());
                                                shoeInTheMarket.add(store.getName());
                                                shoeInTheMarket.add(shoe.getName());
                                                shoeInTheMarket.add(String.valueOf(shoe.getPrice()));
                                                marketList.add(shoeInTheMarket);
                                            }
                                            shoeInTheMarket = new ArrayList<>();
                                        }
                                    }
                                }
                                for (int i = 0; i <= marketList.size(); i++) {
                                    if (i == marketList.size()) {
                                        writer.println("done writing");
                                    } else {
                                        writer.println("continue");
                                        for (int j = 0; j <= 4; j++) {
                                            if (j == 4) {
                                                writer.println("done");
                                            } else {
                                                writer.println(marketList.get(i).get(j));
                                            }
                                        }
                                    }
                                }
                            } else if (searchChoice.equalsIgnoreCase("Sort by price.")) {
                                String userChoicePrice = reader.readLine();
                                double price = Double.parseDouble(userChoicePrice);
                                marketList = new ArrayList<>();
                                ArrayList<String> shoeInTheMarket = new ArrayList<>();
                                for(Seller seller: MarketPlace.sellers) {
                                    for (Store store : seller.getStores()) {
                                        for (Shoe shoe : store.getShoes()) {
                                            if (shoe.getPrice() <= price) {
                                                shoeInTheMarket.add(seller.getEmail());
                                                shoeInTheMarket.add(store.getName());
                                                shoeInTheMarket.add(shoe.getName());
                                                shoeInTheMarket.add(String.valueOf(shoe.getPrice()));
                                                marketList.add(shoeInTheMarket);
                                            }
                                            shoeInTheMarket = new ArrayList<>();
                                        }
                                    }
                                }
                                for (int i = 0; i <= marketList.size(); i++) {
                                    if (i == marketList.size()) {
                                        writer.println("done writing");
                                    } else {
                                        writer.println("continue");
                                        for (int j = 0; j <= 4; j++) {
                                            if (j == 4) {
                                                writer.println("done");
                                            } else {
                                                writer.println(marketList.get(i).get(j));
                                            }
                                        }
                                    }
                                }
                            } else if (searchChoice.equalsIgnoreCase("Sort by quantity")) {
                                String userChoiceQuantity = reader.readLine();
                                int quantity = Integer.parseInt(userChoiceQuantity);
                                marketList = new ArrayList<>();
                                ArrayList<String> shoeInTheMarket = new ArrayList<>();
                                for(Seller seller: MarketPlace.sellers) {
                                    for (Store store : seller.getStores()) {
                                        for (Shoe shoe : store.getShoes()) {
                                            if (shoe.getQuantity() >= quantity) {
                                                shoeInTheMarket.add(seller.getEmail());
                                                shoeInTheMarket.add(store.getName());
                                                shoeInTheMarket.add(shoe.getName());
                                                shoeInTheMarket.add(String.valueOf(shoe.getPrice()));
                                                marketList.add(shoeInTheMarket);
                                            }
                                            shoeInTheMarket = new ArrayList<>();
                                        }
                                    }
                                }
                                for (int i = 0; i <= marketList.size(); i++) {
                                    if (i == marketList.size()) {
                                        writer.println("done writing");
                                    } else {
                                        writer.println("continue");
                                        for (int j = 0; j <= 4; j++) {
                                            if (j == 4) {
                                                writer.println("done");
                                            } else {
                                                writer.println(marketList.get(i).get(j));
                                            }
                                        }
                                    }
                                }
                            }
                            String purchase = reader.readLine();
                            if (purchase.equalsIgnoreCase("wants to purchase")) {
                                String sellerEmail = reader.readLine();
                                String storeName = reader.readLine();
                                String shoeName = reader.readLine();
                                int sellerIndex = -1;
                                for (int i = 0; i < MarketPlace.sellers.size(); i++) {
                                    if (MarketPlace.sellers.get(i).getEmail().equalsIgnoreCase(sellerEmail)) {
                                        sellerIndex = i;
                                        break;
                                    }
                                }
                                Seller seller = MarketPlace.sellers.get(sellerIndex);
                                int storeIndex = -1;
                                for (int i = 0; i < seller.getStores().size(); i++) {
                                    if (seller.getStores().get(i).getName().equalsIgnoreCase(storeName)) {
                                        storeIndex = i;
                                        break;
                                    }
                                }
                                Store store = MarketPlace.sellers.get(sellerIndex).getStores().get(storeIndex);
                                Shoe shoe;
                                synchronized (GATEKEEPER) {
                                    shoe = customer.findShoe(shoeName, storeName);
                                    oos.writeObject("Store: " + shoe.getStore().getName() + "\n" + "Name: " + shoe.getName() + "\n" +
                                            "Description: " + shoe.getDescription() + "\n" + "Price: " + shoe.getPrice() + "\n" +
                                            "Quantity: " + shoe.getQuantity());
                                }
                                String quantity = reader.readLine();
                                synchronized (GATEKEEPER) {
                                    customer.purchase(shoe, store, Integer.parseInt(quantity));
                                    writer.println(shoe.getName() + " successfully purchased!");
                                }
                            }
                        }
                        else if (customerChosenOption.equalsIgnoreCase(MarketPlace.REVIEW_PURCHASE_HISTORY)) {
                            synchronized (GATEKEEPER) {
                                oos.writeObject(customer.viewPurchaseHistory(false, ""));
                                MarketPlace.customers.set(index, customer);
                            }
                        }
                        else if (customerChosenOption.equalsIgnoreCase(MarketPlace.EXPORT_SHOE)) {
                            String filePath = reader.readLine();
                            synchronized (GATEKEEPER) {
                                writer.println(customer.viewPurchaseHistory(true, filePath));
                                MarketPlace.customers.set(index, customer);
                            }
                        }
                        else if (customerChosenOption.equalsIgnoreCase(MarketPlace.CHANGE_CUSTOMER_EMAIL)) {
                            String newEmail = reader.readLine();
                            synchronized (GATEKEEPER) {
                                customer.setEmail(newEmail);
                                MarketPlace.customers.set(index, customer);
                            }
                        }
                        else if (customerChosenOption.equalsIgnoreCase(MarketPlace.CHANGE_CUSTOMER_PASSWORD)) {
                            String newPass = reader.readLine();
                            synchronized (GATEKEEPER) {
                                customer.setPassword(newPass);
                                MarketPlace.customers.set(index, customer);
                            }
                        }
                        else if (customerChosenOption.equalsIgnoreCase(MarketPlace.PURCHASE_SHOE)) {
                            ArrayList<ArrayList<String>> marketList = new ArrayList<>();
                            ArrayList<String> shoeInTheMarket = new ArrayList<>();
                            for (Seller seller : MarketPlace.sellers) {
                                for (Store store : seller.getStores()) {
                                    for (Shoe shoe : store.getShoes()) {
                                        shoeInTheMarket = new ArrayList<>();
                                        shoeInTheMarket.add(seller.getEmail());
                                        shoeInTheMarket.add(store.getName());
                                        shoeInTheMarket.add(shoe.getName());
                                        shoeInTheMarket.add(String.valueOf(shoe.getPrice()));
                                        marketList.add(shoeInTheMarket);
                                    }
                                }
                            }
                            for (int i = 0; i <= marketList.size(); i++) {
                                if (i == marketList.size()) {
                                    writer.println("done writing");
                                } else {
                                    writer.println("continue");
                                    for (int j = 0; j <= 4; j++) {
                                        if (j == 4) {
                                            writer.println("done");
                                        } else {
                                            writer.println(marketList.get(i).get(j));
                                        }
                                    }
                                }
                            }
                            String sellerEmail = reader.readLine();
                            String storeName = reader.readLine();
                            String shoeName = reader.readLine();
                            int sellerIndex = -1;
                            for (int i = 0; i < MarketPlace.sellers.size(); i++) {
                                if (MarketPlace.sellers.get(i).getEmail().equalsIgnoreCase(sellerEmail)) {
                                    sellerIndex = i;
                                    break;
                                }
                            }
                            Seller seller = MarketPlace.sellers.get(sellerIndex);
                            int storeIndex = -1;
                            for (int i = 0; i < seller.getStores().size(); i++) {
                                if (seller.getStores().get(i).getName().equalsIgnoreCase(storeName)) {
                                    storeIndex = i;
                                    break;
                                }
                            }
                            Store store = MarketPlace.sellers.get(sellerIndex).getStores().get(storeIndex);
                            Shoe shoe;
                            synchronized (GATEKEEPER) {
                                shoe = customer.findShoe(shoeName, storeName);
                                oos.writeObject("Store: " + shoe.getStore().getName() + "\n" + "Name: " + shoe.getName() + "\n" +
                                        "Description: " + shoe.getDescription() + "\n" + "Price: " + shoe.getPrice() + "\n" +
                                        "Quantity: " + shoe.getQuantity());
                            }
                            String quantity = reader.readLine();
                            synchronized (GATEKEEPER) {
                                customer.purchase(shoe, store, Integer.parseInt(quantity));
                                writer.println(shoe.getName() + " successfully purchased!");
                            }
                        } else if (customerChosenOption.equalsIgnoreCase(MarketPlace.VIEW_MARKET_STATISTICS)) {
                            String option = reader.readLine();
                            if (option.equals("No")) {
                                oos.writeObject(MarketPlace.viewStoreStatistics(false, -1, ""));
                                oos.flush();
                            } else if (option.equals("Sort by number of products sold in every store")) {
                                oos.writeObject(MarketPlace.viewStoreStatistics(true, 1, ""));
                                oos.flush();
                            } else {
                                oos.writeObject(MarketPlace.viewStoreStatistics(true, 2, customer.getPin()));
                                oos.flush();
                            }
                        }
                        performAnotherActivity = reader.readLine();
                    } while (performAnotherActivity.equals("0"));

                }


            } catch (Exception e) {
               int num;
            }
        }
    }


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            Server server = new Server(serverSocket);
            server.startServer();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error starting server");
        }
    }
}
