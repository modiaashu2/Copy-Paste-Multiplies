import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.datatransfer.*;
import java.awt.image.*;

class MyCBoard
{
    private static Clipboard[] cboard;
    private static Object[] data;
    private static DataFlavor[] dataflavor;
    static{
        cboard = new Clipboard[5];
        data = new Object[5];
        dataflavor = new DataFlavor[5];
    }
    public static void add(int  i) throws Exception
    {
        System.out.println("Inside add function java block at " + i + " clipboard");
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tble = c.getContents(null);

        if(c.getContents(null).isDataFlavorSupported(DataFlavor.stringFlavor))
        {
            data[i] = (String)c.getContents(null).getTransferData(DataFlavor.stringFlavor);
            dataflavor[i] = DataFlavor.stringFlavor;
        }
        if(c.getContents(null).isDataFlavorSupported(DataFlavor.imageFlavor))
        {
            data[i] = (Image)c.getContents(null).getTransferData(DataFlavor.imageFlavor);
            dataflavor[i] = DataFlavor.imageFlavor;
        }
        if(c.getContents(null).isDataFlavorSupported(DataFlavor.javaFileListFlavor))
        {
            data[i] = (java.util.List)c.getContents(null).getTransferData(DataFlavor.javaFileListFlavor);
            dataflavor[i] = DataFlavor.javaFileListFlavor;
        }
        System.out.println(data[i]);
    }

    public static void get(int i) throws Exception
    {
        System.out.println("Inside get function java block at " + i + " clipboard");
        System.out.println("Contents are: ");

        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        System.out.println("Contents are: ");
        Object fdata = null;
        if(dataflavor[i].equals(DataFlavor.stringFlavor))
        {
            fdata = new StringSelection((String)data[i]);
        }
        else if(dataflavor[i].equals(DataFlavor.javaFileListFlavor))
        {
            fdata = new FileSelection((java.util.List)data[i]);
        }
        else if(dataflavor[i].equals(DataFlavor.imageFlavor))
        {
            fdata = new ImageSelection((Image)data[i]);
        }
        
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents((Transferable)fdata, new ClipboardOwner(){
            public void lostOwnership(Clipboard c, Transferable t)
            {
                System.out.println("Lost ownership");
            }
        });
        
    }   

}
