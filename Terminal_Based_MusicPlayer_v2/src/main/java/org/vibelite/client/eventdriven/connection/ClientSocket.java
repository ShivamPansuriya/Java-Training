package org.vibelite.client.eventdriven.connection;

import static org.vibelite.client.eventdriven.utils.Constants.*;

import org.vibelite.client.eventdriven.ClientApplication;
import org.vibelite.client.eventdriven.handler.ClientRequestHandler;
import org.vibelite.client.eventdriven.ui.TerminalUI;

import java.io.IOException;
import java.net.Socket;


public class ClientSocket
{
    private  Socket socket;

    private TerminalUI terminalUI;

    public ClientSocket(TerminalUI terminalUI)
    {
        this.terminalUI = terminalUI;
    }

    public ClientRequestHandler connect()
    {
        ClientRequestHandler requestHandler = null;

        try
        {
            //establish connection with server
            socket = new Socket(IP_ADDRESS,PORT);

            // create single clientRequestHandler object
            requestHandler = new ClientRequestHandler(socket, terminalUI);

        }
        catch(IOException e)
        {
            System.out.println("Server you are trying to connect is not available");

            try
            {
                Thread.sleep(5000);
            }
            catch(InterruptedException ex)
            {
                System.out.println("(FATAL ERROR)");
                ClientApplication.logger.error("reconnect sleep interrupted");
            }
        }
        return requestHandler;
    }

    public void disconnect() {
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            System.out.println("Cannot disconnect socket connection");
            ClientApplication.logger.error("Cannot disconnect socket connection");
        }
    }
}
