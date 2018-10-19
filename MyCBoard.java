import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.datatransfer.*;
import java.awt.image.*;

class MyCBoard
{
    public static void add(int  i) throws Exception
    {
        System.out.println(Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor));
    }

    public static void get(int i)
    {

    }

}