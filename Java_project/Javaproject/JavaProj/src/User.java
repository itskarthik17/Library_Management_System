import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class User extends JFrame {
    private JTextField usernameField;
    private JTextField idField;
    private JLabel resultLabel;
    private static final String FILE_PATH = "members.txt";
    String enteredid, enteredUsername;

    public User() {
        setTitle("Login Page");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        placeComponents(panel);
        panel.setBackground(Color.WHITE);
        add(panel);

        // Add an image label with resized image
        ImageIcon originalIcon = new ImageIcon("images/user.jpg"); // Replace with the actual path
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel imageLabel = new JLabel(resizedIcon);
        imageLabel.setBounds(280, 10, 100, 100); // Adjust the bounds as needed
        panel.add(imageLabel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 165, 25);
        panel.add(usernameField);

        JLabel idLabel = new JLabel("Digital ID:");
        idLabel.setBounds(10, 50, 80, 25);
        panel.add(idLabel);

        idField = new JTextField(20);
        idField.setBounds(100, 50, 165, 25);
        panel.add(idField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        resultLabel = new JLabel("");
        resultLabel.setBounds(10, 110, 300, 25);
        panel.add(resultLabel);

        // ActionListener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check the login credentials (you would replace this with your authentication logic)
                enteredUsername = usernameField.getText();
                enteredid = idField.getText();

                try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                    String line;
                    boolean loginSuccessful = false;

                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        String storedUsername = parts[0];
                        String storedid = parts[1];

                        if (enteredUsername.equals(storedUsername) && enteredid.equals(storedid)) {
                          //created public variable to use across user_interface fns
                          //public String username=enteredUsername;
                            loginSuccessful = true;
                            break;
                        }
                    }

                    if (loginSuccessful) {
                        resultLabel.setText("Login Successful!");
                        dispose();
                        openUserPage(enteredid);

                    } else {
                        resultLabel.setText("Login Failed. Please try again.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    resultLabel.setText("Error during login. Please try again.");
                }

                // For simplicity, let's just clear the login fields
                usernameField.setText("");
                idField.setText("");
            }
        });
    }

    public String get_username() {
        return enteredUsername;
    }

    public String get_id() {
        return enteredid;
    }

    public void openUserPage(String enteredid) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new user_interface();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new User();
            }
        });
    }
}