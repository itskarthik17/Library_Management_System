import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class type extends JFrame {
    public type() {
        setTitle("Home Page");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        // Set background color for the panel
        panel.setBackground(Color.WHITE);

        placeComponents(panel);

        // Set background color for the content pane of the JFrame
        getContentPane().setBackground(Color.LIGHT_GRAY);

        add(panel);
        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel lms = new JLabel("Library Management System");
        lms.setBounds(150, 5, 400, 25);

        // Create a Font object with the desired size
        Font labelFontLMS = lms.getFont();
        lms.setFont(new Font(labelFontLMS.getName(), Font.PLAIN, 20)); 


        panel.add(lms);

        JLabel details = new JLabel("Home Page");
        details.setBounds(230, 40, 160, 25);

        // Create a Font object with the desired size
        Font labelFont = details.getFont();
        details.setFont(new Font(labelFont.getName(), Font.PLAIN, 20)); 


        panel.add(details);

        // Load and resize images for buttons
        ImageIcon adminIcon = new ImageIcon(resizeImage(new ImageIcon("images/admin.jpg").getImage(), 200, 190));
        ImageIcon userIcon = new ImageIcon(resizeImage(new ImageIcon("images/user.jpg").getImage(), 200, 190));
        
        JButton Admin = new JButton(adminIcon);
        Admin.setBounds(30, 75, 200, 190);
        panel.add(Admin);

        JButton User = new JButton(userIcon);
        User.setBounds(300, 75, 200, 190);
        panel.add(User);

        JLabel adminLabel = new JLabel("Admin");
        adminLabel.setBounds(100, 265, 100, 20);
        panel.add(adminLabel);

        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(400, 265, 100, 20);
        panel.add(userLabel);

        Admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                openAdminUser();
            }
        });

        User.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                openUser();
            }
        });
    }

    private Image resizeImage(Image image, int width, int height) {
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    public void openUser() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new User();
            }
        });
    }

    public void openAdminUser() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new admin_user();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new type();
            }
        });
    }
}