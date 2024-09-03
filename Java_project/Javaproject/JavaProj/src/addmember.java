import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class addmember extends JFrame {
    private JTextField signUpUsernameField,studentField;

    private JTextField phone_field,id_field;
    private JLabel resultLabel;

    private static final String FILE_PATH = "members.txt";

    public addmember() {
        setTitle("Member Adding");
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

        JLabel signUpUserLabel = new JLabel("Username: ");
        signUpUserLabel.setBounds(10, 70, 80, 25);
        panel.add(signUpUserLabel);

        signUpUsernameField = new JTextField(20);
        signUpUsernameField.setBounds(150, 70, 165, 25);
        panel.add(signUpUsernameField);

        JLabel phone = new JLabel("Phone no.: ");
        phone.setBounds(10, 100, 80, 25);
        panel.add(phone);

        phone_field = new JTextField(20);
        phone_field.setBounds(150, 100, 165, 25);
        panel.add(phone_field);

        JLabel id = new JLabel("Digital id: ");
        id.setBounds(10, 130, 80, 25);
        panel.add(id);

        id_field = new JTextField(20);
        id_field.setBounds(150, 130, 165, 25);
        panel.add(id_field);

        JLabel studentLabel0 = new JLabel("0-Faculty");
        studentLabel0.setBounds(10, 160, 150, 25);
        panel.add(studentLabel0);

      JLabel studentLabel1 = new JLabel("1-Student");
      studentLabel1.setBounds(10, 185, 150, 25);
      panel.add(studentLabel1);

      JLabel studentLabel2 = new JLabel("2-Research Scholar ");
      studentLabel2.setBounds(10, 210, 150, 25);
      panel.add(studentLabel2);

        studentField = new JTextField(20);
        studentField.setBounds(150, 160, 165, 25);
        panel.add(studentField);


        JButton backbutton=new JButton("Back");
        backbutton.setBounds(200,240,120,25);
        panel.add(backbutton);

        JButton submitbutton = new JButton("Submit");
        submitbutton.setBounds(60, 240, 120, 25);
        panel.add(submitbutton);


        resultLabel = new JLabel("");
        resultLabel.setBounds(10, 280, 300, 25);
        panel.add(resultLabel);


        submitbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String signUpUsername = signUpUsernameField.getText();
                String id =id_field.getText();
                String phone =phone_field.getText();
                String student=studentField.getText();

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                    writer.write(signUpUsername + "," +id+","+phone+","+student);
                    writer.newLine();
                    resultLabel.setText("Member added successful!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    resultLabel.setText("Error during adding member. Please try again.");
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
                new addmember();
            }
        });
    }
}
