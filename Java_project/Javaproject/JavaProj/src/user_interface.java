import javax.swing.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class user_interface extends JFrame {
  public user_interface() {
    setTitle("User Profile");
    setSize(400, 350);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel();
    placeComponents(panel);

    add(panel);
    setVisible(true);
  }

  private void placeComponents(JPanel panel) {
    panel.setLayout(null);

    panel.setBackground(Color.WHITE);

    JButton RenewBookButton = new JButton("Renew Book");
    RenewBookButton.setBounds(30, 40, 120, 25);
    panel.add(RenewBookButton);

    /*
     * JButton SearchBookButton = new JButton("Search Book");
     * SearchBookButton.setBounds(30, 80, 120, 25);
     * panel.add(SearchBookButton);
     */
    JButton ReturnBookButton = new JButton("Return Book");
    ReturnBookButton.setBounds(30, 80, 120, 25);
    panel.add(ReturnBookButton);

    /*
     * JButton ViewBook = new JButton("View My Books");
     * ViewBook.setBounds(30, 120, 120, 25);
     * panel.add(ViewBook);
     */

    JButton PayFine = new JButton("Pay Fine");
    PayFine.setBounds(30, 120, 120, 25);
    panel.add(PayFine);

    JButton BorrowBook = new JButton("Borrow Book");
    BorrowBook.setBounds(30, 160, 120, 25);
    panel.add(BorrowBook);

    JButton PaperPublicationButton = new JButton("Paper Publication");
    PaperPublicationButton.setBounds(30, 200, 120, 25);
    panel.add(PaperPublicationButton);

    JButton QueryButton = new JButton("Your Queries");
    QueryButton.setBounds(200, 160, 120, 25);
    panel.add(QueryButton);

    JButton logoutButton = new JButton("Log Out");
    logoutButton.setBounds(30, 240, 120, 25);
    panel.add(logoutButton);

    RenewBookButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
        openRenewpage();
      }
    });

    /*
     * SearchBookButton.addActionListener(new ActionListener() {
     * 
     * @Override
     * public void actionPerformed(ActionEvent e) {
     * dispose();
     * openSearchPage();
     * }
     * });
     */

    // ActionListener for the "Defaulter" button
    /*
     * ViewBook.addActionListener(new ActionListener() {
     * 
     * @Override
     * public void actionPerformed(ActionEvent e) {
     * dispose();
     * openViewpage();
     * }
     * });
     */

    ReturnBookButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
        openReturnpage();

      }
    });

    PaperPublicationButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
        openPaperPublish();

      }
    });

    BorrowBook.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
        openBorrowpage();

      }
    });

    PayFine.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
        openPayfinepage();

      }
    });

    QueryButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
        openQueryPage();

      }
    });

    logoutButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
        goToTypePage();
      }
    });

  }

  /*
   * public void openSearchPage() {
   * SwingUtilities.invokeLater(new Runnable() {
   * 
   * @Override
   * public void run() {new search();}
   * });
   * 
   * 
   * }
   */

  public void openViewpage() {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new view();
      }
    });

  }

  public void openRenewpage() {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new renew();
      }
    });
  }

  public void openBorrowpage() {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new BookBorrow();
      }
    });
  }

  public void openPayfinepage() {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new PayFine();
      }
    });
  }

  public void openPaperPublish() {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new PaperPublicationSystem();
      }
    });
  }

  public void openReturnpage() {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new ReturnBook();
      }
    });
  }

  public void openQueryPage() {
   
   SwingUtilities.invokeLater(new Runnable() {
   
   @Override
   public void run() {new userQuery();}
   });
  }
  
  public void goToTypePage() {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new type();
      }
    });

  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new user_interface();
      }
    });
  }
}
