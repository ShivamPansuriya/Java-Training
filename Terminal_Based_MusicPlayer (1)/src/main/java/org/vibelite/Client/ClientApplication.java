package org.vibelite.Client;

import org.vibelite.Client.connection.ClientSocket;
import static org.vibelite.Client.utils.Constants.*;

public class ClientApplication
{
    public static void main(String[] args)
    {

        var clientSocket = new ClientSocket(IP_ADDRESS,PORT);
        clientSocket.connect();
    }
}
