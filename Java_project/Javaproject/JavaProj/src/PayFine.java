import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PayFine extends JFrame {
    private JTextField usernameTextField;
    private JLabel fineAmountLabel;
    private JTextArea borrowedBooksArea;
    private JButton payButton, backButton;

    public PayFine() {
        setTitle("Pay Fine");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        usernameTextField = new JTextField(20);
        fineAmountLabel = new JLabel("Fine Amount: $0.0");
        borrowedBooksArea = new JTextArea("Borrowed Books:\n");
        borrowedBooksArea.setEditable(false);

        payButton = new JButton("Pay Fine");
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayFineAndBooks();
            }
        });

        backButton = new JButton("Back to User Interface");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                openUserInterface();
            }
        });

        // Create layout using GroupLayout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(usernameTextField)
                .addComponent(fineAmountLabel)
                .addComponent(borrowedBooksArea)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(payButton)
                        .addComponent(backButton)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(usernameTextField)
                .addComponent(fineAmountLabel)
                .addComponent(borrowedBooksArea)
                .addGroup(layout.createParallelGroup()
                        .addComponent(payButton)
                        .addComponent(backButton)));

        pack();
    }

    private void displayFineAndBooks() {
        String username = usernameTextField.getText().trim();
        if (!username.isEmpty()) {
            double fineAmount = calculateFine(username);
            fineAmountLabel.setText("Fine Amount: $" + fineAmount);

            String borrowedBooks = getBorrowedBooks(username);
            borrowedBooksArea.setText("Borrowed Books:\n" + borrowedBooks);
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a username.");
        }
    }

    private double calculateFine(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            double totalFine = 0.0;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                String name = userData[0].trim();
                String books = userData[1].trim();
                if (name.equals(username)) {
                    int renewalDays = getRenewalDays(username);
                    Date currentDate = new Date();
                    String[] bookData = books.split(",");
                    for (int i = 0; i < bookData.length; i += 2) {
                        String bookName = bookData[i];
                        String borrowedDateString = bookData[i + 1];
                        Date borrowedDate = new SimpleDateFormat("yyyy-MM-dd").parse(borrowedDateString);
                        long diffInMillies = Math.abs(currentDate.getTime() - borrowedDate.getTime());
                        long diffDays = diffInMillies / (24 * 60 * 60 * 1000);
                        if (diffDays > renewalDays) {
                            // Calculate fine (considering a fixed fine rate, you can adjust this as needed)
                            double fineRate = 10.0; // Adjust this rate as needed
                            totalFine += (diffDays - renewalDays) * fineRate;
                        }
                    }
                }
            }
            return totalFine;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return 0; // User not found or other error
    }

    private int getRenewalDays(String username) {
        // In a real application, you would read this information from a file or database.
        // For simplicity, using a fixed renewal period based on user type.
        try (BufferedReader reader = new BufferedReader(new FileReader("members.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                String name = userData[0].trim();
                String label = userData[3].trim();
                if (name.equals(username)) {
                    switch (label) {
                        case "0": // Student
                            return 10;
                        case "1": // Faculty
                            return 50;
                        case "2": // Research Scholar
                            return 25;
                        default:
                            return 0; // Default, no renewal period
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0; // User not found or other error
    }

    private String getBorrowedBooks(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                String name = userData[0].trim();
                String books = userData[1].trim();
                if (name.equals(username)) {
                    return books;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ""; // User not found or other error
    }

    private void openUserInterface() {
        SwingUtilities.invokeLater(() -> new user_interface().setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PayFine().setVisible(true));
    }
}
