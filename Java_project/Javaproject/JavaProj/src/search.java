import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class Book1 {
    String title;
    String author;
    String isbn;
    int available;

    public Book1(String title, String author, String isbn, int available) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = available;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getAvailable() {
        return available;
    }
}

public class search extends JFrame {
    private List<Book1> books;

    public search() {
        books = new ArrayList<>();
        readDataFromFile("books.txt");

        setTitle("Book Search and Borrow");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        JButton searchButton = new JButton("Search Book");
        buttonPanel.add(searchButton);

        JButton redirectTointerfacepage = new JButton("Back");
        buttonPanel.add(redirectTointerfacepage);


        JTextArea resultArea = new JTextArea(20, 40);
        resultArea.setEditable(false);
        mainPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);

        redirectTointerfacepage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                openinterfacePage();
            }
        });

        searchButton.addActionListener(e -> {
            String option = JOptionPane.showInputDialog("Enter option (Book Title / Author / ISBN):");
            if (option != null && !option.isEmpty()) {
                if (option.equalsIgnoreCase("Book Title")) {
                    String bookTitle = JOptionPane.showInputDialog("Enter book title:");
                    searchByTitle(bookTitle, resultArea);
                } else if (option.equalsIgnoreCase("Author")) {
                    String authorName = JOptionPane.showInputDialog("Enter author name:");
                    searchByAuthor(authorName, resultArea);
                } else if (option.equalsIgnoreCase("ISBN")) {
                    String isbnNumber = JOptionPane.showInputDialog("Enter ISBN number:");
                    searchByISBN(isbnNumber, resultArea);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid option!");
                }
            }
        });
    }

    private void readDataFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] bookData = line.split(",");
                if (bookData.length == 4) {
                    String title = bookData[0];
                    String author = bookData[1];
                    String isbn = bookData[2];
                    int available = Integer.parseInt(bookData[3]);
                    books.add(new Book1(title, author, isbn, available));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchByTitle(String bookTitle, JTextArea resultArea) {
        StringBuilder displayText = new StringBuilder();
        for (Book1 book : books) {
            if (book.getTitle().equalsIgnoreCase(bookTitle)) {
                displayText.append("Title: ").append(book.getTitle()).append("\n");
                displayText.append("Author: ").append(book.getAuthor()).append("\n");
                displayText.append("ISBN: ").append(book.getIsbn()).append("\n");
                if (book.getAvailable() > 0) {
                    JButton borrowButton = new JButton("Borrow");
                    borrowButton.addActionListener(e -> borrowBook(book));
                    displayText.append("Availability: Available (").append(book.getAvailable()).append(")\n");
                    displayText.append("Borrow Button: ").append("\n");
                } else {
                    displayText.append("Availability: Not Available\n");
                }
                displayText.append("\n");
            }
        }
        resultArea.setText(displayText.toString());
    }

    private void searchByAuthor(String authorName, JTextArea resultArea) {
        StringBuilder displayText = new StringBuilder();
        for (Book1 book : books) {
            if (book.getAuthor().equalsIgnoreCase(authorName)) {
                displayText.append("Title: ").append(book.getTitle()).append("\n");
                displayText.append("Author: ").append(book.getAuthor()).append("\n");
                displayText.append("ISBN: ").append(book.getIsbn()).append("\n");
                if (book.getAvailable() > 0) {
                    JButton borrowButton = new JButton("Borrow");
                    borrowButton.addActionListener(e -> borrowBook(book));
                    displayText.append("Availability: Available (").append(book.getAvailable()).append(")\n");
                    displayText.append("Borrow Button: ").append("\n");
                } else {
                    displayText.append("Availability: Not Available\n");
                }
                displayText.append("\n");
            }
        }
        resultArea.setText(displayText.toString());
    }

    private void searchByISBN(String isbnNumber, JTextArea resultArea) {
        StringBuilder displayText = new StringBuilder();
        for (Book1 book : books) {
            if (book.getIsbn().equalsIgnoreCase(isbnNumber)) {
                displayText.append("Title: ").append(book.getTitle()).append("\n");
                displayText.append("Author: ").append(book.getAuthor()).append("\n");
                displayText.append("ISBN: ").append(book.getIsbn()).append("\n");
                if (book.getAvailable() > 0) {
                    JButton borrowButton = new JButton("Borrow");
                    borrowButton.addActionListener(e -> borrowBook(book));
                    displayText.append("Availability: Available (").append(book.getAvailable()).append(")\n");
                    displayText.append("Borrow Button: ").append("\n");
                } else {
                    displayText.append("Availability: Not Available\n");
                }
                displayText.append("\n");
            }
        }
        resultArea.setText(displayText.toString());
    }

    public void openinterfacePage() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new user_interface();
            }
        });


    }

    private void borrowBook(Book1 book) {
        LocalDate currentDate = LocalDate.now();
        String borrowedBookDetails = "User," + book.getTitle() + "," + currentDate + "\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user.txt", true))) {
            writer.write(borrowedBookDetails);
            JOptionPane.showMessageDialog(null, "Book borrowed: " + book.getTitle() + "\nBorrowed on: " + currentDate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(search::new);
    }
}