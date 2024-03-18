package org.vibelite.Client;

import java.io.IOException;
import java.net.Socket;

public class ClientSocket
{
    private final int serverPort;

    private final String serverIP;

    private  Socket socket;

    ClientSocket(String serverIP, int serverPort){
        this.serverIP = serverIP;

        this.serverPort = serverPort;
    }

    public void connect(){
        try{
            socket = new Socket(serverIP,serverPort);

            ClientRequestHandler requestHandler = new ClientRequestHandler(socket);

            TerminalUI terminalUI = new TerminalUI(this, requestHandler);

            terminalUI.start();

        } catch(IOException e)
        {
            System.out.println("Socket you are trying to connect is not available");

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
