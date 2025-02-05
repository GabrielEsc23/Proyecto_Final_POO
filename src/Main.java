import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 1000);
        frame.setPreferredSize(new Dimension(900, 1000));
        frame.setVisible(true);
        frame.setContentPane(new login().mainPanel);
        }
    }
