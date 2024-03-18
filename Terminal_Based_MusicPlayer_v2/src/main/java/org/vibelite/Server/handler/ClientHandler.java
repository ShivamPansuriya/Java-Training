package org.vibelite.Server.handler;

import static org.vibelite.Server.utils.Constants.*;

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
        var serverRequestHandler = new ServerRequestHandler(clientSocket);

        try(BufferedReader receive = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            while(true)
            {
                //request type from client
                var request = receive.readLine();

                // TODO :- make constants for exit. library, audiofile, etc : Done
                if(request==null || request.equals(REQUEST_EXIT))
                    break;

                switch(request)
                {
                    case REQUEST_LIBRARY:
                        serverRequestHandler.sendLibrary();
                        break;

                    case REQUEST_AUDIO_STREAMING:
                        //audio file name input from client
                        request = receive.readLine();

                        serverRequestHandler.streamAudioFile(request);

                        break;

                    case REQUEST_CREATE_PLAYLIST:
                        //playlist name input from client
                        request = receive.readLine();

                        serverRequestHandler.createPlaylist(request);

                        break;

                    case REQUEST_PLAYLIST_NAMES:
                        serverRequestHandler.sendPlaylistNames();

                        break;

                    case REQUEST_ADDTO_PLAYLIST:
                        //audio file name input from client
                        request = receive.readLine();

                        serverRequestHandler.updateToPlaylist(REQUEST_ADDTO_PLAYLIST,request);

                        break;

                    case REQUEST_REMOVE_FROM_PLAYLIST:
                        request = receive.readLine();

                        serverRequestHandler.updateToPlaylist(REQUEST_REMOVE_FROM_PLAYLIST,request);

                        break;

                    case REQUEST_REMOVE_PLAYLIST:
                        request = receive.readLine();

                        serverRequestHandler.removePlaylist(request);

                        break;

                    case REQUEST_PLAYLIST:
                        //playlist name input from client
                        request = receive.readLine();

                        serverRequestHandler.sendPlaylist(request);

                        break;

                    case REQUEST_UPLOAD:
                        request = receive.readLine();

                        serverRequestHandler.saveAudioFile(request);

                        break;

                    case REQUEST_DOWNLOAD:
                        request = receive.readLine();

                        serverRequestHandler.sendAudioFile(request);

                        break;
                }
            }
        }
        catch(IOException e)
        {
            System.out.println("An IOException occurred. Please check your input or connection." + e.getMessage());
        }
        catch(NullPointerException e)
        {
            System.out.println("(ERROR) entered request is null: ");
        }
        finally
        {
            try
            {
                clientSocket.close();

                System.out.println("client connection closed: " + clientSocket);
            }
            catch(IOException e)
            {
                System.out.println("(ERROR) client socket is closed");
            }
        }
    }
}
