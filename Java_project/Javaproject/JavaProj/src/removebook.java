import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class removebook extends JFrame {
    private JTextField ISBN_field;
    private JLabel resultLabel;

    private static final String FILE_PATH = "books.txt";

    public removebook() {
        setTitle("Book Removal");
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



        JLabel ISBN = new JLabel("ISBN Code:");
        ISBN.setBounds(10, 80, 80, 25);
        panel.add(ISBN);

        ISBN_field = new JTextField(20);
        ISBN_field.setBounds(100, 80, 165, 25);
        panel.add(ISBN_field);



        JButton backbutton=new JButton("Back");
        backbutton.setBounds(200,210,120,25);
        panel.add(backbutton);

        JButton submitbutton = new JButton("Submit");
        submitbutton.setBounds(60, 210, 120, 25);
        panel.add(submitbutton);


        resultLabel = new JLabel("");
        resultLabel.setBounds(10, 280, 300, 25);
        panel.add(resultLabel);


        //make serious changes
        submitbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String ISBN_id =ISBN_field.getText();
                
                File tempFile = new File("tempFile.txt");
                File inputFile = new File("books.txt");
                try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
                    
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                    String line;
                    boolean bookremoved = false;

                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        
                        System.out.println();
                        String title=parts[0];
                        String authors=parts[1];
                        String isbn = parts[2];
                        String version=parts[3];

                        if (ISBN_id.equals(isbn)) {
                            bookremoved = true;
                        }
                        else{
                            writer.write(title+","+authors+","+isbn+","+version);
                            writer.newLine();
                        }
                    }
                    writer.close();
            reader.close();

            if (!inputFile.delete()) {
                System.out.println("Could not delete the original file");
                return;
            }

            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Could not rename the temporary file");
            }

                    if (bookremoved) {
                        resultLabel.setText("Book removed Successfully!");
                    } else {
                        resultLabel.setText("Book not found. Please try again.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    resultLabel.setText("Error during finding book. Please try again.");
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
    /* public void openUserPage() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new admin_interface();
            }
        });


    } */

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
                new removebook();
            }
        });
    }
}
