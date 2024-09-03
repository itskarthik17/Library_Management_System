import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class book extends JFrame {
    private JTextField BookTitleField;

    private JTextField author_field,ISBN_field,Version_field;
    private JLabel resultLabel;

    private static final String FILE_PATH = "books.txt";

    public book() {
        setTitle("Book Adding");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        placeComponents(panel);

        add(panel);
        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel bookdetails = new JLabel("Book Details");
        bookdetails.setBounds(150, 20, 80, 25);
        panel.add(bookdetails);

        JLabel BookTitle = new JLabel("Title:");
        BookTitle.setBounds(10, 80, 80, 25);
        panel.add(BookTitle);

        BookTitleField = new JTextField(20);
        BookTitleField.setBounds(100, 80, 165, 25);
        panel.add(BookTitleField);

        JLabel author = new JLabel("Author:");
        author.setBounds(10, 110, 80, 25);
        panel.add(author);

        author_field = new JTextField(20);
        author_field.setBounds(100, 110, 165, 25);
        panel.add(author_field);

        JLabel ISBN = new JLabel("ISBN Code:");
        ISBN.setBounds(10, 140, 80, 25);
        panel.add(ISBN);

        ISBN_field = new JTextField(20);
        ISBN_field.setBounds(100, 140, 165, 25);
        panel.add(ISBN_field);

        JLabel version=new JLabel("Version");
        version.setBounds( 10, 170, 80, 25);
        panel.add(version);

        Version_field=new JTextField(20);
        Version_field.setBounds(100,170,165,25);
        panel.add(Version_field);


        JButton backbutton=new JButton("Back");
        backbutton.setBounds(200,210,120,25);
        panel.add(backbutton);

        JButton submitbutton = new JButton("Submit");
        submitbutton.setBounds(60, 210, 120, 25);
        panel.add(submitbutton);


        resultLabel = new JLabel("");
        resultLabel.setBounds(10, 280, 300, 25);
        panel.add(resultLabel);


        submitbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String title = BookTitleField.getText();
                String ISBN_id =ISBN_field.getText();
                String authors =author_field.getText();
                String version=Version_field.getText();

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                    writer.write(title+","+authors+","+ISBN_id+","+version);
                    writer.newLine();
                    resultLabel.setText("Details Stored Succesfully");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    resultLabel.setText("Error during storing. Please try again.");
                }
                dispose();

                openinterfacePage();
            }
        });

        backbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                openinterfacePage();
            }
        });
    }

    public void openinterfacePage()
    {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){new admin_interface();}
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new book();
            }
        });
    }
}
