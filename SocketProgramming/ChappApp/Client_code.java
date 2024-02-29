package SocketProgramming.ChappApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client_code
{
    public static void main(String[] args)
    {
        try
        {
            Scanner sc = new Scanner(System.in);

            InetAddress inetAddress = InetAddress.getByName("localhost");

            Socket clientSocket = new Socket(inetAddress, 12345);

            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(),true);

            String message;

            new Thread(new ReadServer(clientSocket)).start();

            while(true){
                System.out.println("(Me)Entre the message: ");

                message = sc.nextLine();

                if(message.equals("exit")){
                    ReadServer.changeFlag();
                    break;
                }

                printWriter.println(message);

            }
            clientSocket.close();

        } catch(IOException e)
        {
            throw new RuntimeException(e);
        }

    }
}
