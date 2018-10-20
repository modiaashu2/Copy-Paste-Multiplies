import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.datatransfer.*;
import java.awt.image.*;

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

    public static void main(String[] args) throws Exception
    {
        clipboard = new MyCBoard();
        new Main().grabkey();
    }
}