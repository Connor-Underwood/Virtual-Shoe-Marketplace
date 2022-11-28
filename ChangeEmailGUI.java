import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChangeEmailGUI extends JComponent implements Runnable {
    JLabel enterEmail;
    JTextField newEmailTextField;
    JButton changeEmailButton;
    JFrame frame;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == changeEmailButton) {
                changeEmail();
            }
        }
    };

    public void changeEmail() {
        String newEmail = newEmailTextField.getText();
        frame.dispose();
    }

    public void run() {
        frame = new JFrame("Happy Feet");
        Container pane = frame.getContentPane();
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        pane.setLayout(new GridBagLayout());

        enterEmail = new JLabel("Enter new email:");
        newEmailTextField = new JTextField();
        changeEmailButton = new JButton("Change Email");
        changeEmailButton.addActionListener(actionListener);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 442);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(6,6,6,6);
        c.gridx = 0;
        c.gridy = 0;
        pane.add(enterEmail, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(newEmailTextField, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        pane.add(changeEmailButton, c);
    }
}
