import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

class Book5 {
    private String name;
    private String author;
    private String isbn;
    private int version;

    public Book5(String name, String author, String isbn, int version) {
        this.name = name;
        this.author = author;
        this.isbn = isbn;
        this.version = version;
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

    public String getVersion() {
        return (String.valueOf(version));
    }
}

public class borrow extends JFrame {
    
    private JTextArea resultArea;

    public borrow() {
        
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        setTitle("User Book Borrow");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        JButton viewBooksButton = new JButton("View Books");
        mainPanel.add(viewBooksButton);

        JButton redirectTointerfacepage = new JButton("Back");
        mainPanel.add(redirectTointerfacepage);

        JButton BorrowBookButton = new JButton("Borrow Book");
        mainPanel.add(BorrowBookButton);

        add(mainPanel);
        setVisible(true);

        viewBooksButton.addActionListener(e -> showAllBooks());

        redirectTointerfacepage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                openinterfacePage();
            }
        });
    }

    /* private void readDataFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] bookDataArray = line.split(",");
                    String[] booksAndAuthor= new String[bookDataArray.length - 1];
                    System.arraycopy(bookDataArray, 1, booksAndAuthor, 0, userDataArray.length - 1);
                    bookData.put(bookDataArray[0], booksAndDates);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } */

    public void openinterfacePage() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new user_interface();
            }
        });


    }

    private void showAllBooks() {
        //String id = JOptionPane.showInputDialog("Enter digital ID:");
        List<Book5> books = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0].trim();
                    String author = parts[1].trim();
                    String isbn = parts[2].trim();
                    int version = Integer.parseInt(parts[3].trim());
                    Book5 book = new Book5(name, author, isbn, version);
                    books.add(book);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        
            
        StringBuilder allBooks = new StringBuilder("All Books:\n\n");
        for (Book5 book : books) {
            allBooks.append(book.getName()+" "+book.getAuthor()+" "+book.getIsbn()).append("\n");
            
        }
        resultArea.setText(allBooks.toString());
    
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(view::new);
    }
}