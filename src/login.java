import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login
{
    public static void main(String[] args)
    {
        JFrame jf=new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel jp=new JPanel();
        JButton jb=new JButton("登录");
        JLabel jl1=new JLabel("账号");
        JLabel jl2=new JLabel("密码");
        JTextField jtf1=new JTextField(10);
        JTextField jtf2=new JTextField(10);
        jp.setLayout(new GridLayout(2,2));
        jp.add(jl1);
        jp.add(jtf1);
        jp.add(jl2);
        jp.add(jtf2);
        jf.add(jp,BorderLayout.CENTER);
        jf.add(jb,BorderLayout.SOUTH);
        jf.setSize(400,150);
        jf.setVisible(true);
        jb.addActionListener((e)->
        {
            db dbcon=new db();
            String sql="select passwd from pw where name='"+jtf1.getText()+"'";
            String pwd;
            try
            {
                ResultSet rs = dbcon.executeQuery(sql);
                if (rs.next())
                {
                    pwd=rs.getString("passwd");
                    if (pwd.equals(jtf2.getText()))
                    {
                        JOptionPane.showMessageDialog(null,"登录成功");
                        staticvar.auth=jtf1.getText();
                        if (staticvar.auth.equals("admin")) staticvar.authSta=true; else staticvar.authSta=false;
                        new gui(1);
                        jf.dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"密码错误");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"用户名不存在");
                }
            }
            catch (SQLException sqle)
            {
                System.out.println(sqle.toString());
            }
        });
    }
}
