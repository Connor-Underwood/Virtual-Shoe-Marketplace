import javax.swing.*;
import java.awt.*;

public class RemoveShoeGUI extends JComponent implements Runnable {
    JLabel StoreName;
    JTextField StoreNameField;

    JLabel ShoeName;
    JTextField ShoeNameField;

    JLabel ShoeDescription;
    JTextField ShoeDescriptionField;

    JLabel ShoePrice;
    JTextField ShoePriceField;

    JLabel ShoeQuantity;
    JTextField ShoeQuantityField;

    JButton removeShoe;



    JFrame frame;
    @Override
    public void run() {
        frame = new JFrame("Remove Shoe");
        Container pane = frame.getContentPane();
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        ShoeName = new JLabel("Shoe Name:");
        ShoeName.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        ShoeNameField = new JTextField("", 5);

        ShoeDescription = new JLabel("Shoe Description:");
        ShoeDescription.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        ShoeDescriptionField = new JTextField("", 5);

        ShoePrice = new JLabel("Shoe Price:");
        ShoePrice.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        ShoePriceField = new JTextField("", 5);

        ShoeQuantity = new JLabel("Shoe Quantity:");
        ShoeQuantity.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        ShoeQuantityField = new JTextField("", 5);

        StoreName = new JLabel("Store Name:");
        StoreName.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        ShoeNameField = new JTextField("", 5);

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        c.insets = new Insets(3,3,3,3);

        JPanel namePanel = new JPanel();
        namePanel.add(ShoeName);
        namePanel.add(ShoeNameField);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(namePanel, c);

        JPanel descPanel = new JPanel();
        descPanel.add(ShoeDescription);
        descPanel.add(ShoeDescriptionField);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(descPanel, c);

        JPanel pricePanel = new JPanel();
        pricePanel.add(ShoePrice);
        pricePanel.add(ShoePriceField);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        pane.add(pricePanel, c);


        JPanel quantityPanel = new JPanel();
        quantityPanel.add(ShoeQuantity);
        quantityPanel.add(ShoeQuantityField);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        pane.add(quantityPanel, c);

        removeShoe = new JButton("Remove Shoe");
        removeShoe.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        pane.add(removeShoe, c);


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new RemoveShoeGUI());
    }
}
