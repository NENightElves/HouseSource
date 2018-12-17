import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLConnection;

public class pic
{
    public pic(String urls)
    {
        int srcWidth=0,srcHeight=0;
        JFrame jf=new JFrame();
        URL url=null;
        try{url=new URL(urls);}catch (Exception e){}
        Image image=Toolkit.getDefaultToolkit().getImage(url);
        MyCanvas mc=new MyCanvas(image);
        try
        {
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            BufferedImage imagex = ImageIO.read(connection.getInputStream());
            srcWidth = imagex.getWidth();
            srcHeight = imagex.getHeight();
        }
        catch (Exception e){}
        jf.add(mc);
        jf.pack();
        jf.setSize(srcWidth,srcHeight);
        jf.setVisible(true);
    }
    class MyCanvas extends Canvas
    {
        private Image in;
        public int x,y;
        public MyCanvas(Image in)
        {
            this.in=in;
        }
        public void paint(Graphics g)
        {
            g.drawImage(in,0,0,this);
        }
    }
    public static void main(String args[])
    {
        new pic("http://gayhy.top:12345/iisstart.png");
    }
}
