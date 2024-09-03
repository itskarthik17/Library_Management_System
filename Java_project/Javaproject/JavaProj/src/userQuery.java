import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Component;


public class userQuery extends JFrame {
  private JButton askQuery;
    private JButton backButton;
   private JTextArea resultArea;

  public void appendResult(String result) {
      resultArea.append(result + "\n");
  }
  
    public userQuery() {

      
        setTitle("Queries");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        


      JPanel panel = new JPanel(new BorderLayout());
        placeComponents(panel);

        add(panel);
        setVisible(true);
    }

    private void placeComponents(JPanel panel) {

      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); 

        /* JLabel bookdetails = new JLabel("Queries");
        bookdetails.setBounds(150, 15, 80, 25);
        panel.add(bookdetails); */

      JLabel bookdetails = new JLabel("Queries");
      bookdetails.setAlignmentX(Component.CENTER_ALIGNMENT); 
      panel.add(bookdetails);

      askQuery = new JButton("Ask A Query");
      askQuery.addActionListener(e -> askQuery());

      backButton = new JButton("Back");
      backButton.addActionListener(e -> {
          user_interface prevPage = new user_interface();
          prevPage.setVisible(true);
          dispose();

      });

      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

      /* JLabel usernameLabel = new JLabel("Username:");
      usernameLabel.setBounds(30, 20, 80, 25);
      panel.add(usernameLabel);

      usernameField = new JTextField();
      usernameField.setBounds(120, 20, 150, 25);
      panel.add(usernameField); */


        buttonPanel.add(askQuery);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);


      JPanel centerPanel = new JPanel(new BorderLayout());

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        loadDataFromFile("query.txt");

        centerPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        panel.add(centerPanel, BorderLayout.CENTER);
    }


 /*  private void placeComponents(JPanel panel) {
      // Use BorderLayout for the main panel
      panel.setLayout(new BorderLayout());

      // Panel for buttons at the bottom using FlowLayout
      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
      askQuery = new JButton("Ask A Query");
      backButton = new JButton("Back");

      backButton.addActionListener(e -> {
          // Your action listener code here
      });

      buttonPanel.add(askQuery);
      buttonPanel.add(backButton);
      panel.add(buttonPanel, BorderLayout.SOUTH);

      // Use BorderLayout for the center part to hold the result area
      JPanel centerPanel = new JPanel(new BorderLayout());

      resultArea = new JTextArea();
      resultArea.setEditable(false);
      loadDataFromFile("query.txt");

      centerPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);
      panel.add(centerPanel, BorderLayout.CENTER);
  } */

  private void loadDataFromFile(String filename) {

    String username=JOptionPane.showInputDialog("Enter username: ");
    StringBuilder sb = new StringBuilder();
      String line;
      try (BufferedReader br = new BufferedReader(new FileReader("query.txt"))) {
        int i=1;
          while ((line = br.readLine()) != null) {
              String[] data = line.split(",");

            String name=data[0];
            String query=data[1];
            String ans=data[2];
              if (name.equals(username)) {
                sb.append(query+" "+i).append(" ->");
                i++;
                if(ans.equals("-")){
                  sb.append("No Answer Yet. We'll get back to you soon...\n");
                }
                else{
                  sb.append(ans).append("\n");
                }
              }
          }
        //System.out.println(sb.toString());
        resultArea.setText(sb.toString());
      } catch (IOException e) {
          e.printStackTrace();
      }
  }


  void askQuery() {

     String username=JOptionPane.showInputDialog("Enter username: ");
    
    String query=JOptionPane.showInputDialog("Enter your query: ");

    try (Writer writer = new BufferedWriter(new FileWriter("query.txt", true))) {
        // Writing data to append to the end of the file
        writer.write(username+","+query+",-\n");

        //writer.newLine();

      appendResult(username+","+query+", No Answer Yet. We'll get back to you soon...\n");

      
    } catch (IOException e) {
        e.printStackTrace();
    }
  

    
  }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new userQuery();
            }
        });
    }
}