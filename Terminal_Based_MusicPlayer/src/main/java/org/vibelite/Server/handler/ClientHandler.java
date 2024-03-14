package org.vibelite.Server.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler extends Thread
{
    private final Socket clientSocket;

    public ClientHandler(Socket socket)
    {

        this.clientSocket = socket;
    }

    @Override
    public void run()
    {
        // main class from where all request will be redirected
        ServerRequestHandler serverRequestHandler = new ServerRequestHandler(clientSocket);

        try(BufferedReader receive = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));)
        {
            while(true)
            {
                //request type from client
                String request = receive.readLine();

                if(request==null || request.equals("exit"))
                    break;

                switch(request)
                {
                    case "library":
                        serverRequestHandler.sendLibrary();
                        break;

                    case "audioFile":
                        //audio file name input from client
                        request = receive.readLine();

                        serverRequestHandler.sendAudioFile(request);

                        break;

                    case "createPlaylist":
                        //playlist name input from client
                        request = receive.readLine();

                        serverRequestHandler.createPlaylist(request);

                        break;

                    case "getPlaylistName":
                        serverRequestHandler.sendPlaylistName();

                        break;

                    case "addToPlaylist":
                        //audio file name input from client
                        request = receive.readLine();

                        serverRequestHandler.addToPlaylist(request);

                        break;

                    case "getPlaylist":
                        //playlist name input from client
                        request = receive.readLine();

                        serverRequestHandler.sendPlaylist(request);

                        break;

                    case "uploadAudio":
                        request = receive.readLine();

                        serverRequestHandler.saveAudio(request);

                        break;
                }
            }
        } catch(IOException e)
        {
            System.out.println("An IOException occurred. Please check your input or connection." + e.getMessage());
        }
        catch(NullPointerException e){
            System.out.println("(ERROR) entered request is null: ");
        }
        finally
        {
            try
            {
                clientSocket.close();

                System.out.println("client connection closed: " + clientSocket);
            } catch(IOException e)
            {
                System.out.println("(ERROR) client socket is closed");
            }
        }
    }
}
