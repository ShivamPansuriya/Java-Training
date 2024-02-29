package iotest;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

public class IOTest
{
    public static void main(String[] args) throws Exception
    {
        try(FileOutputStream fp = new FileOutputStream("iotest.txt");FileInputStream fi = new FileInputStream("/home/shivam/Java/Practice/iotest.txt"))
        {
            String s = "this is an io test 1";

            fp.write(s.getBytes());

            byte[] b = new byte[fi.available()];
            fi.read(b);
            String str = new String(b);
            System.out.println(str);

        }
    }
}
