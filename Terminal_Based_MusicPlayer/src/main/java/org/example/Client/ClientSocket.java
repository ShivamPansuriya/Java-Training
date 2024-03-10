package org.example.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

//            try(BufferedReader receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));){
//                System.out.println(receive.readLine());
//            }
        } catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
