import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ReturnBook extends JFrame {
    private JTextField usernameField;
    private JButton showBooksBtn;
    private JTable booksTable;
    private DefaultTableModel tableModel;
    private JButton returnBtn;
    private JButton backBtn;

    private String filename = "user.txt"; // Name of the file storing user data

    public ReturnBook() {
        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for user input and book display
        JPanel inputPanel = new JPanel(new FlowLayout());

        usernameField = new JTextField(15);
        showBooksBtn = new JButton("Show Borrowed Books");

        // Action listener for showBooksBtn
        showBooksBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showBorrowedBooks();
            }
        });

        inputPanel.add(new JLabel("Enter Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(showBooksBtn);

        // Table to display borrowed books
        String[] columnNames = {"Book Name", "Due Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        booksTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(booksTable);

        // Panel for return and back buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        returnBtn = new JButton("Return");
        backBtn = new JButton("Back");

        // Action listener for returnBtn
        returnBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });

        // Action listener for backBtn
        backBtn.addActionListener(e -> {
                user_interface prevPage = new user_interface();
                prevPage.setVisible(true);
                dispose();
            });

        buttonPanel.add(returnBtn);
        buttonPanel.add(backBtn);

        // Adding components to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }

    private void showBorrowedBooks() {
        String username = usernameField.getText();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a username.");
            return;
        }

        // Clear table before displaying new data
        tableModel.setRowCount(0);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                if (userInfo[0].equals(username)) {
                    for (int i = 1; i < userInfo.length; i += 2) {
                        String bookName = userInfo[i];
                        String dueDate = (i + 1 < userInfo.length) ? userInfo[i + 1] : "";
                        tableModel.addRow(new Object[]{bookName, dueDate});
                    }
                    break;
                }
            }

            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void returnBook() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to return.");
            return;
        }

        String bookName = (String) tableModel.getValueAt(selectedRow, 0);
        String dueDate = (String) tableModel.getValueAt(selectedRow, 1);
        String username = usernameField.getText();

        try {
            File inputFile = new File(filename);
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean found = false;

            while ((currentLine = reader.readLine()) != null) {
                String[] userInfo = currentLine.split(",");
                if (userInfo[0].equals(username)) {
                    found = true;
                    StringBuilder updatedInfo = new StringBuilder(userInfo[0]);

                    for (int i = 1; i < userInfo.length; i += 2) {
                        if (!(userInfo[i].equals(bookName) && userInfo[i + 1].equals(dueDate))) {
                            updatedInfo.append(",").append(userInfo[i]).append(",").append(userInfo[i + 1]);
                        }
                    }
                    writer.write(updatedInfo.toString() + "\n");
                } else {
                    writer.write(currentLine + "\n");
                }
            }

            reader.close();
            writer.close();

            if (!found) {
                JOptionPane.showMessageDialog(this, "User not found.");
            } else {
                if (!tempFile.renameTo(inputFile)) {
                    System.out.println("Error updating file.");
                }

                JOptionPane.showMessageDialog(this, "Book '" + bookName + "' with due date '" + dueDate + "' returned successfully.");

                // Remove the row from the table after returning the book
                tableModel.removeRow(selectedRow);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
                ReturnBook returnbook = new ReturnBook();
                returnbook.setVisible(true);
            });
    }
}
