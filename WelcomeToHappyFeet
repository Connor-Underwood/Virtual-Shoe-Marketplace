import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WelcomeToHappyFeet extends JComponent implements Runnable {

    JButton logInButton;
    JButton createAccountButton;
    JLabel welcomeMessage;

    JFrame frame;
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == logInButton) {
                logIn();
            }

            if (e.getSource() == createAccountButton) {
                createAccount();
            }
        }
    };

    public void logIn() {
        frame.dispose();
    }

    public void createAccount() {

    }
    public WelcomeToHappyFeet() {

    }

    public void run() {
       frame = new JFrame("Happy Feet");
       Container pane = frame.getContentPane();
       pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
       pane.setLayout(new GridBagLayout());
       GridBagConstraints c = new GridBagConstraints();
       if (shouldFill) {
           c.fill = GridBagConstraints.HORIZONTAL;
       }

       welcomeMessage = new JLabel("Welcome to Happy Feet");
       logInButton = new JButton("Log In");
       logInButton.addActionListener(actionListener);
       createAccountButton = new JButton("Create An Account");
       createAccountButton.addActionListener(actionListener);

       frame.setSize(600, 400);
       frame.setLocationRelativeTo(null);
       frame.setVisible(true);

       if (shouldWeightX) {
           c.weightx = 0.0;
       }
       JPanel textPanel = new JPanel();
       textPanel.add(welcomeMessage);
       c.fill = GridBagConstraints.HORIZONTAL;
       c.gridx = 0;
       c.gridy = 0;
       pane.add(textPanel, c);

       JPanel buttonPanel = new JPanel();
       buttonPanel.add(logInButton);
       buttonPanel.add(createAccountButton);
       c.fill = GridBagConstraints.HORIZONTAL;
       c.weightx = 0.0;
       c.gridx = 0;
       c.gridy = 1;
       pane.add(buttonPanel,c);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new WelcomeToHappyFeet());
    }
}
