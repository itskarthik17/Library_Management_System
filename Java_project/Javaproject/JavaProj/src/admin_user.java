import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class admin_user extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel resultLabel;

    private static final String FILE_PATH = "admin_signup.txt";

    public admin_user() {
        setTitle("Login Page");
        setSize(400, 250); // Adjusted size to accommodate the image
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        placeComponents(panel);
        panel.setBackground(Color.WHITE);
        add(panel);

        // Add an image label with resized image
        ImageIcon originalIcon = new ImageIcon("images/admin.jpg");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel imageLabel = new JLabel(resizedIcon);
        imageLabel.setBounds(250, 10, 100, 100);
        panel.add(imageLabel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 120, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 120, 25);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        resultLabel = new JLabel("");
        resultLabel.setBounds(10, 110, 300, 25);
        panel.add(resultLabel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ... (unchanged authentication logic)

                String enteredUsername = usernameField.getText();
                char[] enteredPasswordChars = passwordField.getPassword();
                String enteredPassword = new String(enteredPasswordChars);

                String storedUsername = "Admin7";
                String storedPassword = "777";
                if (enteredUsername.equals(storedUsername) && enteredPassword.equals(storedPassword)) {
                    resultLabel.setText("Login Successful!");
                } else {
                    resultLabel.setText("Login Failed. Please try again.");
                }

                // Clear login fields
                usernameField.setText("");
                passwordField.setText("");
                dispose();

                // Open the User frame
                openUserPage();
            }
        });
    }

    public void openUserPage() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new admin_interface();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new admin_interface();
            }
        });
    }
}