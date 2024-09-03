import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class view extends JFrame {
    private Map<String, String[]> userData;

    public view() {
        userData = new HashMap<>();
        readDataFromFile("user.txt");

        setTitle("User Book Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        JButton viewBooksButton = new JButton("View Books");
        mainPanel.add(viewBooksButton);

        JButton redirectTointerfacepage = new JButton("Back");
        mainPanel.add(redirectTointerfacepage);

        add(mainPanel);
        setVisible(true);

        viewBooksButton.addActionListener(e -> showUserBooks());

        redirectTointerfacepage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                openinterfacePage();
            }
        });
    }

    private void readDataFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDataArray = line.split(",");
                if (userDataArray.length >= 3) {
                    String[] booksAndDates = new String[userDataArray.length - 1];
                    System.arraycopy(userDataArray, 1, booksAndDates, 0, userDataArray.length - 1);
                    userData.put(userDataArray[0], booksAndDates);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openinterfacePage() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new user_interface();
            }
        });


    }

    private void showUserBooks() {
        String userName = JOptionPane.showInputDialog("Enter username:");

        if (userData.containsKey(userName)) {
            String[] booksAndDates = userData.get(userName);
            StringBuilder displayText = new StringBuilder("Books borrowed by " + userName + ":\n");

            for (int i = 0; i < booksAndDates.length; i += 2) {
                displayText.append(booksAndDates[i]).append(" - Borrowed on ").append(booksAndDates[i + 1]).append("\n");
            }

            JTextArea resultArea = new JTextArea(displayText.toString());
            resultArea.setEditable(false);
            JOptionPane.showMessageDialog(null, new JScrollPane(resultArea));
        } else {
            JOptionPane.showMessageDialog(null, "User not found!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(view::new);
    }
}