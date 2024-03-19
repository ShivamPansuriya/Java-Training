package org.vibelite.Server.handler;

import org.json.JSONException;
import org.json.JSONObject;
import org.vibelite.Server.ServerApplication;

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
            String request;
            while((request = receive.readLine())!= null)
            {
                //request type from client

                ServerApplication.logger.info(request);

                var reqJson = new JSONObject(request);

                // TODO :- make constants for exit. library, audiofile, etc : Done
                if(request.equals(REQUEST_EXIT))
                    break;

                switch(reqJson.getString(COMMAND))
                {
                    case REQUEST_LIBRARY:
                        serverRequestHandler.sendLibrary();
                        break;

                    case REQUEST_AUDIO_STREAMING:
                        //audio file name input from client
                        serverRequestHandler.streamAudioFile(reqJson.getString(MESSAGE));

                        break;

                    case REQUEST_CREATE_PLAYLIST:
                        //playlist name input from client
                        serverRequestHandler.createPlaylist(reqJson.getString(MESSAGE), reqJson.getString("username"));

                        break;

                    case REQUEST_PLAYLIST_NAMES:
                        serverRequestHandler.sendPlaylistNames();

                        break;

                    case REQUEST_ADDTO_PLAYLIST:
                        //audio file name input from client
                        serverRequestHandler.updateToPlaylist(REQUEST_ADDTO_PLAYLIST,reqJson.getString(MESSAGE),reqJson.getString("username"));

                        break;

                    case REQUEST_REMOVE_FROM_PLAYLIST:
                        serverRequestHandler.updateToPlaylist(REQUEST_REMOVE_FROM_PLAYLIST,reqJson.getString(MESSAGE), reqJson.getString("username"));

                        break;

                    case REQUEST_REMOVE_PLAYLIST:
                        serverRequestHandler.removePlaylist(reqJson.getString(MESSAGE), reqJson.getString("username"));

                        break;

                    case REQUEST_PLAYLIST:
                        //playlist name input from client
                        serverRequestHandler.sendPlaylist(reqJson.getString(MESSAGE));

                        break;

                    case REQUEST_UPLOAD:
                        serverRequestHandler.saveAudioFile(reqJson.getString(MESSAGE));

                        break;

                    case REQUEST_DOWNLOAD:
                        serverRequestHandler.sendAudioFile(reqJson.getString(MESSAGE));

                        break;

                    case REGISTER:
                        serverRequestHandler.addNewUser(reqJson.getString("username"), reqJson.getString("password"));

                        break;

                    case LOGIN:
                        serverRequestHandler.validateUser(reqJson.getString("username"), reqJson.getString("password"));

                        break;
                }
            }
        }
        catch(IOException e)
        {
            ServerApplication.logger.error("An IOException occurred. Please check your input or connection." + e.getMessage());
        }
        catch(JSONException e)
        {
            ServerApplication.logger.error("send request of improper json format");
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
                ServerApplication.logger.error("(ERROR) client socket is closed");
            }
        }
    }
}
