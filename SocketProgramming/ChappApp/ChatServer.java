package SocketProgramming.ChappApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

class ReadServer extends Thread
{
    private static volatile boolean f = false;

    private final Socket socket;

    ReadServer(Socket socket)
    {
        this.socket = socket;
    }

    public static void changeFlag()
    {
        f = true;
    }

    public void run()
    {
        try
        {
            BufferedReader readClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String receiveMessage;

            while(true)
            {
                if(f)
                    break;

                if((receiveMessage = readClient.readLine()) != null)
                {

                    System.out.println("(Client): " + receiveMessage);

                    if(receiveMessage.equals("exit"))
                        break;
                }


            }
            socket.close();
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}

public class ChatServer
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("Entre the port number for server");

        int port = 12345;

        try
        {
            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("Waiting for client.....");

            Socket socket = serverSocket.accept();

            System.out.println("Client Connected");

            //            BufferedReader readClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter sendClient = new PrintWriter(socket.getOutputStream(), true);

            new Thread(new ReadServer(socket)).start();

            String receiveMessage;
            while(true)
            {
                //                if((receiveMessage = readClient.readLine())!= null)
                //                  System.out.println("(Client): " + receiveMessage);
                System.out.println("(server) Entre Message to send: ");
                String message = sc.nextLine();
                sendClient.println(message);
                if(message.equals("exit"))
                {
                    ReadServer.changeFlag();
                    break;
                }

            }

        } catch(IOException e)
        {
            throw new RuntimeException(e);
        }

    }
}
