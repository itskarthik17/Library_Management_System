/* import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Book5 {
    private String name;
    private String author;
    private String isbn;
    private int availability;

    public Book5(String name, String author, String isbn, int availability) {
        this.name = name;
        this.author = author;
        this.isbn = isbn;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getAvailability() {
        return availability;
    }
}

public class outofstock extends JFrame {
    private JTextArea resultArea;
    private JButton viewOutOfStockButton;

    private JButton redirectTointerfacepage;

    private void findOutOfStockBooks() {
        List<Book5> books = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0].trim();
                    String author = parts[1].trim();
                    String isbn = parts[2].trim();
                    int availability = Integer.parseInt(parts[3].trim());
                    Book5 book = new Book5(name, author, isbn, availability);
                    books.add(book);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        StringBuilder outOfStockBooks = new StringBuilder("Out of Stock Books:\n\n");
        for (Book5 book : books) {
            if (book.getAvailability() == 0) {
                outOfStockBooks.append(book.getName()).append("\n");
            }
        }
        resultArea.setText(outOfStockBooks.toString());
    }

    public outofstock() {
        setTitle("Out of Stock Books");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        viewOutOfStockButton = new JButton("View Out of Stock Books");
        redirectTointerfacepage = new JButton("Back");

        viewOutOfStockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findOutOfStockBooks();
            }
        });

        redirectTointerfacepage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                interfacePage();
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel();

        buttonPanel.add(viewOutOfStockButton);
        buttonPanel.add(redirectTointerfacepage);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        getContentPane().add(panel);
    }

    public void interfacePage() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                admin_interface a = new admin_interface();
                a.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new outofstock().setVisible(true);
            }
        });
    }
} */