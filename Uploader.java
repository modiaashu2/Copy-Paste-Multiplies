import java.util.zip.*;
import java.io.*;
import java.net.*;

public class Uploader
{
    private String url;
    public Uploader(String url)
    {
        this.url = url;
    }
    public void publish(File[] files) throws Exception
    {
        File inputZipFile = File.createTempFile("upload",".zip");
        FileOutputStream fos = new FileOutputStream(inputZipFile);
        java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(fos);
        
        //add all selected file to the zip file
        for (int i = 0; i < files.length; i++) 
        {
            ZipEntry zipEntry = new ZipEntry(files[i].getName());
            zipEntry.setSize(files[i].length());
            zos.putNextEntry(zipEntry);
            FileInputStream fis = new FileInputStream(files[i]);
            byte[] inputFile = new byte[fis.available()];
            fis.read(inputFile);
            zos.write(inputFile);
            fis.close();
        }
        zos.close();
        fos.close();

        FileInputStream fis = new FileInputStream(inputZipFile);
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes);
        fis.close();
 
        //open a connection
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        //set http header boundary 
        String boundary = "-----------------------------" + ((int)(Math.random() * 100000000));
        //different parts in the http request are forwarded by a header//this header contains the type, filename , ...
        String header = boundary + "\r\n" + 
        "Content-Disposition: form-data; name=\"file1\"; filename=\"upload.zip\"" + "\r\n" +
        "Content-Type: application/x-zip-compressed" + "\r\n" + "\r\n";
        String footer = "\r\n" + boundary + "--";
        con.setRequestMethod("POST");
        //request is closed using a footer
        con.setRequestProperty("Content-length", String.valueOf(bytes.length + header.length()+ footer.length()));
        //fill up the http header
        con.setRequestProperty("content-type","multipart/form-data; boundary=" + boundary.substring(2));
        OutputStream out = con.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(out);
        //write the header   bos.write(header.getBytes());
            
        //write the file
        bos.write(bytes);
        
        //write the footerbos.write(footer.getBytes());
        bos.flush();
        
        // Read the response
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        while (true) 
        {
            String line = in.readLine();
            if (line == null)
            break;
            System.out.println(line);
        }
        in.close();
        out.close();
        bos.close();
        con.disconnect();
        
        //remove the temp file 
        inputZipFile.delete(); 
    }

    public static void main(String args[]) 
    {
        try 
        {
            File [] fileList = {new File("send2.txt"), new File("Uploader.java")};
            new Uploader("http://5fed7094.ngrok.io/").publish(fileList);
        }
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
}