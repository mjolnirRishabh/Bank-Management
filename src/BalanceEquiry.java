import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import javax.swing.*;

class BalanceEnquiry extends JFrame  {
    JButton b1;
    JLabel l1;
    String pin;

    BalanceEnquiry(String pin) {
        this.pin = pin;

        l1 = new JLabel();
        l1.setForeground(Color.black);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(100, 50, 400, 35);

        b1 = new JButton("BACK");
        b1.setBounds(100, 100, 150, 35);

        add(l1);
        add(b1);
        int balance = 0;

        try{
            Conn c1 = new Conn();
            ResultSet rs = c1.s.executeQuery("select * from bank where pin = '"+pin+"'");
            while (rs.next()) {
                if (rs.getString("mode").equals("Deposit")) {
                    balance += Integer.parseInt(rs.getString("amount"));
                } else {
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            }
        }catch(Exception e){}
        
        l1.setText("Your Current Account Balance is Rs "+balance);

       b1.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               setVisible(false);
               new Transactions(pin).setVisible(true);
           }
       });

        setLayout(null);
        setSize(500, 400);
        setLocation(500, 0);
        setVisible(true);
    }

    public static void main(String[] args) {
        new BalanceEnquiry("").setVisible(true);
    }
}