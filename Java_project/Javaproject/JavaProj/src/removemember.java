import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class removemember extends JFrame {
    private JTextField ID_field;
    private JLabel resultLabel;

    private static final String FILE_PATH = "members.txt";

    public removemember() {
        setTitle("Member Removal");
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

        JLabel details = new JLabel("Member Details");
        details.setBounds(150, 20, 160, 25);
        panel.add(details);

        JLabel ID = new JLabel("Digital ID:");
        ID.setBounds(10, 140, 80, 25);
        panel.add(ID);

        ID_field = new JTextField(20);
        ID_field.setBounds(100, 140, 165, 25);
        panel.add(ID_field);



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

                String ID =ID_field.getText();
                
                File tempFile = new File("tempFile.txt");
                File inputFile = new File("members.txt");
                try (BufferedReader reader = new BufferedReader(new FileReader("members.txt"))) {
                    
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                    String line;
                    boolean memberremoved = false;

                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        System.out.println();
                        String name=parts[0];
                        String id=parts[1];
                        String phone = parts[2];
                        String student=parts[3];

                        if (ID.equals(id)) {
                            memberremoved = true;
                        }
                        else{
                            writer.write(name+","+id+","+phone+","+student);
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

                    if (memberremoved) {
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
                new removemember();
            }
        });
    }
}
