import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.PreparedStatement;
import java.util.*;

public class Deposit extends JFrame implements ActionListener{
    JTextField t1;
    JButton b1,b2;
    JLabel l1;
    String pin;

    Deposit(String pin){
        this.pin = pin;

        l1 = new JLabel("ENTER AMOUNT YOU WANT TO DEPOSIT");
        l1.setForeground(Color.black);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(100,100,400,35);

        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 22));
        t1.setBounds(100,150,320,25);

        b1 = new JButton("DEPOSIT");
        b1.setBounds(100,250,150,35);

        b2 = new JButton("BACK");
        b2.setBounds(100,300,150,35);

        add(l1);
        add(t1);
        add(b1);
        add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);
        
        setLayout(null);
        setLocation(500,0);
        setSize(550,500);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        try{        
            String amount = t1.getText();
            Date date = new Date();

            if(ae.getSource()==b1){
                if(t1.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Please enter the Amount to you want to Deposit");
                }else{
                    Conn c1 = new Conn();
                    PreparedStatement ps ;
                    String str = "insert into bank values(?,?,?,?)" ;
                    ps = c1.c.prepareStatement(str);

                    ps.setString(1,pin);
                    ps.setString(2,date.toString());
                    ps.setString(3,"Deposit");
                    ps.setString(4,amount);

                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Rs. "+amount+" Deposited Successfully");

                    setVisible(false);
                    new Transactions(pin).setVisible(true);
                }
            }else if(ae.getSource()==b2){
                setVisible(false);
                new Transactions(pin).setVisible(true);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        new Deposit("").setVisible(true);
    }
}