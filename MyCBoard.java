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
    private static String flavor;
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
        Object writedata = "";
        flavor = "";
        if(c.getContents(null).isDataFlavorSupported(DataFlavor.stringFlavor))
        {
            data[i] = (String)c.getContents(null).getTransferData(DataFlavor.stringFlavor);
            writedata = data[i];
            dataflavor[i] = DataFlavor.stringFlavor;
            flavor = "string";
        }
        if(c.getContents(null).isDataFlavorSupported(DataFlavor.imageFlavor))
        {
            data[i] = (Image)c.getContents(null).getTransferData(DataFlavor.imageFlavor);
            dataflavor[i] = DataFlavor.imageFlavor;
            writedata = new ImageSerializable((BufferedImage)data[i]);
            flavor = "image";
        }
        if(c.getContents(null).isDataFlavorSupported(DataFlavor.javaFileListFlavor))
        {
            data[i] = (java.util.List)c.getContents(null).getTransferData(DataFlavor.javaFileListFlavor);
            dataflavor[i] = DataFlavor.javaFileListFlavor;
            writedata = data[i];
            flavor = "file";
        }
        System.out.println(data[i] + "\n" + dataflavor[i]);

        FileOutputStream fout = new FileOutputStream("send" + i + ".txt");
        ObjectOutputStream fw = new ObjectOutputStream(fout);
        fw.writeObject(writedata);
        fw.close();
        FileWriter fw_ = new FileWriter("sendtype" + i + ".txt");
        fw_.write(flavor);
        fw_.close();
        
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
