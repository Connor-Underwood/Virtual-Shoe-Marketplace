import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddShoeGUI extends JComponent implements Runnable {
    JTextField shoeName;
    JLabel shoeNameLabel;
    JTextField shoeDescription;
    JLabel shoeDescriptionLabel;
    JTextField shoePrice;
    JLabel shoePriceLabel;
    JTextField shoeQuantity;
    JLabel shoeQuantityLabel;
    JTextField storeName;
    JLabel storeNameLabel;
    JButton addShoe;

    JFrame frame;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addShoe) {
                // check params like get text
            }

        }

    };

    public void dispose() {
        this.frame.dispose();
    }

    public void run() {
        frame = new JFrame("Happy Feet");
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Container pane = frame.getContentPane();
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JPanel panel1 = new JPanel();
        shoeName = new JTextField(5);
        shoeNameLabel = new JLabel("Shoe Name:");
        shoeNameLabel.setFont(new Font("Times new Roman", Font.PLAIN, 12));
        panel1.add(shoeNameLabel);
        panel1.add(shoeName);
        c.gridx = 0;
        c.gridy = 0;
        pane.add(panel1,c);

        JPanel panel2 = new JPanel();
        shoeDescription = new JTextField(5);
        shoeDescriptionLabel = new JLabel("Shoe Description:");
        shoeDescriptionLabel.setFont(new Font("Times new Roman", Font.PLAIN, 12));
        panel2.add(shoeDescriptionLabel);
        panel2.add(shoeDescription);
        c.gridx = 0;
        c.gridy = 1;
        pane.add(panel2,c);

        JPanel panel3 = new JPanel();
        shoePrice = new JTextField(5);
        shoePriceLabel = new JLabel("Shoe Price:");
        shoePriceLabel.setFont(new Font("Times new Roman", Font.PLAIN, 12));
        panel3.add(shoePriceLabel);
        panel3.add(shoePrice);
        c.gridx = 0;
        c.gridy = 2;
        pane.add(panel3,c);

        JPanel panel4 = new JPanel();
        shoeQuantity = new JTextField(5);
        shoeQuantityLabel = new JLabel("Shoe Quantity:");
        shoeQuantityLabel.setFont(new Font("Times new Roman", Font.PLAIN, 12));
        panel4.add(shoeQuantityLabel);
        panel4.add(shoeQuantity);
        c.gridx = 0;
        c.gridy = 3;
        pane.add(panel4,c);

        JPanel panel5 = new JPanel();
        storeName = new JTextField(5);
        storeNameLabel = new JLabel("Store Name:");
        storeNameLabel.setFont(new Font("Times new Roman", Font.PLAIN, 12));
        panel5.add(storeNameLabel);
        panel5.add(storeName);
        c.gridx = 0;
        c.gridy = 4;
        pane.add(panel5,c);

        c.gridx = 0;
        c.gridy = 5;
        addShoe = new JButton("Add Shoe.");
        addShoe.addActionListener(actionListener);
        addShoe.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        pane.add(addShoe, c);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new AddShoeGUI());
    }
}
