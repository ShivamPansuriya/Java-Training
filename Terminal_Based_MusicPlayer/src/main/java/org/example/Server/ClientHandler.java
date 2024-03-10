package org.example.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable
{
    private final Socket clientSocket;

    ClientHandler(Socket socket)
    {

        this.clientSocket = socket;
    }

    @Override
    public void run()
    {
        ServerRequestHandler serverRequestHandler = new ServerRequestHandler(clientSocket);
        serverRequestHandler.Hello();
    }
}
