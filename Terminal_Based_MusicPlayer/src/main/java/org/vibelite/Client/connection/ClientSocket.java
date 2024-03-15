package org.vibelite.Client.connection;

import org.vibelite.Client.ClientApplication;
import org.vibelite.Client.handler.ClientRequestHandler;
import org.vibelite.Client.ui.TerminalUI;

import java.io.IOException;
import java.net.Socket;

public class ClientSocket
{
    private final int serverPort;

    private final String serverIP;

    private  Socket socket;

    public ClientSocket(String serverIP, int serverPort){
        this.serverIP = serverIP;

        this.serverPort = serverPort;
    }

    public void connect(){
        try{
            //establish connection with server
            socket = new Socket(serverIP,serverPort);

            // create single clientRequestHandler object
            var requestHandler = new ClientRequestHandler(socket);

            // creating main ui object and redirecting client to ui
            var terminalUI = new TerminalUI(this, requestHandler);

            terminalUI.start();

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
            ClientApplication.main(null);
        }
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Cannot disconnect socket connection");
        }
    }
}
