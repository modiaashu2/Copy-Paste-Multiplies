import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

class GUI extends JFrame
{
    Vw p;
    public GUI()
    {
        p = new Vw();
        setUndecorated(true);
        setBackground(new Color(21, 99, 91, 120));
        p.setSize(400, 550);
        p.setPreferredSize(new Dimension(400, 1550));
        setLayout(new BorderLayout(40, 40));
        
        JScrollPane pane = new JScrollPane(p);
        pane.setOpaque(false);
        pane.getViewport().setOpaque(false);
        pane.setBorder(BorderFactory.createLineBorder(new Color(21, 99, 91, 0), 20));
        pane.setBackground(new Color(21, 99, 91, 120));
        pane.getVerticalScrollBar().setBackground(new Color(21, 99, 91, 120));
        pane.getVerticalScrollBar().setOpaque(false);
        pane.getVerticalScrollBar().setForeground(new Color(21, 99, 91, 120));
        
        // pane.getVerticalScrollBar().setPreferredSize(new Dimension(1, Integer.MAX_VALUE));
        p.setBackground(new Color(21, 99, 91, 120));
        add(pane, BorderLayout.CENTER);
    }
    public static void main(String[] ar)
    {
        EventQueue.invokeLater(new Runnable(){
            public void run()
            {
                GUI g = new GUI();
                g.setVisible(true);
                g.setSize(455, 600);
                g.setResizable(false);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
                Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
                int x = (int) rect.getMaxX() - g.getWidth();
                int y = (int) rect.getMaxY() - g.getHeight();
                g.setLocation(x, y);
            }
        });
    }
}

class Vw extends JPanel
{
    MyBox p[];
    public Vw()
    {
        p = new MyBox[20];
        setOpaque(false);
        setBackground(new Color(21, 99, 91, 120));
        setLayout(new GridLayout(10, 2, 30, 30));
        for(int i = 0; i < 20; i++)
        {
            p[i] = new MyBox();
            this.add(p[i]);
        }
        
    }
}

class MyBox extends JPanel
{
    public MyBox()
    {
        setSize(150, 150);
        setBackground(new Color(44, 140, 139, 150));
    }
}