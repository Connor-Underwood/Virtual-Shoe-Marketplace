import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;

import static javax.swing.JOptionPane.*;

// Client class
public class Client {
    private PrintWriter writer;
    private BufferedReader reader;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    // find some way to differentiate clients in server

    //public static void main(String[] args) {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1234)) {

            // writing to server
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            // reading from server
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            String[] welcomePage = {"Login", "Create An Account"}; // Welcomes the Client presents drop down menu
            // to login or create account
            String sellerOrCustomer = (String) showInputDialog(null, "Welcome To Happy Feet!",
                    "Happy Feet", INFORMATION_MESSAGE, null, welcomePage, null);


            String email; // email of client we use for later
            String password; // password of client we use for later
            String userType = ""; // userType, Customer or Seller
            try {
                if (sellerOrCustomer.equals("Login")) {
                    writer.println("Login"); // Let Server know Client is trying to log in
                    email = showInputDialog(null, "Enter Your E-Mail", "Happy Feet", QUESTION_MESSAGE);
                    if (email == null) { // If click exit, email will be null, which means we exit program
                        return;
                    }
                    writer.println(email); // send E-Mail to Server and wait for it to be verified
                    while (reader.readLine().equals("Invalid E-Mail")) { // If Invalid E-Mail, run loop to get a Valid One
                        int num = showConfirmDialog(null,
                                "Invalid E-Mail",
                                "Happy Feet",
                                DEFAULT_OPTION,
                                PLAIN_MESSAGE);
                        if (num == -1) { // If they click exit, exit the program
                            return;
                        }
                        // Get new E-Mail
                        email = showInputDialog(null, "Enter Your E-Mail", "Happy Feet", QUESTION_MESSAGE);
                        if (email == null) {
                            return;
                        }
                        writer.println(email);
                    }
                    // At this point, the user has entered a valid E-Mail
                    // Now we start the password verification below
                    password = showInputDialog(null, "Enter Your Password", "Happy Feet", QUESTION_MESSAGE);
                    if (password == null) { // Check if exit button, then exit the program
                        return;
                    }
                    writer.println(password); // Send password to Server and wait for verification
                    while (reader.readLine().equals("Invalid Password")) {
                        int num = showConfirmDialog(null,
                                "Invalid Password",
                                "Happy Feet",
                                DEFAULT_OPTION,
                                PLAIN_MESSAGE);
                        if (num == -1) { // If they exit, close program
                            return;
                        }
                        // Get new Password
                        password = showInputDialog(null, "Enter Your Password", "Happy Feet", QUESTION_MESSAGE);
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
                    email = showInputDialog(null, "Please Enter Your E-Mail.");
                    if (email == null) { // If they click exit, close program
                        return;
                    }
                    while (!email.contains("@")) { // A loop to make sure the E-Mail contains an @ character
                        int num = showConfirmDialog(null, "Invalid E-Mail", "Happy Feet",
                                DEFAULT_OPTION, PLAIN_MESSAGE);
                        if (num == -1) {
                            return;
                        }
                        email = showInputDialog(null, "Please Enter Your E-Mail");
                        if (email == null) {
                            return;
                        }
                    }
                    writer.println(email); // Send to Server for taken E-Mail verification
                    while (reader.readLine().equals("Taken")) { // If taken, run a loop until the E-Mail is valid and unique
                        int num = showConfirmDialog(null, "This E-Mail is Already Taken", "Happy Feet", DEFAULT_OPTION,
                                PLAIN_MESSAGE);
                        if (num == CLOSED_OPTION) { // If Client exits, close program
                            return;
                        }
                        email = showInputDialog(null, "Please Enter Your E-Mail");
                        // Receive new E-Mail
                        if (email == null) { // If they exit, close program
                            return;
                        }
                        while (!email.contains("@")) { // Have to check again for the @ character, run a loop
                            num = showConfirmDialog(null, "Invalid E-Mail", "Happy Feet",
                                    DEFAULT_OPTION, PLAIN_MESSAGE);
                            if (num == CLOSED_OPTION) {
                                return;
                            }
                            email = showInputDialog(null, "Please Enter Your E-Mail");
                            if (email == null) {
                                return;
                            }
                        }
                        writer.println(email); // At this point, the E-Mail is valid, so we send to Server to our
                        // account can be added to the Server Database
                    }

                    // Receive Password from Client Input
                    password = showInputDialog(null, "Please Enter A Password Greater Than 5 Characters");
                    if (password == null) { // If they exit, close program
                        return;
                    }
                    while (password.length() < 5) { // Make sure password is greater than 5 characters inside a loop
                        int num = showConfirmDialog(null, "Password Must Be Greater Than 5 Characters", "Happy Feet",
                                DEFAULT_OPTION, PLAIN_MESSAGE);
                        if (num == CLOSED_OPTION) { // If they exit, close program
                            return;
                        }
                        password = showInputDialog(null, "Please Enter A Password Greater Than 5 Characters");
                        if (password == null) { // If they exit close program
                            return;
                        }
                    }
                    // Server does not have to verify a new password, so we just send straight to server
                    writer.println(password);

                    String[] userTypes = {"Seller", "Customer"};
                    // Ask if Client is a Customer or Seller
                    userType = (String) showInputDialog(null, "Choose",
                            "Happy Feet", INFORMATION_MESSAGE, null, userTypes, 0);
                    if (userType == null) {
                        return;
                    }
                    // Send this userType information to the Server to it can be added to the Database
                    writer.println(userType);
                }
            } catch (NullPointerException n) {
                return;
            }

            if (userType.equals("Seller")) { // Seller Implementation
                String storeName;
                String shoeName;
                int num = showConfirmDialog(null, "Welcome Seller!", "Happy Feet",
                        DEFAULT_OPTION, PLAIN_MESSAGE);
                if (num == CLOSED_OPTION) { // If they exit, close program
                    return;
                }

                int performAnotherActivity;
                String[] sellerMenuOptions = {"Add a Store", "Add a New Shoe", "Remove a Shoe", "Edit a Shoe",
                        "View your sales information", "Change Email", "Change Password", "Import products from a file",
                        "Export products to a file"};

                // PRESENTS SELLER MENU
                do {
                    String chosenOption = (String) showInputDialog(null, "Select an Option",
                            "Happy Feet", INFORMATION_MESSAGE, null, sellerMenuOptions, 0);
                    if (chosenOption == null) {
                        return;
                    }

                    // SENDS THE CHOSEN OPTION TO THE SERVER
                    writer.println(chosenOption);

                    // ADD STORE
                    try {
                        if (chosenOption.equalsIgnoreCase("Add a store")) {
                            storeName = showInputDialog(null, "What is the name of " +
                                    "the store you would like to add:", "Happy Feet", QUESTION_MESSAGE);
                            if (storeName == null) {
                                return;
                            }
                            while (storeName.equals("")) {
                                num = showConfirmDialog(null, "Invalid Value", "Happy Feet", DEFAULT_OPTION, PLAIN_MESSAGE);
                                if (num == CLOSED_OPTION) {
                                    return;
                                }
                                storeName = showInputDialog(null, "What is the name of " +
                                        "the store you would like to add:", "Happy Feet", QUESTION_MESSAGE);
                                if (storeName == null) {
                                    return;
                                }
                            }
                            writer.println(storeName); // SENDS STORENAME TO THE SERVER
                            String addStoreResult = reader.readLine(); // USED TO CHECK IF STORE IS ADDED
                            if (addStoreResult.equalsIgnoreCase("Store added")) {
                                num = showConfirmDialog(null, "Store added successfully", "Happy Feet",
                                        DEFAULT_OPTION, PLAIN_MESSAGE);
                                if (num == CLOSED_OPTION) { // If they exit, close program
                                    return;
                                }
                            } else if (addStoreResult.equalsIgnoreCase("You already own this store!")) {
                                num = showConfirmDialog(null, "You already own this store!", "Happy Feet",
                                        DEFAULT_OPTION, PLAIN_MESSAGE);
                                if (num == CLOSED_OPTION) { // If they exit, close program
                                    return;
                                }
                            }

                        }

                        // ADD SHOE
                        else if (chosenOption.equalsIgnoreCase("Add a New Shoe")) {
                            ArrayList<String> storeNames = new ArrayList<>();
                            String stringPrice = "";
                            while ((stringPrice = reader.readLine()) != null) {
                                storeNames.add(stringPrice);
                                if (reader.readLine().equalsIgnoreCase("Done")) {
                                    break;
                                }
                            }
                            String[] size = new String[storeNames.size()];
                            String[] stores = storeNames.toArray(size);
                            storeName = (String) showInputDialog(null, "Which store would you like to add" +
                                    " the shoe to:", "Happy Feet", QUESTION_MESSAGE, null, stores, -1);
                            if (storeName == null) {
                                return;
                            }
                            writer.println(storeName); // WE HAVE A PROBLEM HERE (SENDS STORENAME TO THE SERVER)
                            String storeIndex = reader.readLine(); //GETS THE STORE INDEX, FROM THE SERVER TO CHECK IF STORE EXISTS
                            if (storeIndex.equals("-1")) {
                                showMessageDialog(null, "You do not own this store!",
                                        "Happy Feet", ERROR_MESSAGE);
                            } else {
                                shoeName = showInputDialog(null, "What is the name of the " +
                                        "shoe you wish to add:", "Happy Feet", QUESTION_MESSAGE);
                                if (shoeName == null) {
                                    return;
                                }
                                while (shoeName.equals("")) {
                                    num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                            DEFAULT_OPTION, PLAIN_MESSAGE);
                                    if (num == -1) {
                                        return;
                                    }
                                    shoeName = showInputDialog(null, "What is the name of the " +
                                            "shoe you wish to add:", "Happy Feet", QUESTION_MESSAGE);
                                    if (shoeName == null) {
                                        return;
                                    }
                                }
                                writer.println(shoeName);
                                String shoeDesc = showInputDialog(null, "What is the description of the " +
                                        "shoe you wish to add:", "Happy Feet", QUESTION_MESSAGE);
                                if (shoeDesc == null) {
                                    return;
                                }
                                while (shoeDesc.equals("")) {
                                    num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                            DEFAULT_OPTION, PLAIN_MESSAGE);
                                    if (num == -1) {
                                        return;
                                    }
                                    shoeDesc = showInputDialog(null, "What is the description of the " +
                                            "shoe you wish to add:", "Happy Feet", QUESTION_MESSAGE);
                                    if (shoeDesc == null) {
                                        return;
                                    }
                                }
                                writer.println(shoeDesc);
                                stringPrice = showInputDialog(null, "What is the price of the " +
                                        "shoe you wish to add:", "Happy Feet", QUESTION_MESSAGE);
                                if (stringPrice.contains(".")) {
                                    int index = stringPrice.indexOf(".");
                                    while (stringPrice.substring(index + 1).length() > 2) {
                                        showMessageDialog(null, "Invalid");
                                        stringPrice = showInputDialog(null,
                                                "What is the price of the " +
                                                        "shoe you wish to add:", "Happy Feet",
                                                QUESTION_MESSAGE);
                                        index = stringPrice.indexOf(".");
                                    }
                                }
                                if (stringPrice == null) {
                                    return;
                                }
                                double price = -1;
                                boolean validPrice = false;
                                do {
                                    try {
                                        price = Double.parseDouble(stringPrice);
                                        if (price >= .01 && !stringPrice.equals("")) {
                                            validPrice = true;
                                        } else {
                                            num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                                    DEFAULT_OPTION, PLAIN_MESSAGE);
                                            if (num == -1) {
                                                return;
                                            }
                                            stringPrice = showInputDialog(null, "What is the price of the " +
                                                    "shoe you wish to add:", "Happy Feet", QUESTION_MESSAGE);
                                            if (stringPrice == null) {
                                                return;
                                            }
                                        }
                                    } catch (NumberFormatException n) {
                                        num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                                DEFAULT_OPTION, PLAIN_MESSAGE);
                                        if (num == -1) {
                                            return;
                                        }
                                        stringPrice = showInputDialog(null, "What is the price of the " +
                                                "shoe you wish to add:", "Happy Feet", QUESTION_MESSAGE);
                                        if (stringPrice == null) {
                                            return;
                                        }
                                    }
                                } while (!validPrice);
                                writer.println(price);
                                String stringQuantity = showInputDialog(null, "What is the quantity of the " +
                                        "shoe you wish to add:", "Happy Feet", QUESTION_MESSAGE);
                                int quantity = -1;
                                boolean validQuantity = false;
                                do {
                                    try {
                                        quantity = Integer.parseInt(stringQuantity);
                                        if (quantity >= 1 && !stringQuantity.equals("")) {
                                            validQuantity = true;
                                        } else {
                                            num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                                    DEFAULT_OPTION, PLAIN_MESSAGE);
                                            if (num == -1) {
                                                return;
                                            }
                                            stringQuantity = showInputDialog(null, "What is the quantity of the " +
                                                    "shoe you wish to add:", "Happy Feet", QUESTION_MESSAGE);
                                            if (stringQuantity == null) {
                                                return;
                                            }
                                        }
                                    } catch (NumberFormatException n) {
                                        num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                                DEFAULT_OPTION, PLAIN_MESSAGE);
                                        if (num == -1) {
                                            return;
                                        }
                                        stringQuantity = showInputDialog(null, "What is the quantity of the " +
                                                "shoe you wish to add:", "Happy Feet", QUESTION_MESSAGE);
                                        if (stringQuantity == null) {
                                            return;
                                        }
                                    }
                                } while (!validQuantity);
                                writer.println(quantity);
                                String addShoeResult = reader.readLine(); // USED TO CHECK IF SHOE WAS ADDED TO THE STORE
                                if (addShoeResult.equalsIgnoreCase("Shoe added")) {
                                    showMessageDialog(null, "Shoe added successfully!",
                                            "Happy Feet", PLAIN_MESSAGE);
                                } else if (addShoeResult.equalsIgnoreCase("Shoe could not be added")) {
                                    showMessageDialog(null, "Shoe could not be added :(",
                                            "Happy Feet", ERROR_MESSAGE);
                                }

                            }
                        }

                        // REMOVE SHOE
                        else if (chosenOption.equalsIgnoreCase("Remove a Shoe")) {
                            shoeName = showInputDialog(null,
                                    "What is the name of the shoe you would like to remove?", "Happy Feet", QUESTION_MESSAGE);
                            if (shoeName == null) {
                                return;
                            }
                            while (shoeName.equals("")) {
                                num = showConfirmDialog(null, "You do not own this store!", "Happy Feet",
                                        DEFAULT_OPTION, PLAIN_MESSAGE);
                                if (num == -1) {
                                    return;
                                }
                                shoeName = showInputDialog(null,
                                        "What is the name of the shoe you would like to remove?", "Happy Feet", QUESTION_MESSAGE);
                                if (shoeName == null) {
                                    return;
                                }
                            }
                            writer.println(shoeName);
                            ArrayList<String> stores = new ArrayList<>();
                            String s = "";
                            while ((s = reader.readLine()) != null) {
                                stores.add(s);
                                if (reader.readLine().equalsIgnoreCase("Done")) {
                                    break;
                                }
                            }
                            String[] size = new String[stores.size()];
                            String[] storeArray = stores.toArray(size);
                            storeName = (String) showInputDialog(null,
                                    "What store would you like to remove the Shoe from?", "Happy Feet", QUESTION_MESSAGE, null, storeArray, -1);
                            if (storeName == null) {
                                return;
                            }
                            writer.println(storeName);
                            String storeIndex = reader.readLine(); //GETS THE STORE INDEX, FROM THE SERVER TO CHECK IF STORE EXISTS
                            if (storeIndex.equals("-1")) {
                                num = showConfirmDialog(null, "You do not own this store!", "Happy Feet",
                                        DEFAULT_OPTION, PLAIN_MESSAGE);
                                if (num == -1) {
                                    return;
                                }
                            } else {
                                String shoeDescription = showInputDialog(null, "What is the " +
                                        "description of the shoe you would like to remove?", "Happy Feet", QUESTION_MESSAGE);
                                if (shoeDescription == null) {
                                    return;
                                }
                                while (shoeDescription.equals("")) {
                                    num = showConfirmDialog(null, "You do not own this store!", "Happy Feet",
                                            DEFAULT_OPTION, PLAIN_MESSAGE);
                                    if (num == -1) {
                                        return;
                                    }
                                    shoeDescription = showInputDialog(null, "What is the " +
                                            "description of the shoe you would like to remove?", "Happy Feet", QUESTION_MESSAGE);
                                    if (shoeDescription == null) {
                                        return;
                                    }
                                }
                                writer.println(shoeDescription);
                                String shoePrice = showInputDialog(null, "What is the " +
                                        "price of the shoe you would like to remove?", "Happy Feet", QUESTION_MESSAGE);
                                double price = -1;
                                boolean validPrice = false;
                                do {
                                    try {
                                        price = Double.parseDouble(shoePrice);
                                        if (price >= .01 && !shoePrice.equals("")) {
                                            validPrice = true;
                                        } else {
                                            num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                                    DEFAULT_OPTION, PLAIN_MESSAGE);
                                            if (num == -1) {
                                                return;
                                            }
                                            shoePrice = showInputDialog(null, "What is the price of the " +
                                                    "shoe you wish to remove:", "Happy Feet", QUESTION_MESSAGE);
                                            if (shoePrice == null) {
                                                return;
                                            }
                                        }
                                    } catch (NumberFormatException n) {
                                        num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                                DEFAULT_OPTION, PLAIN_MESSAGE);
                                        if (num == -1) {
                                            return;
                                        }
                                        shoePrice = showInputDialog(null, "What is the price of the " +
                                                "shoe you wish to remove:", "Happy Feet", QUESTION_MESSAGE);
                                        if (shoePrice == null) {
                                            return;
                                        }
                                    }
                                } while (!validPrice);
                                writer.println(price);
                                String shoeQuantity = showInputDialog(null, "What is the quantity of the " +
                                        "shoe you would like to remove?", "Happy Feet", QUESTION_MESSAGE);
                                if (shoeQuantity == null) {
                                    return;
                                }
                                int quantity = -1;
                                boolean validQuantity = false;
                                do {
                                    try {
                                        quantity = Integer.parseInt(shoeQuantity);
                                        if (quantity >= 1 && !shoeQuantity.equals("")) {
                                            validQuantity = true;
                                        } else {
                                            num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                                    DEFAULT_OPTION, PLAIN_MESSAGE);
                                            if (num == -1) {
                                                return;
                                            }
                                            shoeQuantity = showInputDialog(null, "What is the quantity of the " +
                                                    "shoe you wish to remove:", "Happy Feet", QUESTION_MESSAGE);
                                            if (shoeQuantity == null) {
                                                return;
                                            }
                                        }
                                    } catch (NumberFormatException n) {
                                        num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                                DEFAULT_OPTION, PLAIN_MESSAGE);
                                        if (num == -1) {
                                            return;
                                        }
                                        shoeQuantity = showInputDialog(null, "What is the quantity of the " +
                                                "shoe you wish to remove:", "Happy Feet", QUESTION_MESSAGE);
                                        if (shoeQuantity == null) {
                                            return;
                                        }
                                    }
                                } while (!validQuantity);
                                writer.println(quantity);
                                String addShoeResult = reader.readLine(); // USED TO CHECK IF SHOE WAS REMOVED FROM THE STORE
                                if (addShoeResult.equalsIgnoreCase("Shoe Removed!")) {
                                    showMessageDialog(null, "Shoe removed successfully!",
                                            "Happy Feet", PLAIN_MESSAGE);
                                } else if (addShoeResult.equalsIgnoreCase(storeName + " does not own " + shoeName + "'s!")) {
                                    showMessageDialog(null, storeName + " does not own " + shoeName + "'s!",
                                            "Happy Feet", ERROR_MESSAGE);
                                }
                            }
                        }

                        // EDIT SHOE
                        else if (chosenOption.equalsIgnoreCase("Edit a Shoe")) {
                            shoeName = showInputDialog(null, "What shoe do you want to edit",
                                    "Happy Feet", QUESTION_MESSAGE);
                            if (shoeName == null) {
                                return;
                            }
                            while (shoeName.equals("")) {
                                num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                        DEFAULT_OPTION, PLAIN_MESSAGE);
                                if (num == -1) {
                                    return;
                                }
                                shoeName = showInputDialog(null, "What shoe do you want to edit",
                                        "Happy Feet", QUESTION_MESSAGE);
                                if (shoeName == null) {
                                    return;
                                }
                            }
                            writer.println(shoeName);
                            storeName = showInputDialog(null, "What store does the shoe " +
                                    "belong to?", "Happy Feet", QUESTION_MESSAGE);
                            if (storeName == null) {
                                return;
                            }
                            while (storeName.equals("")) {
                                num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                        DEFAULT_OPTION, PLAIN_MESSAGE);
                                if (num == -1) {
                                    return;
                                }
                                storeName = showInputDialog(null, "What store does the shoe " +
                                        "belong to?", "Happy Feet", QUESTION_MESSAGE);
                                if (storeName == null) {
                                    return;
                                }
                            }
                            writer.println(storeName);
                            String storeIndex = reader.readLine();
                            if (storeIndex.equals("-1")) {
                                num = showConfirmDialog(null, "You do not own this store!", "Happy Feet",
                                        DEFAULT_OPTION, PLAIN_MESSAGE);
                                if (num == CLOSED_OPTION) {
                                    return;
                                }
                            } else {
                                String shoeIndex = reader.readLine();
                                if (shoeIndex.equals("-1")) {
                                    num = showConfirmDialog(null, storeName + " does not own this shoe!", "Happy Feet",
                                            DEFAULT_OPTION, PLAIN_MESSAGE);
                                    if (num == CLOSED_OPTION) {
                                        return;
                                    }
                                } else {
                                    int choice = showConfirmDialog(null, "Do you want to change the name of the Shoe?",
                                            "Happy Feet", YES_NO_OPTION);
                                    if (choice == YES_OPTION) {
                                        writer.println("Change Shoe Name");
                                        shoeName = showInputDialog(null, "What do you want the name of the shoe to be?");
                                        if (shoeName == null) {
                                            return;
                                        }
                                        while (shoeName.equals("")) {
                                            num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                                    DEFAULT_OPTION, PLAIN_MESSAGE);
                                            if (num == -1) {
                                                return;
                                            }
                                            shoeName = showInputDialog(null, "What do you want the name of the shoe to be?");
                                            if (shoeName == null) {
                                                return;
                                            }
                                        }
                                        writer.println(shoeName);
                                    } else {
                                        writer.println("");
                                    }
                                    choice = showConfirmDialog(null, "Do you want to change " +
                                            "the description of the Shoe?", "Happy Feet", YES_NO_OPTION);
                                    if (choice == YES_OPTION) {
                                        writer.println("Change Shoe Description");
                                        String shoeDescription = showInputDialog(null, "What do you want the " +
                                                "description of the Shoe to be?");
                                        if (shoeDescription == null) {
                                            return;
                                        }
                                        while (shoeDescription.equals("")) {
                                            num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                                    DEFAULT_OPTION, PLAIN_MESSAGE);
                                            if (num == -1) {
                                                return;
                                            }
                                            shoeDescription = showInputDialog(null, "What do you want the " +
                                                    "description of the Shoe to be?");
                                            if (shoeDescription == null) {
                                                return;
                                            }
                                        }
                                        writer.println(shoeDescription);
                                    } else {
                                        writer.println("");
                                    }
                                    choice = showConfirmDialog(null, "Do you want to change " +
                                            "the price of the Shoe?", "Happy Feet", YES_NO_OPTION);
                                    if (choice == YES_OPTION) {
                                        writer.println("Change Shoe Price");
                                        String shoePrice = showInputDialog(null, "What do you want the " +
                                                "price of the Shoe to be?");
                                        if (shoePrice.contains(".")) {
                                            int index = shoePrice.indexOf(".");
                                            while (shoePrice.substring(index + 1).length() > 2) {
                                                showMessageDialog(null, "Invalid");
                                                shoePrice = showInputDialog(null,
                                                        "What is the price of the " +
                                                                "shoe you wish to add:", "Happy Feet",
                                                        QUESTION_MESSAGE);
                                                index = shoePrice.indexOf(".");
                                            }
                                        }
                                        double price;
                                        boolean validPrice = false;
                                        do {
                                            try {
                                                price = Double.parseDouble(shoePrice);
                                                if (price >= .01 && !shoePrice.equals("")) {
                                                    validPrice = true;
                                                } else {
                                                    num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                                            DEFAULT_OPTION, PLAIN_MESSAGE);
                                                    if (num == -1) {
                                                        return;
                                                    }
                                                    shoePrice = showInputDialog(null, "What is the price of the " +
                                                            "shoe:", "Happy Feet", QUESTION_MESSAGE);
                                                    if (shoePrice == null) {
                                                        return;
                                                    }
                                                }
                                            } catch (NumberFormatException n) {
                                                num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                                        DEFAULT_OPTION, PLAIN_MESSAGE);
                                                if (num == -1) {
                                                    return;
                                                }
                                                shoePrice = showInputDialog(null, "What is the price of the " +
                                                        "shoe:", "Happy Feet", QUESTION_MESSAGE);
                                                if (shoePrice == null) {
                                                    return;
                                                }
                                            }
                                        } while (!validPrice);
                                        writer.println(shoePrice);
                                    } else {
                                        writer.println("");
                                    }
                                    choice = showConfirmDialog(null, "Do you want to change the " +
                                            "quantity of the Shoe?", "Happy Feet", YES_NO_OPTION);
                                    if (choice == YES_OPTION) {
                                        writer.println("Change Shoe Quantity");
                                        String shoeQuantity = showInputDialog(null, "What do you want " +
                                                "the quantity of the Shoe to be?");
                                        if (shoeQuantity == null) {
                                            return;
                                        }
                                        int quantity = -1;
                                        boolean validQuantity = false;
                                        do {
                                            try {
                                                quantity = Integer.parseInt(shoeQuantity);
                                                if (quantity >= 1 && !shoeQuantity.equals("")) {
                                                    validQuantity = true;
                                                } else {
                                                    num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                                            DEFAULT_OPTION, PLAIN_MESSAGE);
                                                    if (num == -1) {
                                                        return;
                                                    }
                                                    shoeQuantity = showInputDialog(null, "What is the quantity of the " +
                                                            "shoe:", "Happy Feet", QUESTION_MESSAGE);
                                                    if (shoeQuantity == null) {
                                                        return;
                                                    }
                                                }
                                            } catch (NumberFormatException n) {
                                                num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                                        DEFAULT_OPTION, PLAIN_MESSAGE);
                                                if (num == -1) {
                                                    return;
                                                }
                                                shoeQuantity = showInputDialog(null, "What is the quantity of the " +
                                                        "shoe:", "Happy Feet", QUESTION_MESSAGE);
                                                if (shoeQuantity == null) {
                                                    return;
                                                }
                                            }
                                        } while (!validQuantity);
                                        writer.println(shoeQuantity);
                                    } else {
                                        writer.println("");
                                    }
                                    String valid = reader.readLine();
                                    if (valid.equals("Y")) {
                                        num = showConfirmDialog(null, "Shoe Successfully Edited!",
                                                "Happy Feet", DEFAULT_OPTION, PLAIN_MESSAGE);
                                        if (num == CLOSED_OPTION) {
                                            return;
                                        }
                                    } else {
                                        num = showConfirmDialog(null, "Shoe Could Not Be Edited",
                                                "Happy Feet", DEFAULT_OPTION, PLAIN_MESSAGE);
                                        if (num == CLOSED_OPTION) {
                                            return;
                                        }
                                    }
                                }
                            }
                        }

                        // VIEW SALES
                        else if (chosenOption.equalsIgnoreCase("View your sales information")) {
                            String[] arr = {"Sort by Customer Sales", "Sort by Store Sales", "No Filters"};
                            String option = (String) showInputDialog(null, "Sort the DashBoard", "Happy Feet", QUESTION_MESSAGE, null,
                                    arr, -1);
                            if (option == null) {
                                return;
                            }
                            if (option.equalsIgnoreCase("no filters")) {
                                writer.println("none");
                                String s = (String) ois.readObject();
                                num = showConfirmDialog(null, s,
                                        "Happy Feet", DEFAULT_OPTION, PLAIN_MESSAGE);
                                if (num == CLOSED_OPTION) {
                                    return;
                                }
                            } else if (option.equalsIgnoreCase("sort by store sales")) {
                                writer.println("store");
                                String s = (String) ois.readObject();
                                num = showConfirmDialog(null, s,
                                        "Happy Feet", DEFAULT_OPTION, PLAIN_MESSAGE);
                                if (num == CLOSED_OPTION) {
                                    return;
                                }
                            } else if (option.equalsIgnoreCase("sort by customer sales")) {
                                writer.println("customer");
                                String s = (String) ois.readObject();
                                num = showConfirmDialog(null, s,
                                        "Happy Feet", DEFAULT_OPTION, PLAIN_MESSAGE);
                                if (num == CLOSED_OPTION) {
                                    return;
                                }
                            }
                        }
                        // CHANGE E-MAIL
                        else if (chosenOption.equalsIgnoreCase("Change Email")) {
                            String newEmail = showInputDialog(null, "Enter your new Email:");
                            if (newEmail == null) {
                                return;
                            }
                            while (!MarketPlace.checkEmail(newEmail) || newEmail.equals("")) {
                                num = showConfirmDialog(null, "Invalid E-Mail or Taken E-Mail", "Happy Feet",
                                        DEFAULT_OPTION, PLAIN_MESSAGE);
                                if (num == -1) {
                                    return;
                                }
                                newEmail = showInputDialog(null, "Enter your new Email:");
                                if (newEmail == null) {
                                    return;
                                }
                            }
                            writer.println(newEmail);
                        }
                        else if (chosenOption.equalsIgnoreCase("Change Password")) {
                            String newPass;
                            while (true) {
                                newPass = showInputDialog(null, "What do you want your new password to be?");
                                if (newPass == null) {
                                    return;
                                }
                                if (newPass.length() < 5) {
                                    num = showConfirmDialog(null, "Password must be greater than 5 characters!",
                                            "Happy Feet", DEFAULT_OPTION, PLAIN_MESSAGE);
                                    if (num == CLOSED_OPTION) {
                                        return;
                                    }
                                    continue;
                                }
                                break;
                            }
                            writer.println(newPass);
                        }
                        else if (chosenOption.equalsIgnoreCase("Import products from a file")) {
                            String input = showInputDialog(null, "Enter the file path");
                            if (input == null) {
                                return;
                            }
                            while (input.equals("")) {
                                num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                        DEFAULT_OPTION, PLAIN_MESSAGE);
                                if (num == -1) {
                                    return;
                                }
                                input = showInputDialog(null, "Enter the file path");
                                if (input == null) {
                                    return;
                                }
                            }
                            writer.println(input);
                            String success = reader.readLine();
                            if (success.equalsIgnoreCase("true")) {
                                num = showConfirmDialog(null, "Your products have been successfully " +
                                                "imported to the Market!",
                                        "Happy Feet", DEFAULT_OPTION, PLAIN_MESSAGE);
                                if (num == CLOSED_OPTION) {
                                    return;
                                }
                            } else {
                                num = showConfirmDialog(null, "Unable to import your products to " +
                                                "the market.",
                                        "Happy Feet", DEFAULT_OPTION, PLAIN_MESSAGE);
                                if (num == CLOSED_OPTION) {
                                    return;
                                }

                            }
                        }
                        else if (chosenOption.equalsIgnoreCase("Export products to a file")) {
                            String input = showInputDialog(null, "Enter the name of the file you would " +
                                    "like to see your products in.");
                            if (input == null) {
                                return;
                            }
                            while (input.equals("")) {
                                num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                        DEFAULT_OPTION, PLAIN_MESSAGE);
                                if (num == -1) {
                                    return;
                                }
                                input = showInputDialog(null, "Enter the file path");
                                if (input == null) {
                                    return;
                                }
                            }
                            writer.println(input);
                            String success = reader.readLine();
                            if (success.equalsIgnoreCase("true")) {
                                num = showConfirmDialog(null, "Your products have been successfully exported to " + input,
                                        "Happy Feet", DEFAULT_OPTION, PLAIN_MESSAGE);
                                if (num == CLOSED_OPTION) {
                                    return;
                                }
                            } else {
                                num = showConfirmDialog(null, "Unable to export products to File Name: " + input,
                                        "Happy Feet", DEFAULT_OPTION, PLAIN_MESSAGE);
                                if (num == CLOSED_OPTION) {
                                    return;
                                }
                            }
                        }
                    } catch (NullPointerException e) {
                        return;
                    }


                    performAnotherActivity = showConfirmDialog(null,
                            "Would you like to perform another activity", "Happy Feet", YES_NO_OPTION);
                    writer.println(performAnotherActivity);
                } while (performAnotherActivity == YES_OPTION);  //LOOPS OVER SELLER MENU AGAIN IF CLIENT SELECTS YES


            } else { // Customer Implementation
                String storeName;
                String shoeName;
                int num = showConfirmDialog(null, "Welcome Customer!",
                        "Happy Feet", DEFAULT_OPTION, PLAIN_MESSAGE);
                if (num == CLOSED_OPTION) {
                    return;
                }

                int performAnotherActivity;
                String[] customerMenuOptions = {MarketPlace.VIEW_MARKET, MarketPlace.SEARCH_MARKET, MarketPlace.REVIEW_PURCHASE_HISTORY, MarketPlace.EXPORT_SHOE, MarketPlace.CHANGE_CUSTOMER_EMAIL, MarketPlace.CHANGE_CUSTOMER_PASSWORD, MarketPlace.PURCHASE_SHOE, MarketPlace.VIEW_MARKET_STATISTICS};

                // PRESENTS CUSTOMER MENU

                do {
                    String chosenOption = (String) showInputDialog(null, "Select an Option",
                            "Happy Feet", INFORMATION_MESSAGE, null, customerMenuOptions, 0);
                    if (chosenOption == null) {
                        return;
                    }

                    // SENDS THE CHOSEN OPTION TO THE SERVER
                    writer.println(chosenOption);
                    if (chosenOption.equalsIgnoreCase(MarketPlace.VIEW_MARKET)) {
                        Object[] cols = {"Seller ID", "Store Name", "Shoe Name", "Shoe Price"};
                        ArrayList<ArrayList<String>> marketList = new ArrayList<>();
                        ArrayList<String> shoe = new ArrayList<>();
                        String response;
                        while (!(reader.readLine().equals("done writing"))) {
                            while (!((response = reader.readLine())).equals("done")) {
                                shoe.add(response);
                            }
                            marketList.add(shoe);
                            shoe = new ArrayList<>();
                        }

                        if (marketList.size() == 0) {
                            showMessageDialog(null, "No shoes in the market!",
                                    "Happy Feet", ERROR_MESSAGE);
                        } else {
                            Object[][] rows = new Object[marketList.size()][4];
                            for (int i = 0; i < marketList.size(); i++) {
                                for (int j = 0; j < marketList.get(i).size(); j++) {
                                    rows[i][j] = marketList.get(i).get(j);
                                }
                            }
                            JTable table = new JTable(rows, cols);
                            showMessageDialog(null, new JScrollPane(table));
                        }
                        int purchaseChoice = showConfirmDialog(null, "Do you want to purchase any of those shoes?");
                        if (purchaseChoice == YES_OPTION) {
                            writer.println("wants to purchase");
                            if (marketList.size() == 0) {
                                showMessageDialog(null, "No shoes in the market!",
                                        "Happy Feet", ERROR_MESSAGE);
                            } else {
                                ArrayList<String> s = new ArrayList<>();
                                for (int i = 0; i < marketList.size(); i++) {
                                    String string = "";
                                    for (int j = 0; j < marketList.get(i).size(); j++) {
                                        if (j == 0) {
                                            string += "Seller ID: " + marketList.get(i).get(j) + " | ";
                                        } else if (j == 1) {
                                            string += "Store Name: " + marketList.get(i).get(j) + " | ";
                                        } else if (j == 2) {
                                            string += "Shoe Name: " + marketList.get(i).get(j) + " | ";
                                        } else {
                                            string += "Shoe Price: $" + marketList.get(i).get(j);
                                        }
                                    }
                                    s.add(string);
                                }
                                String[] strings = new String[s.size()];
                                String[] arr = s.toArray(strings);
                                String input = (String) showInputDialog(null, "Purchase a shoe.", "Happy Feet",
                                        INFORMATION_MESSAGE, null, arr, -1);
                                String sellerID = input.substring(input.indexOf(":") + 1,input.indexOf("|"));
                                sellerID = sellerID.trim();
                                input = input.substring(input.indexOf("|") + 1,input.length()-1);
                                writer.println(sellerID);

                                String store= input.substring(input.indexOf(":") + 1,input.indexOf("|"));
                                store = store.trim();
                                input = input.substring(input.indexOf("|") + 1,input.length()-1);
                                writer.println(store);

                                String wantedShoeName = input.substring(input.indexOf(":") + 1,input.indexOf("|"));
                                wantedShoeName = wantedShoeName.trim();
                                writer.println(wantedShoeName);

                                String shoeInfo = (String) ois.readObject();
                                showMessageDialog(null, shoeInfo);
                                String quantity = showInputDialog(null, "How many pairs would you like to purchase");
                                boolean validResponse = false;
                                do {
                                    try {
                                        Integer.parseInt(quantity);
                                        if (Integer.parseInt(quantity) < 1) {
                                            showMessageDialog(null, "Invalid Value.");
                                        } else if (Integer.parseInt(quantity) > Integer.parseInt(shoeInfo.split(":")[5].trim())) {
                                            showMessageDialog(null, "Sorry, we do not have that many pairs on stock");
                                        } else {
                                            validResponse = true;
                                        }

                                    } catch (NumberFormatException n) {
                                        showMessageDialog(null, MarketPlace.INVALID_VALUE, "Happy Feet", ERROR_MESSAGE);
                                    }
                                    if(!validResponse) {
                                        quantity = showInputDialog(null, "How many pairs would you like to purchase?");
                                    }
                                } while (!validResponse);
                                writer.println(quantity);
                                showMessageDialog(null, reader.readLine());
                            }
                        } else {
                            writer.println("YO");
                        }
                    } else if (chosenOption.equalsIgnoreCase(MarketPlace.SEARCH_MARKET)) {
                        ArrayList<ArrayList<String>> marketList = new ArrayList<>();
                        ArrayList<String> shoe = new ArrayList<>();
                        String response;
                        Object[] cols = {"Seller ID", "Store Name", "Shoe Name", "Shoe Price"};
                        String[] viewOptions = {"Search by Store Name.", "Search by Shoe Name.", "Search by Shoe Description.", "Sort by Price.", "Sort by Quantity"};
                        String searchChoice = (String) showInputDialog(null, "Select an Option", "Happy Feet", INFORMATION_MESSAGE, null, viewOptions, 0);
                        writer.println(searchChoice);
                        if (searchChoice.equalsIgnoreCase("Search by Store Name.")) {
                            String store = showInputDialog(null, "What is the name of the store?");
                            writer.println(store);
                            while (!(reader.readLine().equals("done writing"))) {
                                while (!((response = reader.readLine())).equals("done")) {
                                    shoe.add(response);
                                }
                                marketList.add(shoe);
                                shoe = new ArrayList<>();
                            }
                            if (marketList.size() == 0) {
                                showMessageDialog(null, "No shoes in the market!",
                                        "Happy Feet", ERROR_MESSAGE);
                            } else {
                                Object[][] rows = new Object[marketList.size()][4];
                                for (int i = 0; i < marketList.size(); i++) {
                                    for (int j = 0; j < marketList.get(i).size(); j++) {
                                        rows[i][j] = marketList.get(i).get(j);
                                    }
                                }
                                JTable table = new JTable(rows, cols);
                                showMessageDialog(null, new JScrollPane(table));
                            }
                        } else if (searchChoice.equalsIgnoreCase("Search by Shoe Name.")) {
                            String name = showInputDialog(null, "What is the name of the shoe?");
                            writer.println(name);
                            while (!(reader.readLine().equals("done writing"))) {
                                while (!((response = reader.readLine())).equals("done")) {
                                    shoe.add(response);
                                }
                                marketList.add(shoe);
                                shoe = new ArrayList<>();
                            }
                            if (marketList.size() == 0) {
                                showMessageDialog(null, "No shoes in the market!",
                                        "Happy Feet", ERROR_MESSAGE);
                            } else {
                                Object[][] rows = new Object[marketList.size()][4];
                                for (int i = 0; i < marketList.size(); i++) {
                                    for (int j = 0; j < marketList.get(i).size(); j++) {
                                        rows[i][j] = marketList.get(i).get(j);
                                    }
                                }
                                JTable table = new JTable(rows, cols);
                                showMessageDialog(null, new JScrollPane(table));
                            }
                        } else if (searchChoice.equalsIgnoreCase("Search by Shoe Description.")) {
                            String Description = showInputDialog(null, "What is the description of the shoe?");
                            writer.println(Description);
                            while (!(reader.readLine().equals("done writing"))) {
                                while (!((response = reader.readLine())).equals("done")) {
                                    shoe.add(response);
                                }
                                marketList.add(shoe);
                                shoe = new ArrayList<>();
                            }
                            if (marketList.size() == 0) {
                                showMessageDialog(null, "No shoes in the market!",
                                        "Happy Feet", ERROR_MESSAGE);
                            } else {
                                Object[][] rows = new Object[marketList.size()][4];
                                for (int i = 0; i < marketList.size(); i++) {
                                    for (int j = 0; j < marketList.get(i).size(); j++) {
                                        rows[i][j] = marketList.get(i).get(j);
                                    }
                                }
                                JTable table = new JTable(rows, cols);
                                showMessageDialog(null, new JScrollPane(table));
                            }
                        } else if (searchChoice.equalsIgnoreCase("Sort by price.")) {
                            String Price = showInputDialog(null, "What is the price you want to sort by?");
                            boolean validResponse = false;
                            do {
                                try {
                                    Double.parseDouble(Price);
                                    validResponse = true;
                                } catch (NumberFormatException n) {
                                    Price = showInputDialog(null, "What is the price you want to sort by?");
                                }
                            } while (!validResponse);
                            writer.println(Price);
                            while (!(reader.readLine().equals("done writing"))) {
                                while (!((response = reader.readLine())).equals("done")) {
                                    shoe.add(response);
                                }
                                marketList.add(shoe);
                                shoe = new ArrayList<>();
                            }
                            if (marketList.size() == 0) {
                                showMessageDialog(null, "No shoes in the market!",
                                        "Happy Feet", ERROR_MESSAGE);
                            } else {
                                Object[][] rows = new Object[marketList.size()][4];
                                for (int i = 0; i < marketList.size(); i++) {
                                    for (int j = 0; j < marketList.get(i).size(); j++) {
                                        rows[i][j] = marketList.get(i).get(j);
                                    }
                                }
                                JTable table = new JTable(rows, cols);
                                showMessageDialog(null, new JScrollPane(table));
                            }
                        } else if (searchChoice.equalsIgnoreCase("sort by quantity")) {
                            String quantity = showInputDialog(null, "What is quantity you want to sort by?");
                            boolean validResponse = false;
                            do {
                                try {
                                    Integer.parseInt(quantity);
                                    validResponse = true;
                                } catch (NumberFormatException n) {
                                    showMessageDialog(null, MarketPlace.INVALID_VALUE, "Happy Feet", ERROR_MESSAGE);
                                    quantity = showInputDialog(null, "What is quantity you want to sort by?");
                                }
                            } while (!validResponse);
                            writer.println(quantity);
                            while (!(reader.readLine().equals("done writing"))) {
                                while (!((response = reader.readLine())).equals("done")) {
                                    shoe.add(response);
                                }
                                marketList.add(shoe);
                                shoe = new ArrayList<>();
                            }
                            if (marketList.size() == 0) {
                                showMessageDialog(null, "No shoes in the market!",
                                        "Happy Feet", ERROR_MESSAGE);
                            } else {
                                Object[][] rows = new Object[marketList.size()][4];
                                for (int i = 0; i < marketList.size(); i++) {
                                    for (int j = 0; j < marketList.get(i).size(); j++) {
                                        rows[i][j] = marketList.get(i).get(j);
                                    }
                                }
                                JTable table = new JTable(rows, cols);
                                showMessageDialog(null, new JScrollPane(table));
                            }
                        }
                        int purchaseChoice = showConfirmDialog(null, "Do you " +
                                "want to purchase any of those shoes?");
                        if (purchaseChoice == CANCEL_OPTION || purchaseChoice == CLOSED_OPTION) {
                            return;
                        }
                        if (purchaseChoice == YES_OPTION) {
                            writer.println("wants to purchase");
                            if (marketList.size() == 0) {
                                showMessageDialog(null, "No shoes in the market!",
                                        "Happy Feet", ERROR_MESSAGE);
                            } else {
                                ArrayList<String> s = new ArrayList<>();
                                for (int i = 0; i < marketList.size(); i++) {
                                    String string = "";
                                    for (int j = 0; j < marketList.get(i).size(); j++) {
                                        if (j == 0) {
                                            string += "Seller ID: " + marketList.get(i).get(j) + " | ";
                                        } else if (j == 1) {
                                            string += "Store Name: " + marketList.get(i).get(j) + " | ";
                                        } else if (j == 2) {
                                            string += "Shoe Name: " + marketList.get(i).get(j) + " | ";
                                        } else {
                                            string += "Shoe Price: $" + marketList.get(i).get(j);
                                        }
                                    }
                                    s.add(string);
                                }
                                String[] strings = new String[s.size()];
                                String[] arr = s.toArray(strings);
                                String input = (String) showInputDialog(null, "Purchase a shoe.", "Happy Feet",
                                        INFORMATION_MESSAGE, null, arr, -1);
                                String sellerID = input.substring(input.indexOf(":") + 1,input.indexOf("|"));
                                sellerID = sellerID.trim();
                                input = input.substring(input.indexOf("|") + 1,input.length()-1);
                                writer.println(sellerID);

                                String store= input.substring(input.indexOf(":") + 1,input.indexOf("|"));
                                store = store.trim();
                                input = input.substring(input.indexOf("|") + 1,input.length()-1);
                                writer.println(store);

                                String wantedShoeName = input.substring(input.indexOf(":") + 1,input.indexOf("|"));
                                wantedShoeName = wantedShoeName.trim();
                                writer.println(wantedShoeName);

                                String shoeInfo = (String) ois.readObject();
                                showMessageDialog(null, shoeInfo);
                                String quantity = showInputDialog(null, "How many pairs would you like to purchase");
                                boolean validResponse = false;
                                do {
                                    try {
                                        Integer.parseInt(quantity);
                                        if (Integer.parseInt(quantity) < 1) {
                                            showMessageDialog(null, "Invalid Value.");
                                        } else if (Integer.parseInt(quantity) > Integer.parseInt(shoeInfo.split(":")[5].trim())) {
                                            showMessageDialog(null, "Sorry, we do not have that many pairs on stock");
                                        } else {
                                            validResponse = true;
                                        }

                                    } catch (NumberFormatException n) {
                                        showMessageDialog(null, MarketPlace.INVALID_VALUE, "Happy Feet", ERROR_MESSAGE);
                                    }
                                    if(!validResponse) {
                                        quantity = showInputDialog(null, "How many pairs would you like to purchase?");
                                    }
                                } while (!validResponse);
                                writer.println(quantity);
                                showMessageDialog(null, reader.readLine());
                            }
                        } else {
                            writer.println("YOO");
                        }
                    } else if (chosenOption.equalsIgnoreCase(MarketPlace.REVIEW_PURCHASE_HISTORY)) {
                        String result = (String) ois.readObject();
                        if (result.startsWith("Total")) {
                            num = showConfirmDialog(null, result, "Happy Feet",
                                    DEFAULT_OPTION, PLAIN_MESSAGE);
                            if (num == -1) {
                                return;
                            }
                        } else {
                            num = showConfirmDialog(null, result, "Happy Feet",
                                    DEFAULT_OPTION, PLAIN_MESSAGE);
                            if (num == -1) {
                                return;
                            }
                        }
                    } else if (chosenOption.equalsIgnoreCase(MarketPlace.EXPORT_SHOE)) {
                        String input = showInputDialog(null, "What is the name of the file you like to see your purchase history in?");
                        if (input == null) {
                            return;
                        }
                        while (input.equals("")) {
                            num = showConfirmDialog(null, "Invalid Value", "Happy Feet",
                                    DEFAULT_OPTION, PLAIN_MESSAGE);
                            if (num == -1) {
                                return;
                            }
                            input = showInputDialog(null, "Enter the file path");
                            if (input == null) {
                                return;
                            }
                        }
                        writer.println(input);
                        String result = reader.readLine();
                        if (result.startsWith("Total")) {
                            num = showConfirmDialog(null, result, "Happy Feet",
                                    DEFAULT_OPTION, PLAIN_MESSAGE);
                            if (num == -1) {
                                return;
                            }
                        } else {
                            num = showConfirmDialog(null, result, "Happy Feet",
                                    DEFAULT_OPTION, PLAIN_MESSAGE);
                            if (num == -1) {
                                return;
                            }
                        }
                    } else if (chosenOption.equalsIgnoreCase(MarketPlace.CHANGE_CUSTOMER_EMAIL)) {
                        String newEmail = showInputDialog(null, "Enter your new Email:");
                        if (newEmail == null) {
                            return;
                        }
                        while (!MarketPlace.checkEmail(newEmail) || newEmail.equals("")) {
                            num = showConfirmDialog(null, "Invalid E-Mail or Taken E-Mail", "Happy Feet",
                                    DEFAULT_OPTION, PLAIN_MESSAGE);
                            if (num == -1) {
                                return;
                            }
                            newEmail = showInputDialog(null, "Enter your new Email:");
                            if (newEmail == null) {
                                return;
                            }
                        }
                        writer.println(newEmail);
                    }
                    else if (chosenOption.equalsIgnoreCase(MarketPlace.CHANGE_CUSTOMER_PASSWORD)) {
                        String newPass;
                        while (true) {
                            newPass = showInputDialog(null, "What do you want your new password to be?");
                            if (newPass == null) {
                                return;
                            }
                            if (newPass.length() < 5) {
                                showMessageDialog(null, "Password must be greater than 5 characters!", "Happy Feet", ERROR_MESSAGE);
                                continue;
                            }
                            break;
                        }
                        writer.println(newPass);
                    }
                    else if (chosenOption.equalsIgnoreCase(MarketPlace.PURCHASE_SHOE)) {
                        ArrayList<ArrayList<String>> marketList = new ArrayList<>();
                        ArrayList<String> shoe = new ArrayList<>();
                        String response;
                        while (!(reader.readLine().equals("done writing"))) {
                            while (!((response = reader.readLine())).equals("done")) {
                                shoe.add(response);
                            }
                            marketList.add(shoe);
                            shoe = new ArrayList<>();
                        }


                        if (marketList.size() == 0) {
                            num = showConfirmDialog(null, "No shoes in the market!", "Happy Feet",
                                    DEFAULT_OPTION, PLAIN_MESSAGE);
                            if (num == -1) {
                                return;
                            }
                        } else {
                            ArrayList<String> s = new ArrayList<>();
                            for (int i = 0; i < marketList.size(); i++) {
                                String string = "";
                                for (int j = 0; j < marketList.get(i).size(); j++) {
                                    if (j == 0) {
                                        string += "Seller ID: " + marketList.get(i).get(j) + " | ";
                                    } else if (j == 1) {
                                        string += "Store Name: " + marketList.get(i).get(j) + " | ";
                                    } else if (j == 2) {
                                        string += "Shoe Name: " + marketList.get(i).get(j) + " | ";
                                    } else {
                                        string += "Shoe Price: $" + marketList.get(i).get(j);
                                    }
                                }
                                s.add(string);
                            }
                            String[] strings = new String[s.size()];
                            String[] arr = s.toArray(strings);
//
                            String input = (String) showInputDialog(null, "Purchase a shoe.", "Happy Feet",
                                    INFORMATION_MESSAGE, null, arr, -1);
                            if (input == null) {
                                return;
                            }
                            String sellerID = input.substring(input.indexOf(":") + 1, input.indexOf("|"));
                            sellerID = sellerID.trim();
                            input = input.substring(input.indexOf("|") + 1, input.length() - 1);
                            writer.println(sellerID);

                            String store = input.substring(input.indexOf(":") + 1, input.indexOf("|"));
                            store = store.trim();
                            input = input.substring(input.indexOf("|") + 1, input.length() - 1);
                            writer.println(store);

                            String wantedShoeName = input.substring(input.indexOf(":") + 1, input.indexOf("|"));
                            wantedShoeName = wantedShoeName.trim();
                            writer.println(wantedShoeName);

                            String shoeInfo = (String) ois.readObject();
                            num = showConfirmDialog(null, shoeInfo, "Happy Feet",
                                    DEFAULT_OPTION, PLAIN_MESSAGE);
                            if (num == -1) {
                                return;
                            }
                            String quantity = showInputDialog(null, "How many pairs would you like to purchase");
                            if (quantity == null) {
                                return;
                            }
                            boolean validResponse = false;
                            do {
                                try {
                                    Integer.parseInt(quantity);
                                    if (Integer.parseInt(quantity) < 1) {
                                        showMessageDialog(null, "Invalid Value.");
                                    } else if (Integer.parseInt(quantity) > Integer.parseInt(shoeInfo.split(":")[5].trim())) {
                                        showMessageDialog(null, "Sorry, we do not have that many pairs on stock");
                                    } else {
                                        validResponse = true;
                                    }

                                } catch (NumberFormatException n) {
                                    showMessageDialog(null, MarketPlace.INVALID_VALUE, "Happy Feet", ERROR_MESSAGE);
                                }
                                if (!validResponse) {
                                    quantity = showInputDialog(null, "How many pairs would you like to purchase?");
                                    if (quantity == null) {
                                        return;
                                    }
                                }
                            } while (!validResponse);
                            writer.println(quantity);
                            num = showConfirmDialog(null, reader.readLine(), "Happy Feet",
                                    DEFAULT_OPTION, PLAIN_MESSAGE);
                            if (num == -1) {
                                return;
                            }
                        }
                    }
                    else if (chosenOption.equalsIgnoreCase(MarketPlace.VIEW_MARKET_STATISTICS)) {
                        int sort = showConfirmDialog(null, "Would you like to sort " +
                                "the dashboard?", "Happy Feet", YES_NO_OPTION);
                        if (sort == YES_OPTION) {
                            String[] options = {"Sort by number of products sold in every store",
                                    "Sort by number of products sold in stores you have purchased from"};
                            String sortBy = (String) showInputDialog(null, "Select an Option",
                                    "Happy Feet", INFORMATION_MESSAGE, null, options, 0);
                            if (sortBy == null) {
                                return;
                            }
                            if (sortBy.equals("Sort by number of products sold in every store")) {
                                writer.println("Sort by number of products sold in every store");
                                String result = (String) ois.readObject();
                                num = showConfirmDialog(null, result, "Happy Feet",
                                        DEFAULT_OPTION, PLAIN_MESSAGE);
                                if (num == -1) {
                                    return;
                                }
                            } else {
                                writer.println("Sort by number of products sold in stores you have purchased from");
                                String result = (String) ois.readObject();
                                num = showConfirmDialog(null, result, "Happy Feet",
                                        DEFAULT_OPTION, PLAIN_MESSAGE);
                                if (num == -1) {
                                    return;
                                }
                            }
                        } else if (sort == NO_OPTION) {
                            writer.println("No");
                            String result = (String) ois.readObject();
                            num = showConfirmDialog(null, result, "Happy Feet",
                                    DEFAULT_OPTION, PLAIN_MESSAGE);
                            if (num == -1) {
                                return;
                            }
                        } else if (sort == CLOSED_OPTION) {
                            return;
                        }
                    }
                    performAnotherActivity = showConfirmDialog(null,
                            "Would you like to perform another activity", "Happy Feet", YES_NO_OPTION);
                    writer.println(performAnotherActivity);
                } while (performAnotherActivity == YES_OPTION);


            }

        } catch (IOException e) {
            showMessageDialog(null, "Client Disconnected");
        } catch (ClassNotFoundException e) {
            showMessageDialog(null, "Client Disconnected");
        } catch (NullPointerException n) {
            showMessageDialog(null, "Client Disconnected");
        }
    }
}
