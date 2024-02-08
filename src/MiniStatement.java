import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class MiniStatement extends JFrame{
    JButton b1;
    JLabel l1;

    MiniStatement(String pin){
        super("Mini Statement");
        getContentPane().setBackground(Color.WHITE);

        l1 = new JLabel();
        l1.setBounds(10, 5, 300, 400);

        JLabel l2 = new JLabel("Indian Bank");
        l2.setBounds(150, 0, 100, 20);

        JLabel l3 = new JLabel();
        l3.setBounds(20, 80, 300, 20);

        JLabel l4 = new JLabel();
        l4.setBounds(20, 400, 300, 20);

        b1 = new JButton("Exit");
        b1.setBounds(20, 500, 100, 25);

        add(l1);
        add(l2);
        add(l3);
        add(l4);
        add(b1);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        setLocation(500,0);
        setSize(400,600);
        setLocation(20,20);
        setLayout(null);

        try{
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from login where pin = '"+pin+"'");

            if(rs.next()){
                l3.setText("Card Number:    " + rs.getString("cardno").substring(0, 4) + "XXXXXXXX" + rs.getString("cardno").substring(12));
            }

            int balance = 0;
            Conn c1  = new Conn();
            rs = c1.s.executeQuery("SELECT * FROM bank where pin = '"+pin+"'");
            while(rs.next()){
                l1.setText(l1.getText() + "<html>"+rs.getString("date")+ " " + rs.getString("mode") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString("amount") + "<br><br><html>");

                if(rs.getString("mode").equals("Deposit")){
                    balance += Integer.parseInt(rs.getString("amount"));
                }else{
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            }
            l4.setText("Your total Balance is Rs "+balance);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new MiniStatement("").setVisible(true);
    }
}