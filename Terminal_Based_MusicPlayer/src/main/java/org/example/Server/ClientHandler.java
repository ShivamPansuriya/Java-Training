package org.example.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler extends Thread
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
        try(BufferedReader receive = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));)
        {
            while(true)
            {
                String request = receive.readLine();

                if(request.equals("exit"))
                    break;

                switch(request)
                {
                    case "library":
                        serverRequestHandler.sendLibrary();
                        break;

                    case "audioFile":
                        request = receive.readLine();
                        serverRequestHandler.sendAudioFile(request);
                        break;

                    case "createPlaylist":
                        request = receive.readLine();
                        serverRequestHandler.createPlaylist(request);
                        break;

                    case "getPlaylist":
                        serverRequestHandler.sendPlaylist();
                        break;
                }
            }
        } catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
