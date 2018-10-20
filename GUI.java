import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

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
        pane.setComponentZOrder(pane.getVerticalScrollBar(), 0);
        pane.setComponentZOrder(pane.getViewport(), 1);
        pane.getVerticalScrollBar().setOpaque(false);
        pane.setBackground(new Color(21, 99, 91, 120));
        pane.setLayout(new ScrollPaneLayout() {
            @Override
            public void layoutContainer(Container parent) {
                JScrollPane pane = (JScrollPane) parent;
      
                Rectangle availR = pane.getBounds();
                availR.x = availR.y = 0;
      
                Insets parentInsets = parent.getInsets();
                availR.x = parentInsets.left;
                availR.y = parentInsets.top;
                availR.width -= parentInsets.left + parentInsets.right;
                availR.height -= parentInsets.top + parentInsets.bottom;
      
                Rectangle vsbR = new Rectangle();
                vsbR.width = 12;
                vsbR.height = availR.height;
                vsbR.x = availR.x + availR.width - vsbR.width;
                vsbR.y = availR.y;
      
                if (viewport != null) {
                    viewport.setBounds(availR);
                }
                if (vsb != null) {
                    vsb.setVisible(true);
                    vsb.setBounds(vsbR);
                }
            }
        });
        pane.getVerticalScrollBar().setUI(new MyScrollBarUI());
        // pane.getVerticalScrollBar().setPreferredSize(new Dimension(1, Integer.MAX_VALUE));
        p.setBackground(new Color(21, 99, 91, 120));
        add(pane, BorderLayout.CENTER);
        
    }
    class MyScrollBarUI extends BasicScrollBarUI {
        private final Dimension d = new Dimension();
      
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return new JButton() {
                @Override
                public Dimension getPreferredSize() {
                    return d;
                }
            };
        }
      
        @Override
        protected JButton createIncreaseButton(int orientation) {
            return new JButton() {
                @Override
                public Dimension getPreferredSize() {
                    return d;
                }
            };
        }
      
        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
        }
      
        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
            Color color = null;
            JScrollBar sb = (JScrollBar) c;
            if (!sb.isEnabled() || r.width > r.height) {
                return;
            } 
            else if (isDragging) {
                color = new Color(15, 100, 99, 120);
            } 
            else if (isThumbRollover()) {
                color = new Color(15, 100, 99, 180);
            } 
            else {
                color = new Color(213, 213, 213, 60);
            }
            g2.setPaint(color);
            g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10);
            g2.setPaint(Color.GRAY);
            g2.drawRoundRect(r.x, r.y, r.width, r.height, 10, 10);
            g2.dispose();
        }
      
        @Override
        protected void setThumbBounds(int x, int y, int width, int height) {
            super.setThumbBounds(x, y, width, height);
            scrollbar.repaint();
        }
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