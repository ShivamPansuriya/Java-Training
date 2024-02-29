package SocketProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class ClientThread extends Thread
{
    private Socket socket = null;

    ClientThread(Socket s)
    {
        this.socket = s;
    }

    public void run()
    {
        PrintWriter bw = null;
        try
        {
            bw = new PrintWriter(socket.getOutputStream(), true);

            bw.println("hello client!");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println(Thread.currentThread().getName()+" : "+bufferedReader.readLine());

            bw.close();

            bufferedReader.close();

            socket.close();
        } catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}

public class Socket_Programming_multiuser
{
    //server code and test it with terminal telnet localhost 9090
    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(9090);

        boolean flag = false;
        while(true)
        {

            System.out.println("waiting for client...");

            Socket socket = serverSocket.accept();

            System.out.println("Client connected!!!");

            new Thread(new ClientThread(socket)).start();
        }
    }
}
