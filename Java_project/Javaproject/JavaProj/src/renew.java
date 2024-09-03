import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

public class renew extends JFrame {
    private Map<String, String[]> userData;
    private String currentUser;
    private JTextField usernameField; // Added JTextField for username input
    private DefaultTableModel tableModel;
    private JTable booksTable;
    private JButton renewBooksButton;
    private JButton backButton;

    public renew() {
        userData = new java.util.HashMap<>();
        readDataFromFile("user.txt");

        setTitle("Book Renewal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);

        JPanel mainPanel = new JPanel(new BorderLayout());

        createUsernameField();
        createBooksTable();

        JButton fetchBooksButton = new JButton("Fetch User's Books");
        renewBooksButton = new JButton("Renew Selected Books");
        backButton = new JButton("Back");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(usernameField);
        buttonPanel.add(fetchBooksButton);
        buttonPanel.add(renewBooksButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        fetchBooksButton.addActionListener(e -> fetchUserBooks());
        renewBooksButton.addActionListener(e -> renewSelectedBooks());
        backButton.addActionListener(e -> openUserInterface());
    }

    private void createUsernameField() {
        usernameField = new JTextField(20);
    }

    private void createBooksTable() {
        tableModel = new DefaultTableModel(new String[]{"Book", "Borrowed Date", "Renewal Status"}, 0);
        booksTable = new JTable(tableModel);
        booksTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(booksTable);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);
    }

    private void updateBooksTable(String user) {
        tableModel.setRowCount(0);
        String[] booksAndDates = userData.get(user);
        if (booksAndDates != null) {
            for (int i = 0; i < booksAndDates.length; i += 2) {
                String book = booksAndDates[i];
                String date = booksAndDates[i + 1];
                String renewalStatus = getRenewalStatus(user, book);
                tableModel.addRow(new Object[]{book, date, renewalStatus});
            }
        }
    }

    private void fetchUserBooks() {
        currentUser = usernameField.getText().trim();
        if (!currentUser.isEmpty() && userData.containsKey(currentUser)) {
            updateBooksTable(currentUser);
        } else {
            JOptionPane.showMessageDialog(null, "User not found or no books borrowed.");
        }
    }

    private String getRenewalStatus(String user, String book) {
        LocalDate currentDate = LocalDate.now();
        String[] booksAndDates = userData.get(user);

        for (int i = 0; i < booksAndDates.length; i += 2) {
            if (Objects.equals(booksAndDates[i], book)) {
                LocalDate borrowedDate = LocalDate.parse(booksAndDates[i + 1], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                int renewalDays = getRenewalDays(user);

                if (currentDate.isAfter(borrowedDate.plusDays(renewalDays))) {
                    return "Overdue";
                } else {
                    return "OK";
                }
            }
        }

        return ""; // Default, no renewal status
    }

    private void renewSelectedBooks() {
        int[] selectedRows = booksTable.getSelectedRows();
        for (int selectedRow : selectedRows) {
            String book = (String) tableModel.getValueAt(selectedRow, 0);
            LocalDate currentDate = LocalDate.now();
            userData.get(currentUser)[Arrays.asList(userData.get(currentUser)).indexOf(book) + 1] = currentDate.toString();
        }

        try {
            updateFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, "Selected books have been renewed.");
        updateBooksTable(currentUser);
    }

    private void updateFile() throws IOException {
        StringBuilder updatedData = new StringBuilder();
        for (Map.Entry<String, String[]> entry : userData.entrySet()) {
            updatedData.append(entry.getKey()).append(",");
            updatedData.append(String.join(",", entry.getValue())).append("\n");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user.txt"))) {
            writer.write(updatedData.toString());
        }
    }

    private void readDataFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDataArray = line.split(",");
                if (userDataArray.length >= 3) {
                    String[] booksAndDates = Arrays.copyOfRange(userDataArray, 1, userDataArray.length);
                    userData.put(userDataArray[0], booksAndDates);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getRenewalDays(String user) {
        // Implement your logic to get renewal days based on user type (e.g., from members.txt)
        return 0; // Default, no renewal period
    }

    private void openUserInterface() {
        SwingUtilities.invokeLater(() -> new user_interface().setVisible(true));
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(renew::new);
    }
}
