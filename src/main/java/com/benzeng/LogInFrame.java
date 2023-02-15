package com.benzeng;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.util.ResourceBundle;

public class LogInFrame extends JFrame {
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LogInFrame frame = new LogInFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public LogInFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 250, 800, 600);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
        welcomeLabel.setBounds(323, 50, 273, 80);
        contentPane.add(welcomeLabel);

        userNameField = new JTextField();
        userNameField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        userNameField.setBounds(375, 200, 250, 30);
        contentPane.add(userNameField);
        userNameField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        passwordField.setBounds(375, 250, 250, 30);
        contentPane.add(passwordField);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setBackground(Color.BLACK);
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        usernameLabel.setBounds(200, 200, 150, 30);
        contentPane.add(usernameLabel);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setBackground(Color.CYAN);
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        passwordLabel.setBounds(200, 250, 150, 30);
        contentPane.add(passwordLabel);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
        loginButton.setBounds(300, 392, 162, 73);
        loginButton.addActionListener(e -> {
            String userName = userNameField.getText();
            String password = new String(passwordField.getPassword());
            try {
                ResourceBundle bundle = ResourceBundle.getBundle("mysql");
                Connection connection = (Connection) DriverManager.getConnection(bundle.getString("mysql.url"),
                        bundle.getString("mysql.username"), bundle.getString("mysql.password"));

                PreparedStatement st = (PreparedStatement) connection
                        .prepareStatement("SELECT * FROM Users WHERE UserName = ? AND Password = ?");

                st.setString(1, userName);
                st.setString(2, password);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    dispose();
                    MainActivity mainActivity = new MainActivity();
                    mainActivity.setVisible(true);
                    JOptionPane.showMessageDialog(loginButton, "You have successfully logged in.");
                } else {
                    JOptionPane.showMessageDialog(loginButton, "Invalid user name or password.");
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });

        contentPane.add(loginButton);
    }
}
