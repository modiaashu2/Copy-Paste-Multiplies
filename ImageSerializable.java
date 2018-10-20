import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.datatransfer.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

class ImageSerializable
{
    transient BufferedImage image;

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        ImageIO.write(image, "png", out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        image = ImageIO.read(in);
    }

    public ImageSerializable(BufferedImage i)
    {
        image = i;
    }
}