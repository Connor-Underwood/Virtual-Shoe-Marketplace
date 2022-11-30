import javax.swing.*;
import java.io.*;
import java.net.*;


import static javax.swing.JOptionPane.YES_OPTION;

// Client class
public class Client {
    private PrintWriter writer;
    private BufferedReader reader;

    // find some way to differentiate clients in server

    //public static void main(String[] args) {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1234)) {

            // writing to server
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            // reading from server
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String[] welcomePage = {"Login", "Create An Account"}; // Welcomes the Client presents drop down menu
            // to login or create account
            String sellerOrCustomer = (String) JOptionPane.showInputDialog(null, "Welcome To Happy Feet!",
                    "Happy Feet", JOptionPane.INFORMATION_MESSAGE, null, welcomePage, null);


            String email; // email of client we use for later
            String password; // password of client we use for later
            String userType = ""; // userType, Customer or Seller
            if (sellerOrCustomer.equals("Login")) {
                writer.println("Login"); // Let Server know Client is trying to log in
                email = JOptionPane.showInputDialog(null, "Enter Your E-Mail", "Happy Feet", JOptionPane.QUESTION_MESSAGE);
                if (email == null) { // If click exit, email will be null, which means we exit program
                    return;
                }
                writer.println(email); // send E-Mail to Server and wait for it to be verified
                while (reader.readLine().equals("Invalid E-Mail")) { // If Invalid E-Mail, run loop to get a Valid One
                    int num = JOptionPane.showConfirmDialog(null,
                            "Invalid E-Mail",
                            "Happy Feet",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.PLAIN_MESSAGE);
                    if (num == JOptionPane.CANCEL_OPTION) { // If they click exit, exit the program
                        return;
                    }

                    // Get new E-Mail
                    email = JOptionPane.showInputDialog(null, "Enter Your E-Mail", "Happy Feet", JOptionPane.QUESTION_MESSAGE);
                    if (email == null) {
                        return;
                    }
                    writer.println(email);
                }
                // At this point, the user has entered a valid E-Mail
                // Now we start the password verification below
                password = JOptionPane.showInputDialog(null, "Enter Your Password", "Happy Feet", JOptionPane.QUESTION_MESSAGE);
                if (password == null) { // Check if exit button, then exit the program
                    return;
                }
                writer.println(password); // Send password to Server and wait for verification
                while (reader.readLine().equals("Invalid Password")) {
                    int num = JOptionPane.showConfirmDialog(null,
                            "Invalid Password",
                            "Happy Feet",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.PLAIN_MESSAGE);
                    if (num == JOptionPane.CLOSED_OPTION) { // If they exit, close program
                        return;
                    }
                    // Get new Password
                    password = JOptionPane.showInputDialog(null, "Enter Your E-Mail", "Happy Feet", JOptionPane.QUESTION_MESSAGE);
                    if (password == null) { // If they exit, close program
                        return;
                    }
                    writer.println(password);
                }
                // At this point, we have valid credentials

                // Receive if the Client is a Customer or Seller fromm the Server Database
                userType = reader.readLine();
            } else if (sellerOrCustomer.equalsIgnoreCase("Create An Account")) {
                writer.println("Create"); // Let Server know Client is trying to Create An Account
                email = JOptionPane.showInputDialog(null, "Please Enter Your E-Mail.");
                if (email == null) { // If they click exit, close program
                    return;
                }
                while (!email.contains("@")) { // A loop to make sure the E-Mail contains an @ character
                    int num = JOptionPane.showConfirmDialog(null, "Invalid E-Mail", "Happy Feet",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (num == JOptionPane.CLOSED_OPTION) {
                        return;
                    }
                    email = JOptionPane.showInputDialog(null, "Please Enter Your E-Mail");
                    if (email == null) {
                        return;
                    }
                }
                writer.println(email); // Send to Server for taken E-Mail verification
                while (reader.readLine().equals("Taken")) { // If taken, run a loop until the E-Mail is valid and unique
                    int num = JOptionPane.showConfirmDialog(null, "This E-Mail is Already Taken", "Happy Feet", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.PLAIN_MESSAGE);
                    if (num == JOptionPane.CLOSED_OPTION) { // If Client exits, close program
                        return;
                    }
                    email = JOptionPane.showInputDialog(null, "Please Enter Your E-Mail");
                    // Receive new E-Mail
                    if (email == null) { // If they exit, close program
                        return;
                    }
                    while (!email.contains("@")) { // Have to check again for the @ character, run a loop
                        num = JOptionPane.showConfirmDialog(null, "Invalid E-Mail", "Happy Feet",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
                        if (num == JOptionPane.CLOSED_OPTION) {
                            return;
                        }
                        email = JOptionPane.showInputDialog(null, "Please Enter Your E-Mail");
                        if (email == null) {
                            return;
                        }
                    }
                    writer.println(email); // At this point, the E-Mail is valid, so we send to Server to our
                    // account can be added to the Server Database
                }

                // Receive Password from Client Input
                password = JOptionPane.showInputDialog(null, "Please Enter A Password Greater Than 5 Characters");
                if (password == null) { // If they exit, close program
                    return;
                }
                while (password.length() < 5) { // Make sure password is greater than 5 characters inside a loop
                    int num = JOptionPane.showConfirmDialog(null, "Password Must Be Greater Than 5 Characters", "Happy Feet",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (num == JOptionPane.CLOSED_OPTION) { // If they exit, close program
                        return;
                    }
                    password = JOptionPane.showInputDialog(null, "Please Enter A Password Greater Than 5 Characters");
                    if (password == null) { // If they exit close program
                        return;
                    }
                }
                // Server does not have to verify a new password, so we just send straight to server
                writer.println(password);

                String[] userTypes = {"Seller", "Customer"} ;
                // Ask if Client is a Customer or Seller
                userType = (String) JOptionPane.showInputDialog(null, "Choose",
                        "Happy Feet", JOptionPane.INFORMATION_MESSAGE, null, userTypes, 0);
                if (userType == null) {
                    return;
                }
                // Send this userType information to the Server to it can be added to the Database
                writer.println(userType);
            }

            if (userType.equals("Seller")) { // Seller Implementation
                String storeName;
                String shoeName;
                JOptionPane.showMessageDialog(null, "Welcome Seller!", "Happy Feet",
                        JOptionPane.PLAIN_MESSAGE);

                int performAnotherActivity;
                String[] sellerMenuOptions = {"Add a Store", "Add a new Shoe", "Remove a Shoe", "Edit a Shoe",
                "View your sales information", "Change Email", "Change Password", "Import products from a file",
                "Export products to a file"};

                do {
                    String chosenOption = (String) JOptionPane.showInputDialog(null, "Select an Option",
                            "Happy Feet", JOptionPane.INFORMATION_MESSAGE, null, sellerMenuOptions, 0);

                    writer.println(chosenOption);

                    // ADD STORE
                    if (chosenOption.equalsIgnoreCase("Add a store")) {
                        storeName = JOptionPane.showInputDialog(null, "What is the name of" +
                                "the store you would like to add:", "Happy Feet", JOptionPane.QUESTION_MESSAGE);
                        writer.println(storeName);
                        String addStoreResult = reader.readLine();
                        if (addStoreResult.equalsIgnoreCase("Store added")) {
                            JOptionPane.showMessageDialog(null, "Store added successfully!",
                                    "Happy Feet", JOptionPane.PLAIN_MESSAGE);
                        } else if (addStoreResult.equalsIgnoreCase("Store already exists")) {
                            JOptionPane.showMessageDialog(null, "You already own the store!",
                                    null, JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    // ADD SHOE
                    if (chosenOption.equalsIgnoreCase("Add a New Shoe")) {
                        storeName = JOptionPane.showInputDialog(null, "Which store would you like to add" +
                                " the shoe to:", "Happy Feet", JOptionPane.QUESTION_MESSAGE);
                        writer.println(storeName); // WE HAVE A PROBLEM HERE
                        String storeIndex = reader.readLine();
                        if (storeIndex.equals("-1")) {
                            JOptionPane.showMessageDialog(null, "You do not own this store!",
                                    "Happy Feet", JOptionPane.ERROR_MESSAGE);
                        } else {
                            shoeName = JOptionPane.showInputDialog(null, "What is the name of the" +
                                    "shoe you wish to add:", "Happy Feet", JOptionPane.QUESTION_MESSAGE);
                            writer.println(shoeName);
                            String shoeDesc = JOptionPane.showInputDialog(null, "What is the description of the" +
                                    "shoe you wish to add:", "Happy Feet", JOptionPane.QUESTION_MESSAGE);
                            writer.println(shoeDesc);
                            double price = Double.parseDouble(JOptionPane.showInputDialog(null, "What is the price of the" +
                                    "shoe you wish to add:", "Happy Feet", JOptionPane.QUESTION_MESSAGE));
                            writer.println(price);
                            int quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "What is the quantity of the" +
                                    "shoe you wish to add:", "Happy Feet", JOptionPane.QUESTION_MESSAGE));
                            writer.println(quantity);
                            String addShoeResult = reader.readLine();
                            if (addShoeResult.equalsIgnoreCase("Shoe added")) {
                                JOptionPane.showMessageDialog(null, "Shoe added successfully!",
                                        "Happy Feet", JOptionPane.PLAIN_MESSAGE);
                            } else if (addShoeResult.equalsIgnoreCase("Shoe could not be added")){
                                JOptionPane.showMessageDialog(null, "Shoe could not be added :(",
                                        "Happy Feet", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }


                    performAnotherActivity = JOptionPane.showConfirmDialog(null,
                            "Would you like to perform another activity", "Happy Feet", JOptionPane.YES_NO_OPTION );
                } while (performAnotherActivity == YES_OPTION);


            } else { // Customer Implementation

            }









        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    // establish a connection by providing host and port
    // number




}
