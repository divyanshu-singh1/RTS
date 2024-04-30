import javax.swing.*;
import java.awt.*;
import java.io.*;
public class Style {

     // Define colors
     public static final Color TEXT_DARK = Color.decode("#0b070b");
     public static final Color TEXT_LIGHT = Color.decode("#FFFFFF");
     public static final Color BACKGROUND_COLOR = Color.decode("#4f7167");
     public static final Color PRIMARY_COLOR = Color.decode("#0B5394");
     public static final Color SECONDARY_COLOR = Color.decode("#b1c8a7");
     public static final Color ACCENT_COLOR = Color.decode("#85b29f");
     
 
     // Define fonts
     public static  Font poppinsFontPlain, poppinsFontBold , poppinsFontBoldInBuilt;
     static{
        try{
            poppinsFontPlain = Font.createFont(Font.TRUETYPE_FONT, new File("D:\\PoppinsFont\\Poppins-Regular.ttf")).deriveFont(Font.PLAIN,20);

            poppinsFontBold = Font.createFont(Font.TRUETYPE_FONT, new File("D:\\PoppinsFont\\Poppins-Regular.ttf")).deriveFont(Font.BOLD,30);

            poppinsFontBoldInBuilt = Font.createFont(Font.TRUETYPE_FONT, new File("D:\\PoppinsFont\\Poppins-Bold.ttf")).deriveFont(Font.BOLD,20);

        }catch(Exception e){
            e.printStackTrace();
        }
     }

    private Style(){

    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("LOG IN PAGE");
        frame.setSize(1300, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BACKGROUND_COLOR);
        frame.setLayout(null);

        // JButton name = new JButton("Noral bg and default font");
        // name.setForeground(Color.WHITE);
        // // name.setBackground(ACCENT_COLOR);
        // name.setBounds(120, 50, 400, 50);
        // name.setFont(poppinsFont);
        // frame.add(name);

        // JButton name2 = new JButton("bg color and heading font");
        // // name2.setForeground(TEXT_COLOR);
        // name2.setBackground(BACKGROUND_COLOR);
        // name2.setBounds(120, 150, 400, 50);
        // name2.setFont(poppinsFont);
        // frame.add(name2);

        // JButton name3 = new JButton("ACCENTcolor and button font");
        // name3.setForeground(TEXT_COLOR);
        // name3.setBackground(ACCENT_COLOR);
        // name3.setBounds(120, 250, 400, 50);
        // // name3.setFont(BUTTON_FONT);
        // frame.add(name3);

        // JButton name4 = new JButton("One with Poppins font");
        // name4.setForeground(TEXT_COLOR);
        // name4.setBackground(PRIMARY_COLOR);
        // name4.setBounds(120, 250, 400, 50);
        // // name4.setFont(BUTTON_FONT);
        // frame.add(name4);

        frame.setVisible(true);
    }
}
