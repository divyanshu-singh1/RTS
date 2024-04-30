import java.io.*;
import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.*;

class ResumeEntry {

    static Connection con;
    static String absoluteFilePath = "";

    public ResumeEntry() {

        JFrame entryFrame = new JFrame("Resume Upload");
        entryFrame.setSize(1300, 900);
        entryFrame.setLocationRelativeTo(null);
        entryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel entryPanel = new JPanel();
        entryPanel.setLayout(null);
        entryPanel.setBackground(Style.BACKGROUND_COLOR);
        entryFrame.add(entryPanel);

        JLabel name = new JLabel("Name : "); // Creating Label for name
        name.setFont(Style.poppinsFontBold);
        name.setBounds(300, 100, 300, 75);
        name.setForeground(Style.TEXT_DARK);
        entryPanel.add(name);

        JTextField tf1 = new JTextField(10); // Text field for name as tf1
        tf1.setFont(Style.poppinsFontPlain);
        tf1.setBounds(600, 100, 350, 55);
        entryPanel.add(tf1);

        JLabel exp = new JLabel("Experience : "); // Creating Label for experience
        exp.setFont(Style.poppinsFontBold);
        exp.setBounds(300, 200, 300, 75);
        exp.setForeground(Style.TEXT_DARK);
        entryPanel.add(exp);

        JTextField tf3 = new JTextField(); // Text field for e as tf3
        tf3.setFont(Style.poppinsFontPlain);
        tf3.setBounds(600, 200, 350, 55);
        entryPanel.add(tf3);

        JLabel special = new JLabel("Specialization : "); // creating label for Specialization
        special.setFont(Style.poppinsFontBold);
        special.setBounds(300, 300, 300, 75);
        special.setForeground(Style.TEXT_DARK);
        entryPanel.add(special);

        String boxList[] = { "Web Development", "Data Science", "Machine Learning", "Cyber Security", "Cloud Computing",
                "Internet of Things" };
        JComboBox<String> specialBox = new JComboBox<>(boxList);
        specialBox.setBounds(600, 300, 350, 55);
        specialBox.setFont(Style.poppinsFontPlain);
        specialBox.setForeground(Style.TEXT_DARK);
        entryPanel.add(specialBox);

        JButton filePath = new JButton("Upload Resume"); // Creating Label for file path
        filePath.setFont(Style.poppinsFontBold);
        filePath.setBounds(350, 400, 300, 60);
        filePath.setFont(Style.poppinsFontPlain);
        filePath.setBackground(Style.ACCENT_COLOR);
        filePath.setForeground(Style.TEXT_DARK);
        entryPanel.add(filePath);

        

        filePath.addActionListener(e -> {
            try {
                JFileChooser fileChooser = new JFileChooser();

                String extensions[] = new String[]{"pdf","docx","txt"};
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Resume File", extensions);
                fileChooser.setFileFilter(filter);
                fileChooser.setCurrentDirectory(new File("."));

                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    System.out.println("File Path : " + selectedFile);


                    /*
                     * extracting file extension
                     */
                    String fileName = selectedFile.getName();
                    String fileExtension = "";
                    int i = fileName.lastIndexOf('.');
                    if(i > 0){
                        fileExtension = fileName.substring(i+1);
                    }

                    // System.out.println("The file extension is " + fileExtension);
                    /*
                     * Checking if it is one of the allowed file type
                     */
                    HashSet<String> set = new HashSet<>(Arrays.asList(extensions));
                    if(set.contains(fileExtension)){    
                        absoluteFilePath = selectedFile.getAbsolutePath();
                    }else{
                        JOptionPane.showMessageDialog(entryPanel,"Please only select these types : " + Arrays.toString(extensions));
                    }


                    // if (!Desktop.isDesktopSupported()) {
                    //     System.out.println("Not supported");
                    // } else {
                    //     Desktop desktop = Desktop.getDesktop();
                    //     desktop.open(selectedFile);
                    // }
                }else if(result == JFileChooser.CANCEL_OPTION){
                    JOptionPane.showMessageDialog(entryPanel,"File Selection Terminated");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        JButton submit = new JButton("Submit");
        submit.setFont(Style.poppinsFontPlain);
        submit.setBackground(Style.PRIMARY_COLOR);
        submit.setForeground(Style.TEXT_LIGHT);
        submit.setBounds(350, 600, 200, 50);
        entryPanel.add(submit);

        JButton back = new JButton("Back");
        back.setFont(Style.poppinsFontPlain);
        back.setBackground(Style.PRIMARY_COLOR);
        back.setForeground(Style.TEXT_LIGHT);
        back.setBounds(650, 600, 200, 50);
        entryPanel.add(back);

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trial", "root", "password");
        } catch (Exception e) {
            System.out.println(e);
        }

        submit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae){

                String uname = tf1.getText(); //taking of valuee of name and specialization
                String sp = (String)specialBox.getSelectedItem();

                int uexp;
                try{
                    uexp = Integer.parseInt(tf3.getText()); // taking value of Experience form textfield tf3 and parsing it.
                }catch(NumberFormatException nfe){
                    System.out.println("Pleae provid experience in interger values as months");
                    uexp = 0;
                }

                try{
                    //Making sure that all values are not empty or null
                    if(!(uname.equals("") || absoluteFilePath.equals("") || sp.equals(""))){

                        File file = new File(absoluteFilePath);
                        if(file.isFile()){ // to check if path is valid

                            //SQL query to enter all values in database trial table applicantDetails

                            PreparedStatement ps = con.prepareStatement("insert into applicantDetails(name,path,exp,specialization) values(?,?,?,?)");
                            ps.setString(1,uname);
                            ps.setString(2,absoluteFilePath);
                            ps.setInt(3,uexp);
                            ps.setString(4,sp);

                            int ret_val = ps.executeUpdate();

                            if(ret_val == 1){
                                JOptionPane.showMessageDialog(entryPanel,"Resume Uploaded Succesfully");
                                //clearing all text field values for the next entry
                                tf1.setText("");tf3.setText("");
                                absoluteFilePath = "";
                            }
                        }else{
                            JOptionPane.showMessageDialog(entryPanel,"Invlaid File Path");
                        }
                    }else {
                        JOptionPane.showMessageDialog(entryPanel,"Provide all Values");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                entryFrame.setVisible(false);
                new LogInPage();
            }

        });
        entryFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new ResumeEntry();
    }
}
