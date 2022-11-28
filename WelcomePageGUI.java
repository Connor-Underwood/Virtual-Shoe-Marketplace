import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WelcomeToHappyFeet extends JComponent implements Runnable {

    public JButton logInButton;
    public JButton createAccountButton;
    public JLabel welcomeMessage;
    public final static boolean shouldFill = true;
    public final static boolean shouldWeightX = true;
    public JFrame frame;
    public Image image;
    public final static String filePath = "image.jpg";

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
        CreateAccount createAccount = new CreateAccount();
        createAccount.run();
        frame.dispose();
    }

    public void run() {
        try {
            image = Toolkit.getDefaultToolkit().getImage(filePath);
            frame = new JFrame("Happy Feet");
            frame.setContentPane(new BackGroundPanel(image));
            Container pane = frame.getContentPane();
            pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            pane.setLayout(new GridBagLayout());

            welcomeMessage = new JLabel("Welcome to Happy Feet");
            welcomeMessage.setFont(new Font("Serif", Font.PLAIN, 30));
            logInButton = new JButton("Log In");
            logInButton.addActionListener(actionListener);
            createAccountButton = new JButton("Create An Account");
            createAccountButton.addActionListener(actionListener);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(600, 442);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            GridBagConstraints c = new GridBagConstraints();
            if (shouldFill) {
                c.fill = GridBagConstraints.HORIZONTAL;
            }

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

            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.0;
            c.gridx = 0;
            c.gridy = 1;
            pane.add(logInButton,c);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.0;
            c.gridx = 0;
            c.gridy = 2;
            pane.add(createAccountButton,c);
        } catch (Exception io) {
            io.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new WelcomeToHappyFeet());
    }
}

class BackGroundPanel extends JPanel {
    private final Image image;
    public BackGroundPanel(Image image) {
        this.image = image;
    }

    public Dimension getPreferredSize() {
        Dimension superSize = super.getPreferredSize();
        if (isPreferredSizeSet() || image == null) {
            return superSize;
        }

        int prefW = Math.max(image.getWidth(null), superSize.width);
        int prefH = Math.max(image.getHeight(null), superSize.height);
        return new Dimension(prefW, prefH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}
