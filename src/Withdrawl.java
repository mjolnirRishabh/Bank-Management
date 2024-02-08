import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Date;
import java.sql.*;

public class Withdrawl extends JFrame implements ActionListener{
    JTextField t1;
    JButton b1,b2;
    JLabel l1,l2;
    String pin;
    Withdrawl(String pin){
        this.pin = pin;

        l1 = new JLabel("MAXIMUM WITHDRAWAL IS RS.10,000");
        l1.setForeground(Color.BLACK);
        l1.setFont(new Font("System", Font.BOLD, 16));
        
        l2 = new JLabel("PLEASE ENTER YOUR AMOUNT");
        l2.setForeground(Color.BLACK);
        l2.setFont(new Font("System", Font.BOLD, 16));
        
        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 25));
        
        b1 = new JButton("WITHDRAW");
        b2 = new JButton("BACK");
        
        setLayout(null);
        
        l1.setBounds(100,100,400,20);
        add(l1);
        
        l2.setBounds(100,150,400,20);
        add(l2);
        
        t1.setBounds(100,250,330,30);
        add(t1);
        
        b1.setBounds(290,300,150,35);
        add(b1);
        
        b2.setBounds(300,350,150,35);
        add(b2);
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        
        setSize(600,600);

        setLocation(500,0);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        try{        
            String amount = t1.getText();
            Date date = new Date();
            if(ae.getSource()==b1){
                if(t1.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Please enter the Amount to you want to Withdraw");
                }else{
                    Conn c1 = new Conn();
                    
                    ResultSet rs = c1.s.executeQuery("select * from bank where pin = '"+pin+"'");
                    int balance = 0;
                    while(rs.next()){
                       if(rs.getString("mode").equals("Deposit")){
                           balance += Integer.parseInt(rs.getString("amount"));
                       }else{
                           balance -= Integer.parseInt(rs.getString("amount"));
                       }
                    }
                    if(balance < Integer.parseInt(amount)){
                        JOptionPane.showMessageDialog(null, "Insuffient Balance");
                        return;
                    }
                    PreparedStatement ps ;
                    String str = "insert into bank values(?,?,?,?)" ;
                    ps = c1.c.prepareStatement(str);

                    ps.setString(1,pin);
                    ps.setString(2,date.toString());
                    ps.setString(3,"Withdrawl");
                    ps.setString(4,amount);

                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Rs. "+amount+" Debited Successfully");

                    setVisible(false);
                    new Transactions(pin).setVisible(true);
                }
            }else if(ae.getSource()==b2){
                setVisible(false);
                new Transactions(pin).setVisible(true);
            }
        }catch(Exception e){
                e.printStackTrace();
                System.out.println("error: "+e);
        }
            
    }
    public static void main(String[] args){
        new Withdrawl("").setVisible(true);
    }
}
