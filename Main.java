import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.beans.*;
class Main
{
    static MyCBoard clipboard;
    static
    {
        System.loadLibrary("keygrabber");
    }

    private native void grabkey();

    public static void copyData(int i) throws Exception
    {
        clipboard.add(i);
    }

    public static void pasteData(int i) throws Exception
    {
        clipboard.get(i);
    }

    public static void gui()
    {
        EventQueue.invokeLater(new Runnable(){
            public void run()
            {
                final GUI g = new GUI();
                g.setVisible(true);
                g.setSize(455, 600);
                g.setResizable(false);

                KeyboardFocusManager.getCurrentKeyboardFocusManager().
        addVetoableChangeListener( "focusedWindow",
                                   new VetoableChangeListener() {
                                     private boolean gained = false;

                                     @Override
                                     public void vetoableChange( PropertyChangeEvent evt ) throws PropertyVetoException {
                                       if ( evt.getNewValue() == g ) {
                                         gained = true;
                                       }
                                       if ( gained && evt.getNewValue() != g ) {
                                         g.dispose();
                                       }
                                     }
                                   } );

                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
                Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
                int x = (int) rect.getMaxX() - g.getWidth();
                int y = (int) rect.getMaxY() - g.getHeight();
                g.setLocation(x, y);
            }
        });
       System.out.println("hi");
    }

    public static void main(String[] args) throws Exception
    {
        clipboard = new MyCBoard();
        new Main().grabkey();
    }
}
