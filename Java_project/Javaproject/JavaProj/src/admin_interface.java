import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class admin_interface extends JFrame {
    public admin_interface() {
        setTitle("Library Management System");
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

        JButton addBookButton = new JButton("Add Book");
        addBookButton.setBounds(30, 40, 120, 25);
        panel.add(addBookButton);

        panel.setBackground(Color.WHITE);

        JButton removeBookButton = new JButton("Remove Book");
        removeBookButton.setBounds(30, 80, 120, 25);
        panel.add(removeBookButton);

        
        JButton addMemberButton = new JButton("Add Member");
        addMemberButton.setBounds(30, 120, 120, 25);
        panel.add(addMemberButton);

        
        JButton removeMemberButton = new JButton("Remove Member");
        removeMemberButton.setBounds(30, 160, 150, 25);
        panel.add(removeMemberButton);

        JButton logoutButton = new JButton("Log Out");
        logoutButton.setBounds(30, 200, 120, 25);
        panel.add(logoutButton);

        JButton queryButton = new JButton("Queries");
        queryButton.setBounds(200, 160, 150, 25);
        panel.add(queryButton);

        

        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                openBookpage();
            }
        });

        /* outofstockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                openstockpage();
            }
        }); */

        removeBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                removeBook();
                //openstockpage();
            }
        });

        addMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                addMember();
            }
        });

         removeMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                removeMember();
            }
        }); 

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                goToTypePage();
            }
        });

      queryButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              dispose();
              query();
          }
      }); 

        
    }

  

    public void openBookpage() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {new book();}
        });


    }

    public void removeBook() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {new removebook();}
        });


    }
    

     /* public void addMember() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                defaulters defaulterPage = new defaulters();
                defaulterPage.setVisible(true);
            }

        });


    }
    
    public void removeMember() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                maxborrowed maxborrowedPage = new maxborrowed();
                maxborrowedPage.setVisible(true);
            }

        });


    } */ 
    
    public void addMember() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {new addmember();}
        });


    }

     public void removeMember() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {new removemember();}
        });


    } 

    public void query() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {new query();}
        });

    }

    public void goToTypePage() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {new type();}
        });


    } 


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new admin_interface();
            }
        });
    }
}
