import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BuyerOrSellerChoiceGUI extends JComponent implements Runnable {

    JButton BuyerButton;
    JButton SellerButton;
    JLabel welcomeMessage;

    JFrame frame;
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == BuyerButton) {
                dispose();
                buyMenu();
            }

            if (e.getSource() == SellerButton) {
                sellerMenu();
            }
        }
    };
    public void dispose() {
        this.frame.dispose();
    }
    public Integer buyMenu() {
        return 2;
    }

    public int sellerMenu() {
        return 1;
    }
    public BuyerOrSellerChoiceGUI() {

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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeMessage = new JLabel("What are you creating this account for?");
        welcomeMessage.setFont(new Font("Times new Roman", Font.PLAIN, 30));
        BuyerButton = new JButton("To buy");
        BuyerButton.addActionListener(actionListener);
        SellerButton = new JButton("To sell");
        SellerButton.addActionListener(actionListener);

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
        pane.add(BuyerButton,c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = 2;
        pane.add(SellerButton,c);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new BuyerOrSellerChoiceGUI());
    }
}
