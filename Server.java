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
        } catch (IOException io) {
            closeServer();
        }
    }
    public void closeServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "Error closing server.");
        }
    }


    // ClientHandler class
    private static class ClientHandler implements Runnable {
        public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

        public static final Object GATEKEEPER = new Object();

        private Socket clientSocket;
        private BufferedReader reader;
        private PrintWriter writer;

        // Constructor
        public ClientHandler(Socket socket) throws IOException {
            this.clientSocket = socket;
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
            clientHandlers.add(this);
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
                }
                else if (start.equals("Create")) { // Client is trying to Create An Account
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
                            System.out.println("Error writing to the accounts.csv file.");
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
                                System.out.println("Error writing to the market.csv file.");
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
                    synchronized (GATEKEEPER) {
                        index = -1;
                        for (Seller s : MarketPlace.sellers) {
                            if (s.getPin().equals(Integer.toString(userPin))) {
                                index = MarketPlace.sellers.indexOf(s);
                            }
                        }
                        seller = MarketPlace.sellers.get(index);
                    }

                    String sellerSelectedOption = reader.readLine();

                    if (sellerSelectedOption.equalsIgnoreCase("Add a store")) {
                        String storeName = reader.readLine();
                        Store tempStore;
                        synchronized (GATEKEEPER) {
                            tempStore = new Store(storeName, seller);
                            if (seller.addStore(tempStore, true)) {
                                writer.println("Store added");
                            } else {
                                writer.println("Store already exists");
                            }
                            MarketPlace.sellers.set(index, seller);
                        }
                    }

                    if (sellerSelectedOption.equalsIgnoreCase("Add a shoe")) {
                        String storeName = reader.readLine();
                        int storeIndex = -1;
                        synchronized (GATEKEEPER) {
                            for (int i = 0; i < seller.getStores().size(); i++) {  // SHOULD WE SYNCHRONIZE THIS??????
                                if (seller.getStores().get(i).getName().equals(storeName)) {
                                    storeIndex = i;
                                }
                            }
                            writer.println(storeIndex);
                            writer.flush();
                        }
                        Store store = seller.getStores().get(storeIndex);
                        String shoeName = reader.readLine();
                        String shoeDesc =  reader.readLine();
                        double price = Double.parseDouble(reader.readLine());
                        int quantity = Integer.parseInt(reader.readLine());
                        synchronized (GATEKEEPER) {
                            Shoe shoe = new Shoe(store, shoeName, shoeDesc, price, quantity);
                            if (seller.addShoe(store, shoe, true)) {
                                MarketPlace.sellers.set(index, seller);
                                writer.println("Shoe added");
                            } else {
                                writer.println("Shoe could not be added");
                            }
                        }
                    }
                }














            } catch (IOException io) {
                JOptionPane.showMessageDialog(null, "Error running ClientHandler.");
            }
        }
    }


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            Server server = new Server(serverSocket);
            server.startServer();
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "Error starting server");
        }
    }
}
