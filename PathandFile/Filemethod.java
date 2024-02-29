package PathandFile;

import java.io.File;
import java.io.IOException;

public class Filemethod
{

    static void useFile(String filename)
    {
        File file = new File(filename);

        //checking if file exist and if it is then we will delete it
        if(file.exists())
        {
            System.out.println("deleting file");

            file.delete();
        }

        // creating new file if file does not eixst
        if(!file.exists())
        {
            System.out.println("File Does not exist creating new file");

            try
            {
                file.createNewFile();
            } catch(IOException e)
            {
                throw new RuntimeException(e);
            }

            System.out.println("create new file: "+ filename);

            if(file.canWrite()){
                System.out.println("performing write operation on :" + filename);
            }
        }
    }

    public static void main(String[] args)
    {
        useFile("./src/PathandFile/file.txt");
    }
}
