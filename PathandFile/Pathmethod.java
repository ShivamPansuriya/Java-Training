package PathandFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Pathmethod
{
    static void usePath(String filename)
    {
        Path path = Path.of(filename);

        //checking if file exist and if it is then we will delete it
        if(Files.exists(path))
        {
            System.out.println("deleting file");

            try
            {
                Files.delete(path);
            } catch(IOException e)
            {
                throw new RuntimeException(e);
            }
        }

        // creating new file if file does not eixst
        if(!Files.exists(path))
        {
            System.out.println("File Does not exist creating new file");

            try
            {
                Files.createFile(path);


                System.out.println("create new file: " + filename);

                if(Files.isWritable(path))
                {
                    System.out.println("performing write operation on :" + filename);

                    Files.writeString(path, """
                            this is the test path file
                            line 2
                            line 3
                            Finish!!!!!""");
                }

                System.out.println("I can tooooooo");
                System.out.println("-------------------");

                String Content = Files.readString(path);
                System.out.println(Content);
            }
            catch(IOException e)
            {
//                throw new RuntimeException(e);
                System.out.println("some error while creating or writing a file");
            }
        }
    }

    public static void main(String[] args)
    {
        usePath("./src/PathandFile/path.txt");
    }
}
