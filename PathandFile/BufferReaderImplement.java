package PathandFile;


import java.io.*;

public class    BufferReaderImplement
{

    public static void main(String[] args)
    {

        try(FileReader reader = new FileReader("./src/PathandFile/file.txt"))
        {
            char[] block = new char[1000];

            int data;

            while((data = reader.read(block)) != -1)
            {
                String content = new String(block, 0, data);

                System.out.printf("---> [%d chars] %s%n", data, content);
            }
        } catch(IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("-----------------------------------");

        try(BufferedWriter bw = new BufferedWriter(new FileWriter("./src/PathandFile/file.txt"));
            BufferedReader bufferedReader = new BufferedReader(new FileReader("./src/PathandFile/file.txt")))
        {
            bw.write("shivam");
            bw.close();
            bufferedReader.lines().forEach(System.out::println);
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
