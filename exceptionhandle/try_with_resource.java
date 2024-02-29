package exceptionhandle;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class try_with_resource {

    public static  void main(String[] args) throws Exception
    {
        try(FileReader f = new FileReader("src/exceptionhandle/my.txt"))
        {

            f.close();
        }

    }
}
