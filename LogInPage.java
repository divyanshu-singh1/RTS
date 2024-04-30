import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class LogInPage {

    static Connection con;
    static JFrame loginFrame;


    public LogInPage() {

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trial", "root","password");
        } catch (Exception ex) {
            System.out.print(ex);
        }

        loginFrame = new JFrame("LOG IN PAGE");
        loginFrame.setSize(1300, 900);
        // loginFrame.setFont(Style.poppinsFontBold);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.getContentPane().setBackground(Style.BACKGROUND_COLOR);
        loginFrame.setLayout(null);

        JPanel heading = new JPanel();
        heading.setBackground(Style.SECONDARY_COLOR);
        heading.setBounds(0, 0, 1300, 100);
        loginFrame.add(heading);

        JLabel name = new JLabel("Log In Page");
        name.setBounds(130, 20, 400, 50);
        name.setForeground(Style.TEXT_DARK);;
        name.setFont(Style.poppinsFontBold);
        heading.add(name);

        JTextField t1 = new JTextField();
        t1.setBounds(560, 200, 200, 50);
        t1.setFont(Style.poppinsFontPlain);
        loginFrame.add(t1);

        JPasswordField p1 = new JPasswordField();
        p1.setBounds(560, 300, 200, 50);
        p1.setFont(Style.poppinsFontPlain);
        loginFrame.add(p1);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setBounds(400, 450, 150, 50);
        loginButton.setFont(Style.poppinsFontPlain);
        loginButton.setBackground(Style.PRIMARY_COLOR);
        loginButton.setForeground(Style.TEXT_LIGHT);
        loginFrame.add(loginButton);

        JButton signupButton = new JButton("SIGN UP");
        signupButton.setBounds(650, 450, 150, 50);
        signupButton.setFont(Style.poppinsFontPlain);
        signupButton.setBackground(Style.PRIMARY_COLOR);
        signupButton.setForeground(Style.TEXT_LIGHT);
        loginFrame.add(signupButton);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(300, 200, 200, 50);
        usernameLabel.setFont(Style.poppinsFontBold);
        usernameLabel.setForeground(Style.TEXT_DARK);
        loginFrame.add(usernameLabel);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(300, 280, 200, 100);
        passwordLabel.setFont(Style.poppinsFontBold);
        passwordLabel.setForeground(Style.TEXT_DARK);
        loginFrame.add(passwordLabel);

        JCheckBox showPwd = new JCheckBox("Show Password");
        showPwd.setBounds(800, 310, 280, 30);
        showPwd.setFont(Style.poppinsFontPlain);
        showPwd.setBackground(Style.BACKGROUND_COLOR);
        showPwd.setForeground(Style.TEXT_DARK);
        loginFrame.add(showPwd);

        JRadioButton resumeEntryRadio = new JRadioButton("Upload Resume");
        resumeEntryRadio.setBounds(300,600,200,50);
        resumeEntryRadio.setBackground(Style.BACKGROUND_COLOR);
        resumeEntryRadio.setForeground(Style.TEXT_DARK);
        resumeEntryRadio.setFont(Style.poppinsFontPlain);
        loginFrame.add(resumeEntryRadio);
        
        JRadioButton resumeFetchRadio = new JRadioButton("Fetch Resume",true);
        resumeFetchRadio.setBounds(800,600,200,50);
        resumeFetchRadio.setBackground(Style.BACKGROUND_COLOR);
        resumeFetchRadio.setForeground(Style.TEXT_DARK);
        resumeFetchRadio.setFont(Style.poppinsFontPlain);
        loginFrame.add(resumeFetchRadio);

        //creating Button group for for radioButtons to open one of main two pages;
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(resumeEntryRadio);
        buttonGroup.add(resumeFetchRadio);

        // Add ActionListener to showPwd checkbox
        showPwd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPwd.isSelected()) {
                    p1.setEchoChar((char) 0);
                } else {
                    p1.setEchoChar('*');
                }
            }
        });


        //add action listener to login button loginButton
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String user = t1.getText();
                String pwd = p1.getText();
                try {
                   
                    //fetching value of provided username

                    PreparedStatement ps = con.prepareStatement("select * from userdetails where username = ?");
                    ps.setString(1, user);

                    ResultSet rs = ps.executeQuery();
                    String msg = null;

                    boolean popupFlag = true; //flag tocheck whether to show pop up
                    if (rs.next()) {
                        String pass = rs.getString("password");         // fetching value from column 'password'
                        String username = rs.getString("username");     // fetching value from column 'password'  
    
                        if (pwd.equals(pass)){
                            loginFrame.setVisible(false);
                            if(resumeEntryRadio.isSelected()){
                                new ResumeEntry();
                            }else{
                                new ShowResume();
                            }
                            popupFlag = false;
                        }
                        else
                            msg = "Incorrect Password";
                    } else {
                        msg = "User ID does not Exist";
                    }
    
                    if(popupFlag)JOptionPane.showMessageDialog(loginFrame, msg);
                } catch (Exception exc) {
                    System.out.println(exc);
                }
            }
        });


        //add action listener to signup button
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
               loginFrame.setVisible(false);
                new SignUpPage();
            }
        });
        
        loginFrame.setVisible(true);
    }

    public static void main(String[] args) {
       new LogInPage();
    }
}