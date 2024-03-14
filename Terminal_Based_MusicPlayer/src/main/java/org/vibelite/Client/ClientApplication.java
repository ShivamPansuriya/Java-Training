package org.vibelite.Client;

import org.vibelite.Client.connection.ClientSocket;

public class ClientApplication
{
    public static void main(String[] args)
    {
        int port = 6000;
        String serverIP = "localhost";

        ClientSocket clientSocket = new ClientSocket(serverIP,port);
        clientSocket.connect();
    }
}
