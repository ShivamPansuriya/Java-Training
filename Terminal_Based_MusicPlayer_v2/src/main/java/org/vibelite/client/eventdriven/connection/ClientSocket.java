package org.vibelite.client.eventdriven.connection;

import static org.vibelite.client.eventdriven.utils.Constants.*;
import org.vibelite.client.eventdriven.handler.ClientRequestHandler;

import java.io.IOException;
import java.net.Socket;


public class ClientSocket
{
    private final int serverPort = PORT;

    private final String serverIP = IP_ADDRESS;

    private  Socket socket;

    public ClientRequestHandler connect(){
        ClientRequestHandler requestHandler = null;
        try{
            //establish connection with server
            socket = new Socket(serverIP,serverPort);

            // create single clientRequestHandler object
            requestHandler = new ClientRequestHandler(socket);

        } catch(IOException e)
        {
            System.out.println("Server you are trying to connect is not available");

            try
            {
                Thread.sleep(5000);
            } catch(InterruptedException ex)
            {
                System.out.println("(FATAL ERROR)");
            }
        }
        return requestHandler;
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Cannot disconnect socket connection");
        }
    }
}
