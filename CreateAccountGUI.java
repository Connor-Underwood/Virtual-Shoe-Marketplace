import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CreateAccount extends JComponent implements Runnable {

    JTextField enterEmailTextField;
    JTextField enterPasswordTextField;
    JButton createAccountButton;
    JLabel createHappyFeetAccount;
    JLabel enterEmail;
    JLabel enterPassword;
    JFrame frame;
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == createAccountButton) {
                createAccount();
            }
        }
    };

    public void createAccount() {
        frame.dispose();
    }

    public void run() {
        frame = new JFrame("Welcome to Happy Feet");
        Container pane = frame.getContentPane();
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) {
            c.fill = GridBagConstraints.HORIZONTAL;
        }

        enterEmailTextField = new JTextField();
        enterPasswordTextField = new JTextField();
        createAccountButton = new JButton("Create New Account");
        createAccountButton.addActionListener(actionListener);
        enterEmail = new JLabel("Enter An Email");
        enterPassword = new JLabel("Enter a Password");
        createHappyFeetAccount = new JLabel("Create New Happy Feet Account");
        createHappyFeetAccount.setFont(new Font("Serif", Font.PLAIN, 20));

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        if (shouldWeightX) {
            c.weightx = 0.0;
        }

        c.insets = new Insets(1,1,1,1);
        JPanel loginPanel = new JPanel();
        loginPanel.add(createHappyFeetAccount);
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        pane.add(loginPanel, c);

        JPanel textPanel = new JPanel();
        textPanel.add(enterEmail);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(textPanel, c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        pane.add(enterEmailTextField, c);


        textPanel = new JPanel();
        textPanel.add(enterPassword);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        pane.add(textPanel, c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        pane.add(enterPasswordTextField, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        pane.add(createAccountButton, c);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new CreateAccount());
    }
}

