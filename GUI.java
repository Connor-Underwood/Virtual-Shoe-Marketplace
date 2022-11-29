import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import javax.swing.*;

public class GUI extends JComponent implements Runnable  {


    // General Frame
    JFrame frame;
    Container pane;

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

    JLabel userTypePrompt;
    JTextField userType;
    //

    boolean login = false;
    boolean createAccount = false;


    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           if (e.getSource() == welcomeLoginButton) {
              welcomePageNonVisible();
              setLoginPageVisible();
           }
           if (e.getSource() == welcomeCreateAccountButton) {
               welcomePageNonVisible();
               setCreateAccountPageVisible();
               createAccount = true;
           }
           if (e.getSource() == loginButton) {
               // check if info correct
//               System.out.println(username.getText());
//               System.out.println(String.valueOf(password.getPassword()));
               login = true;
           }
           if (e.getSource() == createAccountButton) {
               // check if info correct
           }

        }
    };
    public void dispose() {
        this.frame.dispose();
    }

    public void welcomePageNonVisible() {
        welcomePanel.setVisible(false);
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
        loginPanel = new JPanel();
        loginPanel.add(userLabel);
        loginPanel.add(username);
        loginPanel.add(passwordLabel);
        loginPanel.add(password);
        loginPanel.add(loginButton);
        loginPanel.setVisible(true);
        pane.add(loginPanel);
    }

    public void setCreateAccountPageVisible() {
        createAccountPanel = new JPanel();
        enterEmailTextField = new JTextField(5);
        enterPasswordTextField = new JPasswordField(5);
        createAccountButton = new JButton("Create New Account");
        createAccountButton.addActionListener(actionListener);
        enterEmail = new JLabel("Enter An Email");
        enterPassword = new JLabel("Enter a Password");
        userTypePrompt = new JLabel("1: Seller\n2: Customer");
        userType = new JTextField(5);
        createHappyFeetAccount = new JLabel("Create New Happy Feet Account");
        createHappyFeetAccount.setFont(new Font("Serif", Font.PLAIN, 20));
        createAccountPanel.add(enterEmail);
        createAccountPanel.add(enterEmailTextField);
        createAccountPanel.add(enterPassword);
        createAccountPanel.add(enterPasswordTextField);
        createAccountPanel.add(userTypePrompt);
        createAccountPanel.add(userType);
        createAccountPanel.add(createAccountButton);
        createAccountPanel.setVisible(true);
        pane.add(createAccountPanel);
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