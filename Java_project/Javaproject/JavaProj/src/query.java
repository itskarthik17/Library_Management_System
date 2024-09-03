import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import java.util.*;

class Query{
  String name;
  String query;
  String ans;


  Query(String name,String query,String ans){
    this.name=name;
    this.query=query;
    this.ans=ans;
  }

  void setAns(String ans){
    this.ans=ans;
  }
  
}

public class query extends JFrame {
    private JLabel resultLabel;


    public query() {
        setTitle("Queries");
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

        JLabel bookdetails = new JLabel("Queries");
        bookdetails.setBounds(150, 15, 80, 25);
        panel.add(bookdetails);

      List<Query> queriesList=new ArrayList<>();

      //File tempFile = new File("tempFile.txt");
      //File inputFile = new File("query.txt");
      try (BufferedReader reader = new BufferedReader(new FileReader("query.txt"))) {
        

        String line;
        while ((line = reader.readLine()) != null) {
          String[] parts = line.split(",");

          System.out.println();

          
          String name=parts[0];
          String query=parts[1];
          String ans = parts[2];

          Query q=new Query(name,query,ans);
          queriesList.add(q);

        }
        reader.close();
      }catch (IOException ex) {
          ex.printStackTrace();
          resultLabel.setText("Error for query. Please try again.");
      }


      int i=40;
      for(Query q:queriesList){

          if(q.ans.equals("-")){
            JButton querybutton=new JButton(q.query);
            querybutton.setBounds(35,i,250,25);
            panel.add(querybutton);

            querybutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    String new_ans=JOptionPane.showInputDialog(q.query+"\n Enter answer:");
                    q.setAns(new_ans);
                    //JOptionPane.showMessageDialog("Query cleared");
                    //dispose();
                  //query();
                    
                }
            });
            i+=25;
          }
          
        }

    

        JButton backbutton=new JButton("Back");
        backbutton.setBounds(200,210,120,25);
        panel.add(backbutton);

        


        


        /* submitbutton.addActionListener(new ActionListener() {
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
        }); */

        backbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                openinterfacePage();
              System.out.println("interface opened\n");
              File tempFile = new File("tempFile.txt");
              File inputFile = new File("query.txt");
              try(BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));){
                for(Query q:queriesList){
                  System.out.println(q.name+","+q.query+","+q.ans+"\n");
                  writer.write(q.name+","+q.query+","+q.ans);
                  writer.newLine();
                }

                writer.close();

                if (!inputFile.delete()) {
                    System.out.println("Could not delete the original file");
                    return;
                }

                if (!tempFile.renameTo(inputFile)) {
                    System.out.println("Could not rename the temporary file");
                }
              }catch (IOException ex) {
                  ex.printStackTrace();
                  resultLabel.setText("Error during finding book. Please try again.");
              }
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


  /* public void queryAnswer()
  {
      SwingUtilities.invokeLater(new Runnable(){
          @Override
          public void run(){new admin_interface();}
      });
  } */
  
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new query();
            }
        });
    }
}
