import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class addSell
{
    public addSell()
    {
        JFrame jf=new JFrame();
        JPanel jp=new JPanel();
        JButton jb1=new JButton("确定"),jb2=new JButton("返回");
        JTextField jtf1=new JTextField(10),jtf2=new JTextField(10),jtf3=new JTextField(10);
        JTextField jtf4=new JTextField(10),jtf5=new JTextField(10),jtf6=new JTextField(10),jtfSize=new JTextField(10);
        JRadioButton jrb1=new JRadioButton("出租"),jrb2=new JRadioButton("出售");
        jrb1.setSelected(true);
        ButtonGroup bg=new ButtonGroup();
        JComboBox jcb=new JComboBox();
        jcb.addItem("正常");
        jcb.addItem("已成交");
        jcb.addItem("作废");
        bg.add(jrb1);bg.add(jrb2);
        jp.setLayout(new GridLayout(10,2));
        jp.add(new JLabel("编号"));
        jp.add(jtf1);
        jp.add(new JLabel("联系人"));
        jp.add(jtf2);
        jp.add(new JLabel("电话"));
        jp.add(jtf3);
        jp.add(new JLabel("地址"));
        jp.add(jtf4);
        jp.add(new JLabel("大小"));
        jp.add(jtfSize);
        jp.add(jrb1);
        jp.add(jrb2);
        jp.add(new JLabel("图片"));
        jp.add(jtf5);
        jp.add(new JLabel("价格"));
        jp.add(jtf6);
        jp.add(new JLabel("状态"));
        jp.add(jcb);
        jp.add(jb1);
        jp.add(jb2);
        jf.add(jp);
        jf.pack();
        jf.setVisible(true);

        jb1.addActionListener((e)->
        {
            db dbcon=new db();
            String sql="insert into sell values(?,?,?,?,?,?,?,?,?)";
            try
            {
                PreparedStatement pre=dbcon.PreparedStatement(sql);
                pre.setString(1,jtf1.getText());
                pre.setString(2,jtf2.getText());
                pre.setString(3,jtf3.getText());
                pre.setString(4,jtf4.getText());
                pre.setString(5,jtfSize.getText());
                pre.setString(6,(jrb1.isSelected())?"1":"2");
                pre.setString(7,jtf5.getText());
                pre.setString(8,jtf6.getText());
                pre.setString(9,jcb.getSelectedItem().toString());
                pre.executeUpdate();
                new gui(1);
                jf.dispose();
            }
            catch (SQLException sqle)
            {
                System.out.println(sqle);
            }
        });

        jb2.addActionListener((e)->
        {
            new gui(1);
            jf.dispose();
        });

    }
}

class addBuy
{
    public addBuy()
    {
        JFrame jf=new JFrame();
        JPanel jp=new JPanel();
        JButton jb1=new JButton("确定"),jb2=new JButton("返回");
        JTextField jtf1=new JTextField(10),jtf2=new JTextField(10),jtf3=new JTextField(10);
        JTextField jtf4=new JTextField(10);
        JTextField jtfAddress=new JTextField(10),jtfSize=new JTextField(10);
        JRadioButton jrb1=new JRadioButton("出租"),jrb2=new JRadioButton("出售");
        jrb1.setSelected(true);
        ButtonGroup bg=new ButtonGroup();
        JComboBox jcb=new JComboBox();
        jcb.addItem("正常");
        jcb.addItem("已成交");
        jcb.addItem("作废");
        bg.add(jrb1);bg.add(jrb2);
        jp.setLayout(new GridLayout(9,2));
        jp.add(new JLabel("编号"));
        jp.add(jtf1);
        jp.add(new JLabel("联系人"));
        jp.add(jtf2);
        jp.add(new JLabel("电话"));
        jp.add(jtf3);
        jp.add(new JLabel("地址"));
        jp.add(jtfAddress);
        jp.add(new JLabel("大小"));
        jp.add(jtfSize);
        jp.add(jrb1);
        jp.add(jrb2);
        jp.add(new JLabel("价格"));
        jp.add(jtf4);
        jf.add(jp);
        jf.pack();
        jf.setVisible(true);

        jb1.addActionListener((e)->
        {
            db dbcon=new db();
            String sql="insert into sell values(?,?,?,?,?,?,?)";
            try
            {
                PreparedStatement pre=dbcon.PreparedStatement(sql);
                pre.setString(1,jtf1.getText());
                pre.setString(2,jtf2.getText());
                pre.setString(3,jtf3.getText());
                pre.setString(4,jtfAddress.getText());
                pre.setString(5,jtfSize.getText());
                pre.setString(6,(jrb1.isSelected())?"1":"2");
                pre.setString(7,jtf4.getText());
                pre.executeUpdate();
                new gui(2);
                jf.dispose();
            }
            catch (SQLException sqle)
            {
                System.out.println(sqle);
            }
        });

        jb2.addActionListener((e)->
        {
            new gui(2);
            jf.dispose();
        });

    }
    public static void main(String args[])
    {
        new addSell();
    }
}

class addDeal
{
    public addDeal()
    {
        JFrame jf=new JFrame();
        JPanel jp=new JPanel();
        JButton jb1=new JButton("确定"),jb2=new JButton("返回");
        JTextField jtf1=new JTextField(10),jtf2=new JTextField(10),jtfno=new JTextField(10);
        jp.setLayout(new GridLayout(4,2));
        jp.add(new JLabel("编号"));
        jp.add(jtfno);
        jp.add(new JLabel("房源编号"));
        jp.add(jtf1);
        jp.add(new JLabel("求购编号"));
        jp.add(jtf2);
        jp.add(jb1);
        jp.add(jb2);
        jf.add(jp);
        jf.pack();
        jf.setVisible(true);

        jb1.addActionListener((e)->
        {
            db dbcon=new db();
            String sql="insert into auth values(?,?,?)";
            try
            {
                PreparedStatement pre=dbcon.PreparedStatement(sql);
                pre.setString(1,jtfno.getText());
                pre.setString(2,jtf1.getText());
                pre.setString(3,jtf2.getText());
                pre.executeUpdate();
                new gui(3);
                jf.dispose();
            }
            catch (SQLException sqle)
            {
                System.out.println(sqle);
            }
        });

        jb2.addActionListener((e)->
        {
            new gui(3);
            jf.dispose();
        });

    }
}

class addAuth
{
    public addAuth()
    {
        JFrame jf=new JFrame();
        JPanel jp=new JPanel();
        JButton jb1=new JButton("确定"),jb2=new JButton("返回");
        JTextField jtf1=new JTextField(10),jtf2=new JTextField(10),jtfno=new JTextField(10);
        jp.setLayout(new GridLayout(4,2));
        jp.add(new JLabel("编号"));
        jp.add(jtfno);
        jp.add(new JLabel("账号"));
        jp.add(jtf1);
        jp.add(new JLabel("权限"));
        jp.add(jtf2);
        jp.add(jb1);
        jp.add(jb2);
        jf.add(jp);
        jf.pack();
        jf.setVisible(true);

        jb1.addActionListener((e)->
        {
            db dbcon=new db();
            String sql="insert into auth values(?,?,?)";
            try
            {
                PreparedStatement pre=dbcon.PreparedStatement(sql);
                pre.setString(1,jtfno.getText());
                pre.setString(2,jtf1.getText());
                pre.setString(3,jtf2.getText());
                pre.executeUpdate();
                new gui(4);
                jf.dispose();
            }
            catch (SQLException sqle)
            {
                System.out.println(sqle);
            }
        });

        jb2.addActionListener((e)->
        {
            new gui(4);
            jf.dispose();
        });

    }
}