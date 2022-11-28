import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AddStoreGUI extends JComponent implements Runnable {


    JTextField storeNameField;
    JLabel welcomeMessage;

    JButton addStore;

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

        welcomeMessage = new JLabel("Store Name:");
        welcomeMessage.setFont(new Font("Times new Roman", Font.PLAIN, 18));
        storeNameField = new JTextField(5);
        storeNameField.addActionListener(actionListener);

        c.insets = new Insets(6,6,6,6);

        JPanel storePanel = new JPanel();
        storePanel.add(welcomeMessage);
        storePanel.add(storeNameField);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(storePanel, c);


        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        addStore = new JButton("Add Store");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(addStore, c);


        if (shouldWeightX) {
            c.weightx = 0.0;
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new AddStoreGUI());
    }
}
