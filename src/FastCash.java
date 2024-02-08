import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {
    JLabel l1, l2;
    JButton b1, b2, b3, b4, b5, b6, b7;
    String pin;

    FastCash(String pin) {
        this.pin = pin;

        l1 = new JLabel("Select Withdrawal Amount");
        l1.setForeground(Color.BLACK);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(235, 50, 700, 35);

        b1 = new JButton("Rs 100");
        b1.setBounds(170, 100, 150, 35);

        b2 = new JButton("Rs 500");
        b2.setBounds(390, 100, 150, 35);

        b3 = new JButton("Rs 1000");
        b3.setBounds(170, 150, 150, 35);

        b4 = new JButton("Rs 2000");
        b4.setBounds(390, 150, 150, 35);

        b5 = new JButton("Rs 5000");
        b5.setBounds(170, 200, 150, 35);

        b6 = new JButton("Rs 10000");
        b6.setBounds(390, 200, 150, 35);

        b7 = new JButton("BACK");
        b7.setBounds(200, 300, 150, 35);

        add(l1);
        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(b5);
        add(b6);
        add(b7);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);

        setLayout(null);
        setSize(700, 500);
        setLocation(500, 0);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String amount = ((JButton)ae.getSource()).getText().substring(3);
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from bank where pin = '"+pin+"'");
            int balance = 0;
            while (rs.next()) {
                if (rs.getString("mode").equals("Deposit")) {
                    balance += Integer.parseInt(rs.getString("amount"));
                } else {
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            } String num = "17";
            if (ae.getSource() != b7 && balance < Integer.parseInt(amount)) {
                JOptionPane.showMessageDialog(null, "Insuffient Balance");
                return;
            }

            if (ae.getSource() == b7) {
                this.setVisible(false);
                new Transactions(pin).setVisible(true);
            }else{
                Date date = new Date();

                PreparedStatement ps ;
                String str = "insert into bank values(?,?,?,?)" ;
                ps = c.c.prepareStatement(str);

                ps.setString(1,pin);
                ps.setString(2,date.toString());
                ps.setString(3,"Withdrawl");
                ps.setString(4,amount);

                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Rs. "+amount+" Debited Successfully");
                    
                setVisible(false);
                new Transactions(pin).setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new FastCash("").setVisible(true);
    }
}