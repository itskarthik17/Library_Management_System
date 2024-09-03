import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class BookBorrow extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private JButton borrowButton;
    private JButton backButton;

    public BookBorrow(String username) {
        setTitle("Borrow Book");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Create a table model with columns
        String[] columns = {"Name", "Author", "ISBN", "Version"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Load data from book.txt and display in the table
        loadDataFromFile("books.txt");

        // ActionListener for the borrow button
        borrowButton = new JButton("Borrow Book");
        borrowButton.addActionListener(e -> borrowBook(username));

        // Back button
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            // Go back to the previous page (if it's another JFrame)
            // For example: replace the below comment with the code to show the previous page
            user_interface prevPage = new user_interface();
            prevPage.setVisible(true);
            // Dispose the current window
            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(borrowButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadDataFromFile(String filename) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    Vector<String> row = new Vector<>();
                    for (String datum : data) {
                        row.add(datum.trim());
                    }
                    tableModel.addRow(row);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void borrowBook(String username) {
        int selectedRowIndex = table.getSelectedRow();

        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to borrow.");
            return;
        }

        String bookName = table.getValueAt(selectedRowIndex, 0).toString();
        String author = table.getValueAt(selectedRowIndex, 1).toString();
        String isbn = table.getValueAt(selectedRowIndex, 2).toString();
        String version = table.getValueAt(selectedRowIndex, 3).toString();

        // Get current date
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);

        String borrowedBookInfo = "," + bookName + "," + formattedDate;

        try {
            boolean userFound = false;
            File userFile = new File("user.txt");
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(userFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length > 0 && userData[0].equals(username)) {
                    writer.write(line + borrowedBookInfo);
                    writer.newLine();
                    userFound = true;
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }

            if (!userFound) {
                writer.write(username + borrowedBookInfo);
                writer.newLine();
            }

            writer.close();
            reader.close();

            // Replace the original user file with the updated one
            userFile.delete();
            tempFile.renameTo(userFile);

            JOptionPane.showMessageDialog(this, "Book borrowed successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error borrowing the book. Please try again.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Get the username from the User class
        User user = new User();
        String username = user.get_username();

        SwingUtilities.invokeLater(() -> {
            BookBorrow bookBorrow = new BookBorrow(username);
            bookBorrow.setVisible(true);
        });
    }
}
