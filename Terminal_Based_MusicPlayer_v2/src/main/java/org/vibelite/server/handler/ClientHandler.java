package org.vibelite.server.handler;

import org.json.JSONException;
import org.json.JSONObject;
import org.vibelite.server.ServerApplication;

import static org.vibelite.server.utils.Constants.*;

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
                        serverRequestHandler.createPlaylist(reqJson.getString(MESSAGE), reqJson.getString(USERNAME));

                        break;

                    case REQUEST_PLAYLIST_NAMES:
                        serverRequestHandler.sendPlaylistNames();

                        break;

                    case REQUEST_ADDTO_PLAYLIST:
                        //audio file name input from client
                        serverRequestHandler.updateToPlaylist(REQUEST_ADDTO_PLAYLIST,reqJson.getString(MESSAGE),reqJson.getString(USERNAME));

                        break;

                    case REQUEST_REMOVE_FROM_PLAYLIST:
                        serverRequestHandler.updateToPlaylist(REQUEST_REMOVE_FROM_PLAYLIST,reqJson.getString(MESSAGE), reqJson.getString(USERNAME));

                        break;

                    case REQUEST_REMOVE_PLAYLIST:
                        serverRequestHandler.removePlaylist(reqJson.getString(MESSAGE), reqJson.getString(USERNAME));

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
                        serverRequestHandler.addNewUser(reqJson.getString(USERNAME), reqJson.getString("password"));

                        break;

                    case LOGIN:
                        serverRequestHandler.validateUser(reqJson.getString(USERNAME), reqJson.getString("password"));

                        break;

                    default:
                        break;
                }
            }
        }
        catch(IOException e)
        {
            ServerApplication.logger.error("An IOException occurred. Please check your input or connection. {}" , e.getMessage());
        }
        catch(NullPointerException e)
        {
            ServerApplication.logger.error("(ERROR) entered request is null: ");
        }
        catch(JSONException e)
        {
            ServerApplication.logger.error("improper json file format");
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
