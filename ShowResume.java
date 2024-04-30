import java.io.*;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import java.awt.event.*;
public class ShowResume  {
    static Connection con;
    static String result;

    public ShowResume(){
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trial", "root", "password");
        }catch(Exception ae){
            System.out.println(ae);
        }

        

        JFrame showFrame = new JFrame("Show Resume");
        showFrame.setSize(1300,900);
        showFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showFrame.setLocationRelativeTo(null);

        JPanel showPanel  = new JPanel();
        showPanel.setLayout(null);
        showPanel.setBackground(Style.BACKGROUND_COLOR);
        showFrame.add(showPanel);

        JLabel specialization = new JLabel("Specialization : ");        // Creating Label  Specialization tag 
        specialization.setFont(Style.poppinsFontBold);
        specialization.setForeground(Style.TEXT_DARK);
        specialization.setBounds(20,50,250,40);
        showPanel.add(specialization);

        //Creating Combo Box for Specialization selection 
        String boxList[] = {"Web Development","Data Science","Machine Learning","Cyber Security","Cloud Computing","Internet of Things"};
        JComboBox<String> specialBox = new JComboBox<>(boxList);
        specialBox.setBounds(280,50,200,50);
        specialBox.setFont(Style.poppinsFontPlain);
        specialBox.setForeground(Style.TEXT_DARK);
        showPanel.add(specialBox);
        
        JLabel experience = new JLabel("Experience : ");    // Creating Label for experience
        experience.setFont(Style.poppinsFontBold);
        experience.setForeground(Style.TEXT_DARK);
        experience.setBounds(20,150,250,40);
        showPanel.add(experience);

        // Text field for experience
        JTextField expField = new JTextField();  
        expField.setFont(Style.poppinsFontPlain);
        expField.setForeground(Style.TEXT_DARK);
        expField.setBounds(280,150,200,50);
        showPanel.add(expField);

        //Button to Show all applicants with selected specialization and experience
        JButton showButton = new JButton("Show");
        showButton.setFont(Style.poppinsFontPlain);
        showButton.setForeground(Style.TEXT_LIGHT);
        showButton.setBackground(Style.PRIMARY_COLOR);
        showButton.setBounds(50,250,150,50);
        showPanel.add(showButton);

        //back button for going back to Login page
        JButton back = new JButton("Back");
        back.setFont(Style.poppinsFontPlain);
        back.setForeground(Style.TEXT_LIGHT);
        back.setBackground(Style.PRIMARY_COLOR);
        back.setBounds(275,250,150,50);
        showPanel.add(back);

        //Creating Table ,table model and seting columns name
        JTable displayTable = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel();
        String[] colName = {"Id","Name","Experience in Months"};
        tableModel.setColumnIdentifiers(colName);
        displayTable.setModel(tableModel);
        
        //Adding table in JScrollPane
        JScrollPane scrollPane = new JScrollPane(displayTable);
        scrollPane.setBounds(525,45,725,300);
        scrollPane.setForeground(Style.TEXT_DARK);
        scrollPane.setFont(Style.poppinsFontPlain);
        showPanel.add(scrollPane);

        //Button to show all existing applicants in DB
        JButton showAllButton = new JButton("Show All");
        showAllButton.setFont(Style.poppinsFontPlain);
        showAllButton.setForeground(Style.TEXT_LIGHT);
        showAllButton.setBackground(Style.PRIMARY_COLOR);
        showAllButton.setBounds(950,400,200,50);
        showPanel.add(showAllButton);
        
        //Button to clear table 
        JButton clearTableButton = new JButton("Clear Table");
        clearTableButton.setFont(Style.poppinsFontPlain);
        clearTableButton.setForeground(Style.TEXT_LIGHT);
        clearTableButton.setBackground(Style.PRIMARY_COLOR);
        clearTableButton.setBounds(600,400,200,50);
        showPanel.add(clearTableButton);

        //Button to display selected applicant's resume
        JButton showResumeButton = new JButton("Show Resume");
        showResumeButton.setFont(Style.poppinsFontPlain);
        showResumeButton.setForeground(Style.TEXT_LIGHT);
        showResumeButton.setBackground(Style.PRIMARY_COLOR);
        showResumeButton.setBounds(500,700 , 250, 50);
        showPanel.add(showResumeButton);


        
        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                String spe = (String)specialBox.getSelectedItem();
                int exp = 0;

                //belove code is used to force user enter experience as int only
                boolean flag = true;
                while(flag){
                    try{
                        exp = Integer.parseInt(expField.getText());
                        flag = false;
                    }catch(NumberFormatException ne){
                        JOptionPane.showMessageDialog(showFrame,"Enter experience in Months \n (integer only)");
                        expField.setText("");
                        return;
                    }
                }
                
                try{
                    tableModel.setRowCount(0);
                    PreparedStatement ps = con.prepareStatement("select  applicantId ,name,exp  from applicantDetails where specialization=? AND exp >= ?");
                    ps.setString(1,spe);
                    ps.setInt(2,exp);

                    ResultSet rs = ps.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    
                    int colNum = rsmd.getColumnCount();

                    while(rs.next()){
                        String row[] = {rs.getString(1),rs.getString(2),rs.getString(3)};
                        tableModel.addRow(row);
                    }
                }catch(Exception e){
                    System.out.println(e); 
                }
            }
        });

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                showFrame.setVisible(false);
                new LogInPage();
            }
        });

        showAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){

                try{
                    PreparedStatement ps = con.prepareStatement("select applicantId,name,exp from applicantDetails");
                    ResultSet rs = ps.executeQuery();

                    while(rs.next()){
                        String row[] = {rs.getString(1),rs.getString(2),rs.getString(3)};
                        tableModel.addRow(row);
                    }
                }catch(Exception e){
                    JOptionPane.showMessageDialog(showPanel,e);
                }
            }
        });

        clearTableButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent ae){
                tableModel.setRowCount(0);
            } 
        });

        showResumeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){

                int n = displayTable.getSelectedRow();
                int uid =0;

                //Fetching applicantId(primary key in db table)
                if(n == -1){
                   JOptionPane.showMessageDialog(showPanel,"No Applicant Selected");
                   return;
                }else {
                    try{
                        uid = Integer.parseInt((String)displayTable.getValueAt(n, 0));
                    }catch(NumberFormatException nfe){
                        JOptionPane.showMessageDialog(showPanel,"An Errort Occured");
                        return;
                    }
                }

                try{

                    PreparedStatement ps = con.prepareStatement("select path from applicantDetails where applicantId=?");
                    ps.setInt(1,uid);

                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    String path = rs.getString(1);
                    
                    
                    File file = new File(path);
                    if(file.isFile()){
                        if(!Desktop.isDesktopSupported()){
                            System.out.println("Desktop access to open file is not supported");
                        }else{
                            Desktop desktop = Desktop.getDesktop();
                            desktop.open(file);
                        }
                    }else{
                        JOptionPane.showMessageDialog(showPanel,"Error: File not found");
                    }
                    
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        });
        showFrame.setVisible(true);
    }

    public static void main(String [] args){
        new ShowResume();
    }
}
