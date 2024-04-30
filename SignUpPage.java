import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.* ;  

public class SignUpPage {

    static Connection con;

    public SignUpPage(){

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trial","root","password");
        }
        catch(Exception ex){
            System.out.print(ex);
        }

        JFrame f = new JFrame("Sign Up Page");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1300,900);
        f.setLocationRelativeTo(null);
        f.getContentPane().setBackground(Style.BACKGROUND_COLOR);
        f.setLayout(null);

        JPanel heading = new JPanel();
        heading.setBackground(Style.SECONDARY_COLOR);
        heading.setBounds(0,0,1300,100);
        f.add(heading);

        JLabel name = new JLabel("Sign Up Page");
        name.setForeground(Style.TEXT_DARK);
        name.setBounds(120,50,400,50);
        name.setFont(Style.poppinsFontBold);
        heading.add(name);

        JLabel usernameLabel = new JLabel("UserName :");
        usernameLabel.setBounds(350,300,300,50);
        usernameLabel.setFont(Style.poppinsFontBold);
        usernameLabel.setForeground(Style.TEXT_DARK);
        f.add(usernameLabel);

        JTextField usernameEntryField = new JTextField(20);
        usernameEntryField.setBounds(550,300,250,50); 
        usernameEntryField.setFont(Style.poppinsFontPlain);
        usernameEntryField.setForeground(Style.TEXT_DARK);
        f.add(usernameEntryField);
        
        JLabel passwordLabel = new JLabel("Password :");
        passwordLabel.setBounds(350,400 , 250, 50);
        passwordLabel.setFont(Style.poppinsFontBold);
        passwordLabel.setForeground(Style.TEXT_DARK);
        f.add(passwordLabel); 
        
        JPasswordField passwordEntryField = new JPasswordField(20);
        passwordEntryField.setBounds(550, 400, 250, 50);
        passwordEntryField.setFont(Style.poppinsFontPlain);
        passwordEntryField.setForeground(Style.TEXT_DARK);
        f.add(passwordEntryField); 

        JCheckBox showPwd = new JCheckBox("Show Password");
        showPwd.setBounds(850, 400, 280, 30);
        showPwd.setBackground(Style.BACKGROUND_COLOR);
        showPwd.setForeground(Style.TEXT_DARK);
        showPwd.setFont(Style.poppinsFontPlain);
        f.add(showPwd);

        
        JButton signupButton = new JButton("Sign Up");
        signupButton.setBounds(400, 520, 150,40);
        signupButton.setBackground(Style.PRIMARY_COLOR);
        signupButton.setForeground(Style.TEXT_LIGHT);
        signupButton.setFont(Style.poppinsFontPlain);
        f.add(signupButton);

        JButton back  = new JButton("Back");
        back.setBounds(660,520,150,40);
        back.setBackground(Style.PRIMARY_COLOR);
        back.setForeground(Style.TEXT_LIGHT);
        back.setFont(Style.poppinsFontPlain);
        f.add(back);


        
        // Add ActionListener to showPwd checkbox
        showPwd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPwd.isSelected()) {
                    passwordEntryField.setEchoChar((char) 0);
                } else {
                    passwordEntryField.setEchoChar('*');
                }
            }
        });

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) { 
                //String user = t1.getText();
                String pwd = passwordEntryField.getText();
                String uname = usernameEntryField.getText();
                if(pwd.length() < 5) {
                    JOptionPane.showMessageDialog(f, "Atleast 5 characters of password required");
                    return ;
                }
                else if (pwd.contains(" ")) {
                    JOptionPane.showMessageDialog(f, "Password should not contain any spaces");
                    return ;
                }
                try {
                    PreparedStatement check = con.prepareStatement("select * from userDetails where username=?");
                    check.setString(1,uname);
                    ResultSet checkrs = check.executeQuery();
                    if(checkrs.next()){
                        JOptionPane.showMessageDialog(f,"This Username already exist.Please choose another one");
                    }else{
                        PreparedStatement pst = con.prepareStatement("INSERT INTO userDetails (username,password) VALUES (?,?)");
                        pst.setString(1,uname);
                        pst.setString(2,pwd);

                        int result=pst.executeUpdate();
                        if(result>0){
                            JOptionPane.showMessageDialog(f,"User Successfully Registered");
                        }
                    }
                    
                } catch(Exception exc) {
                    System.out.print(exc);
                    
                }
            }
        });

       

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                f.setVisible(false);
                new LogInPage();
            }
        });



        f.setVisible(true);
    }  
    public static void main(String []args){
        new SignUpPage();
    }
}