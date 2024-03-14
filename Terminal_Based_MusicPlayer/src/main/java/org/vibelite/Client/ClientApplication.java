package org.vibelite.Client;

import org.vibelite.Client.connection.ClientSocket;

public class ClientApplication
{
    public static void main(String[] args)
    {
        var port = 6000;
        var serverIP = "localhost";

        var clientSocket = new ClientSocket(serverIP,port);
        clientSocket.connect();
    }
}
