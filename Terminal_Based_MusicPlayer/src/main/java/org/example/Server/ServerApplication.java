package org.example.Server;


public class ServerApplication
{
    public static void main(String[] args)
    {
        int port = 6000;
        ServerSockets socket = new ServerSockets(port);
        socket.start();
    }
}
