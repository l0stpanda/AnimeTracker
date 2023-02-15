package com.benzeng;

import javax.swing.*;
import java.awt.*;

public class loginScreen extends JFrame{

    //Definitions of Buttons/Texts/Panels.
    private JButton LOGINButton;
    private JPanel panel1;
    private JTextField username;
    private JPasswordField password;
    private JLabel wc;

    //Other important definitions
    int wrongTries = getWrongTries(3, 7);

    //Attempt Randomization
    public int getWrongTries(int min, int max) {
        int wt = (int) (Math.random() * (max - min + 1) + min);
        return wt;
    }

    //Put your interaction responses here
    public loginScreen(){

        //Set the "you have .... many tries left" label to be invisible.
        wc.setVisible(false);

        //Authorize/Deny credentials.
        LOGINButton.addActionListener(e -> {

            //Comparison variables
            String pwd = new String(password.getPassword());
            String user = new String(username.getText());

            if(!user.equals("ugly_squid") && !pwd.equals("sunfish")){
                MainActivity.main(); //Authorized user/pass

                panel1.getParent().getParent().getParent().setVisible(false); //Closes Login Screen upon new page open
            } else { //Subtract tries allowed
                wrongTries--;
                if(wrongTries == 0){ //If all tries used
                    LOGINButton.setEnabled(false);
                } else { //Tell them how many tries are left
                    wc.setVisible(true);
                    wc.setText("You have " + wrongTries + " attempt(s) left.");
                }
            }

            //sentinel
            if(user.equals("skts > iwaoi") || pwd.equals("skts > iwaoi")){
                wc.setText("you're wrong");
                LOGINButton.setEnabled(false);
            }
        });

    }

    //Frame initializer
    public static void main(String[] args) {
        Color purple = new Color(128, 0, 128);
        JFrame frame = new JFrame("Anime Tracker Login");
        frame.setContentPane(new loginScreen().panel1);
        frame.getContentPane().setBackground(purple);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setBounds(800, 300, 400, 500);
    }
}