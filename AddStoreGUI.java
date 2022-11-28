import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AddStoreGUI extends JComponent implements Runnable {


    JTextField storeNameField;
    JLabel welcomeMessage;

    JFrame frame;
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == storeNameField) {
                dispose();
                buyMenu();
            }

        }
    };
    public void dispose() {
        this.frame.dispose();
    }
    public String buyMenu() {
        String value = storeNameField.getText();
        return value;
    }


    public AddStoreGUI() {

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

        welcomeMessage = new JLabel("What is the name of the store you would like to add?");
        welcomeMessage.setFont(new Font("Times new Roman", Font.PLAIN, 30));
        storeNameField = new JTextField();
        storeNameField.addActionListener(actionListener);

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        if (shouldWeightX) {
            c.weightx = 0.0;
        }
        c.insets = new Insets(6,6,6,6);
        JPanel textPanel = new JPanel();
        textPanel.add(welcomeMessage);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(textPanel, c);

        JPanel buttonPanel = new JPanel();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(storeNameField,c);


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new AddStoreGUI());
    }
}
