import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaperPublicationSystem extends JFrame {
  private DefaultTableModel tableModel;
  private JTextField titleTextField, authorTextField;
  private JButton backButton;

  public PaperPublicationSystem() {
    setTitle("Paper Publication System");
    setSize(500, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    // Create a table model with columns: Title, Author, Publication Date
    tableModel = new DefaultTableModel();
    tableModel.addColumn("Title");
    tableModel.addColumn("Author");
    tableModel.addColumn("Publication Date");

    JTable table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);

    // Create form components
    JLabel titleLabel = new JLabel("Title:");
    JLabel authorLabel = new JLabel("Author:");

    titleTextField = new JTextField(20);
    authorTextField = new JTextField(20);

    JButton publishButton = new JButton("Publish Paper");
    publishButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        publishPaper();
      }
    });

    backButton = new JButton("Back to User Interface");
    backButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
        openUserInterface();
      }
    });

    // Load existing papers from file on application start
    loadPapersFromFile();

    // Create layout using GroupLayout
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);

    layout.setHorizontalGroup(layout.createParallelGroup()
        .addComponent(scrollPane)
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup()
                .addComponent(titleLabel)
                .addComponent(authorLabel))
            .addGroup(layout.createParallelGroup()
                .addComponent(titleTextField)
                .addComponent(authorTextField)
                .addComponent(publishButton)
                .addComponent(backButton))));

    layout.setVerticalGroup(layout.createSequentialGroup()
        .addComponent(scrollPane)
        .addGroup(layout.createParallelGroup()
            .addComponent(titleLabel)
            .addComponent(titleTextField))
        .addGroup(layout.createParallelGroup()
            .addComponent(authorLabel)
            .addComponent(authorTextField))
        .addComponent(publishButton)
        .addComponent(backButton));

    pack();
    setLocationRelativeTo(null); // Center the frame on the screen
  }

  private void publishPaper() {
    String title = titleTextField.getText();
    String author = authorTextField.getText();
    String publicationDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    // Check if the author is a research scholar
    if (isResearchScholar(author)) {
      // Add the published paper to the table
      tableModel.addRow(new Object[] { title, author, publicationDate });
      // Save the published papers to the file
      savePapersToFile();
      // Clear the input fields
      titleTextField.setText("");
      authorTextField.setText("");
    } else {
      JOptionPane.showMessageDialog(this, "Only Research Scholars are allowed to publish papers.");
    }
  }

  private boolean isResearchScholar(String authorName) {
    try (BufferedReader reader = new BufferedReader(new FileReader("members.txt"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] userData = line.split(",");
        String name = userData[0].trim();
        String id = userData[1].trim();
        String mobile = userData[2].trim();
        String label = userData[3].trim();

        // Check if the author's name matches and the user is a research scholar (label
        // 2)
        if (name.equals(authorName) && label.equals("2")) {
          return true;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return false; // Author is not found or is not a research scholar
  }

  private void savePapersToFile() {
    try (PrintWriter writer = new PrintWriter(new FileWriter("papers.txt"))) {
      for (int row = 0; row < tableModel.getRowCount(); row++) {
        writer.println(String.join(",", tableModel.getValueAt(row, 0).toString(),
            tableModel.getValueAt(row, 1).toString(),
            tableModel.getValueAt(row, 2).toString()));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadPapersFromFile() {
    try (BufferedReader reader = new BufferedReader(new FileReader("papers.txt"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");
        tableModel.addRow(data);
      }
    } catch (IOException e) {
      // File may not exist or other IO issues, ignore for this example
    }
  }

  private void openUserInterface() {
    SwingUtilities.invokeLater(() -> new user_interface().setVisible(true));
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new PaperPublicationSystem().setVisible(true));
  }
}
