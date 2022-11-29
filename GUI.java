import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import javax.swing.*;

public class GUI extends JComponent implements Runnable  {


    // General Frame
    JFrame frame;
    Container pane;
    JButton backButton;

    // Welcome Panel Layout
    JLabel welcomeLabel;
    JButton welcomeLoginButton;
    JButton welcomeCreateAccountButton;
    JPanel welcomePanel;
    // End of Welcome Panel Layout


    // Login Panel Layout
    JTextField username;
    JLabel userLabel;
    JPasswordField password;
    JLabel passwordLabel;

    JButton loginButton;

    JPanel loginPanel;
    // End of Login Panel Layout


    // Create Account Page
    JPanel createAccountPanel;
    JTextField enterEmailTextField;
    JTextField enterPasswordTextField;
    JButton createAccountButton;
    JLabel createHappyFeetAccount;
    JLabel enterEmail;
    JLabel enterPassword;
    JLabel userTypes;

    JComboBox<String> sellerOrCustomer;
    //

    // Seller Panel Layout
    JPanel sellerPanel;
    JComboBox<String> sellerOptions;
    JLabel sellerOptionsLabel;
    // End of Seller Panel Layout

    // Customer Panel Layout
    JPanel customerPanel;
    JComboBox<String> customerOptions;
    JLabel customerOptionsLabel;
    // End of Customer Panel Layout

    boolean login = false;
    boolean createAccount = false;


    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           if (e.getSource() == welcomeLoginButton) {
              welcomePanel.setVisible(false);
              setLoginPageVisible();
           }
           if (e.getSource() == welcomeCreateAccountButton) {
               welcomePanel.setVisible(false);
               setCreateAccountPageVisible();
           }
           if (e.getSource() == loginButton) {
               login = true; // set this to true, set it to false if they hit back button
               // send to server, validate text fields, then continue, BUT SHOULD SERVER TO GUI OR CLIENT
               loginPanel.setVisible(false);
               setSellerPageVisible();
           }
           if (e.getSource() == createAccountButton) {
               createAccount = true; // set this to true, set it to false if they hit back button
               if (sellerOrCustomer.getSelectedIndex() == 0) {
                   createAccountPanel.setVisible(false);
                   setSellerPageVisible();
               } else {
                   createAccountPanel.setVisible(false);
                   setCustomerPageVisible();
               }
           }

        }
    };
    public void dispose() {
        this.frame.dispose();
    }


    public void setWelcomePageVisible() {
        // Welcome-Page GUI
        welcomePanel = new JPanel();
        welcomeLoginButton = new JButton("Log in.");
        welcomeLoginButton.addActionListener(actionListener);

        welcomeCreateAccountButton = new JButton("Create An Account.");
        welcomeCreateAccountButton.addActionListener(actionListener);

        welcomeLabel = new JLabel("Welcome to Happy Feet");
        welcomeLabel.setFont(new Font("Serif", Font.PLAIN, 30));

        welcomePanel.add(welcomeLabel);
        welcomePanel.add(welcomeLoginButton);
        welcomePanel.add(welcomeCreateAccountButton);
        welcomePanel.setVisible(true);
        pane.add(welcomePanel);
        // End of Welcome-Page GUI
    }
    public void setLoginPageVisible() {
        username = new JTextField(5);
        password = new JPasswordField(5);
        userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        loginButton = new JButton("Login");
        loginButton.addActionListener(actionListener);
        backButton = new JButton("Back");
        loginPanel = new JPanel();
        loginPanel.add(userLabel);
        loginPanel.add(username);
        loginPanel.add(passwordLabel);
        loginPanel.add(password);
        loginPanel.add(loginButton);
        loginPanel.add(backButton);
        loginPanel.setVisible(true);
        pane.add(loginPanel);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == backButton) {
                    loginPanel.setVisible(false);
                    setWelcomePageVisible();
                    login = false;
                }
            }
        });
    }
    public void setCreateAccountPageVisible() {
        createAccountPanel = new JPanel();
        enterEmailTextField = new JTextField(5);
        enterPasswordTextField = new JPasswordField(5);
        createAccountButton = new JButton("Create New Account");
        createAccountButton.addActionListener(actionListener);
        enterEmail = new JLabel("Enter An Email");
        enterPassword = new JLabel("Enter a Password");
        userTypes = new JLabel("Select User Type");
        String[] strings = {"Seller", "Customer"};
        sellerOrCustomer = new JComboBox<>(strings);
        createHappyFeetAccount = new JLabel("Create New Happy Feet Account");
        createHappyFeetAccount.setFont(new Font("Serif", Font.PLAIN, 20));
        backButton = new JButton("Back");
        createAccountPanel.add(enterEmail);
        createAccountPanel.add(enterEmailTextField);
        createAccountPanel.add(enterPassword);
        createAccountPanel.add(enterPasswordTextField);
        createAccountPanel.add(userTypes);
        createAccountPanel.add(sellerOrCustomer);
        createAccountPanel.add(createAccountButton);
        createAccountPanel.add(backButton);
        createAccountPanel.setVisible(true);
        pane.add(createAccountPanel);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccountPanel.setVisible(false);
                setWelcomePageVisible();
                createAccount = false;
            }
        });
    }

    public void setSellerPageVisible() {
        sellerPanel = new JPanel();
        String[] arr = {"Add a Store", "Add a Shoe", "Remove a Shoe", "Edit a Shoe", "View your Sales Information",
        "Change E-Mail", "Change Password", "Import Products from a CSV File", "Export Products to a File"};
        sellerOptions = new JComboBox<>(arr);
        sellerOptionsLabel = new JLabel("Seller Options");
        backButton = new JButton("Back");
        sellerPanel.add(sellerOptionsLabel);
        sellerPanel.add(sellerOptions);
        sellerPanel.add(backButton);
        sellerPanel.setVisible(true);
        pane.add(sellerPanel);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellerPanel.setVisible(false);
                if (login) {
                    setLoginPageVisible();
                } else {
                    setCreateAccountPageVisible();
                }
            }
        });
    }

    public void setCustomerPageVisible() {
        customerPanel = new JPanel();
        String[] arr = {"View Entire Market", "Search Market", "Purchase a Shoe", "Review Purchase History", "Export Purchase as a File",
        "Change E-Mail", "Change Password", "View Market Statistics"};
        customerOptions = new JComboBox<>(arr);
        customerOptionsLabel = new JLabel("Customer Options");
        backButton = new JButton("Back");
        customerPanel.add(customerOptionsLabel);
        customerPanel.add(customerOptions);
        customerPanel.add(backButton);
        customerPanel.setVisible(true);
        pane.add(customerPanel);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerPanel.setVisible(false);
                if (login) {
                    setLoginPageVisible();
                } else {
                    setCreateAccountPageVisible();
                }
            }
        });
    }


    public void run() {
        frame = new JFrame("Happy Feet");
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        // Welcome Panel Layout
        pane = frame.getContentPane();


        // Welcome-Page GUI
        welcomePanel = new JPanel();
        welcomeLoginButton = new JButton("Log in.");
        welcomeLoginButton.addActionListener(actionListener);

        welcomeCreateAccountButton = new JButton("Create An Account.");
        welcomeCreateAccountButton.addActionListener(actionListener);

        welcomeLabel = new JLabel("Welcome to Happy Feet");
        welcomeLabel.setFont(new Font("Serif", Font.PLAIN, 30));

        welcomePanel.add(welcomeLabel);
        welcomePanel.add(welcomeLoginButton);
        welcomePanel.add(welcomeCreateAccountButton);
        welcomePanel.setVisible(true);
        pane.add(welcomePanel);
        // End of Welcome-Page GUI


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new GUI());
    }
}
