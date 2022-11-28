import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerGUI extends JComponent implements Runnable {
    JButton ViewMarket;
    JButton SearchMarket;
    JButton ReviewPurchaseHistory;
    JButton ExportShoe;
    JButton ChangeEmail;
    JButton ChangePassword;
    JButton PurchaseShoe;
    JButton ViewMarketStatistics;
    JLabel WelcomeCustomer;
    JFrame frame;

    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == ViewMarket) {
                dispose();
                
            }

            if (e.getSource() == SearchMarket) {
                dispose();
                
            }

            if (e.getSource() == ReviewPurchaseHistory) {
                dispose();
            }

            if (e.getSource() == ExportShoe) {
                dispose();
            }

            if (e.getSource() == ChangeEmail) {
                dispose();
            }

            if (e.getSource() == ChangePassword) {
                dispose();
            }

            if (e.getSource() == PurchaseShoe) {
                dispose();
            }
            
            if (e.getSource() == ViewMarketStatistics) {
                dispose();
            }
        }
    };
    public void dispose() {
        this.frame.dispose();
    }

    public void ViewMarket() {
        
    }

    public void SearchMarket() {

    }


    @Override
    public void run() {
        frame = new JFrame("Customer Menu");
        Container pane = frame.getContentPane();
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        if (shouldFill) {
            c.fill = GridBagConstraints.HORIZONTAL;
        }
        WelcomeCustomer = new JLabel("Welcome Customer!");
        WelcomeCustomer.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        ViewMarket = new JButton("1: View Entire Market");
        ViewMarket.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        ViewMarket.addActionListener(actionListener);
        SearchMarket = new JButton("2: Search Market");
        SearchMarket.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        SearchMarket.addActionListener(actionListener);
        ReviewPurchaseHistory = new JButton("3: Review Purchase History");
        ReviewPurchaseHistory.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        ReviewPurchaseHistory.addActionListener(actionListener);
        ExportShoe = new JButton("4: Export Purchase History as a file.");
        ExportShoe.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        ExportShoe.addActionListener(actionListener);
        ChangeEmail = new JButton("5: Change e-mail.");
        ChangeEmail.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        ChangeEmail.addActionListener(actionListener);
        ChangePassword = new JButton("6: Change password");
        ChangePassword.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        ChangePassword.addActionListener(actionListener);
        PurchaseShoe = new JButton("7: Purchase a shoe.");
        PurchaseShoe.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        PurchaseShoe.addActionListener(actionListener);
        ViewMarketStatistics = new JButton("8: View Market Statistics");
        ViewMarketStatistics.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        ViewMarketStatistics.addActionListener(actionListener);

        c.insets = new Insets(5,5,5,5);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(WelcomeCustomer, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(ViewMarket, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        pane.add(SearchMarket,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        pane.add(ReviewPurchaseHistory,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        pane.add(ExportShoe,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        pane.add(ChangeEmail,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;
        pane.add(ChangePassword,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 7;
        pane.add(PurchaseShoe,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 8;
        pane.add(ViewMarketStatistics,c);



        if (shouldWeightX) {
            c.weightx = 0.0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new CutomerGUI());
    }
}
