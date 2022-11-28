import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginGUI extends JComponent implements Runnable {

    JTextField username;
    JPasswordField password;
    JLabel userLabel;
    JLabel passwordLabel;
    JButton loginButton;

    JLabel login;


    JFrame frame;
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) {
                dispose();
            }

        }
    };

    public void dispose() {
        this.frame.dispose();
    }

    public void sellerGUI() {

    }
    public LoginGUI() {

    }

    public void run() {
        frame = new JFrame("Login Menu");
        Container pane = frame.getContentPane();
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) {
            c.fill = GridBagConstraints.HORIZONTAL;
        }
        login = new JLabel("Login to Happy Feet");
        login.setFont(new Font("Serif", Font.PLAIN, 30));
        username = new JTextField("", 5);
        password = new JPasswordField("",5);
        userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        loginButton = new JButton("Login");
//        loginButton.addActionListener(actionListener);

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        if (shouldWeightX) {
            c.weightx = 0.0;
        }
        JPanel loginPanel = new JPanel();
        loginPanel.add(login);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(loginPanel, c);

        c.insets = new Insets(1,1,1,1);
        JPanel textPanel = new JPanel();
        textPanel.add(userLabel);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(textPanel, c);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        pane.add(username, c);


        textPanel = new JPanel();
        textPanel.add(passwordLabel);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        pane.add(textPanel, c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        pane.add(password, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        pane.add(loginButton, c);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new LoginGUI());
    }
}
