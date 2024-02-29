package SocketProgramming;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Socket_Programming_Server
{
    //server code and test it with terminal telnet localhost 9090
    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(9090);

        System.out.println("waiting for client");

        Socket socket = serverSocket.accept();

        PrintWriter bw = new PrintWriter(socket.getOutputStream(),true);

        bw.println("hello client!");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.println(bufferedReader.readLine());

        bw.close();

        bufferedReader.close();

        socket.close();
    }
}
