import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;

public class SellerGUI extends JComponent implements Runnable {

    JButton addStore;
    JButton addShoe;
    JButton removeShoe;
    JButton editShoe;
    JButton viewSales;
    JButton changeEmail;
    JButton changePassword;
    JButton importProducts;
    JButton exportProducts;
    JLabel sellerMenu;



    JFrame frame;
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };

    public void logIn() {
        frame.dispose();
    }

    public void createAccount() {

    }
    public SellerGUI() {

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
        sellerMenu = new JLabel("Welcome Seller!");
        sellerMenu.setFont(new Font("Times New Roman", Font.PLAIN, 30));

        addStore = new JButton("Add a Store.");
        addStore.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        addStore.addActionListener(actionListener);

        addShoe = new JButton("Add a Shoe.");
        addShoe.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        addShoe.addActionListener(actionListener);

        removeShoe = new JButton("Remove a Shoe.");
        removeShoe.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        removeShoe.addActionListener(actionListener);

        editShoe = new JButton("Edit a Shoe.");
        editShoe.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        editShoe.addActionListener(actionListener);

        viewSales = new JButton("View Your Sales Information.");
        viewSales.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        viewSales.addActionListener(actionListener);

        changeEmail = new JButton("Change Your E-mail.");
        changeEmail.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        changeEmail.addActionListener(actionListener);

        changePassword = new JButton("Change Your Password.");
        changePassword.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        changePassword.addActionListener(actionListener);

        importProducts = new JButton("Import Products from an Existing File.");
        importProducts.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        importProducts.addActionListener(actionListener);

        exportProducts = new JButton("Export Products to a New File.");
        exportProducts.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        exportProducts.addActionListener(actionListener);

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        if (shouldWeightX) {
            c.weightx = 0.0;
        }
        JPanel panel = new JPanel();
        panel.add(sellerMenu, BorderLayout.CENTER);
        pane.add(panel);
        c.insets = new Insets(1,1,1,1);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        pane.add(addStore, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        pane.add(addShoe, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        pane.add(removeShoe, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        pane.add(editShoe, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        pane.add(viewSales, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;
        pane.add(changeEmail, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 7;
        pane.add(changePassword, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 8;
        pane.add(importProducts, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 9;
        pane.add(exportProducts, c);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new SellerGUI());
    }
}