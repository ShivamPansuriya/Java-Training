package org.vibelite.Server.handler;

import org.json.JSONObject;
import org.vibelite.Server.ServerApplication;
import static org.vibelite.Server.utils.Constants.*;

import java.io.*;
import java.net.Socket;

public class ServerRequestHandler
{

    private ObjectOutputStream response;

    private final AudioStreamer audioStreamer;

    ServerRequestHandler(Socket socket)
    {
        audioStreamer = new AudioStreamer(socket);

        try
        {
            this.response = new ObjectOutputStream(socket.getOutputStream());
        }
        catch(IOException e)
        {
            System.out.println("(Error) cannot write on outputStream: " + e.getMessage());
        }

    }

    public void sendLibrary()
    {
        var files = ServerApplication.musicLibrary.getAudioFilename();

        try
        {
            response.writeObject(files);
        }
        catch(IOException e)
        {
            System.out.println("(Error) cannot send Library: " + e.getMessage());
        }
    }

    public void streamAudioFile(String audioName)
    {
        audioStreamer.streamAudioToClient(audioName);
    }

    public void sendAudioFile(String audioName)
    {

        try
        {
            // TODO:- audioStreamer.sendAudioToClient() may return null...
            response.writeObject(audioStreamer.sendAudioToClient(audioName).toString());
        }
        catch(IOException e)
        {
            System.out.println("(ERROR) cannot send audio file to client");
        }
    }

    public void createPlaylist(String playlistName)
    {
        ServerApplication.musicLibrary.createPlaylist(playlistName);
    }

    public void saveAudio(String audioJSON)
    {
        audioStreamer.saveAudioFromClient(audioJSON);
    }
    public void sendPlaylistNames()
    {
        try
        {
            response.writeObject(ServerApplication.musicLibrary.getPlayListNames());
        }
        catch(IOException e)
        {
            System.out.println("(Error) cannot send Playlist: " + e.getMessage());
        }
    }

    public void sendPlaylist(String playlistName)
    {
        try
        {
            response.writeObject(ServerApplication.musicLibrary.getPlaylists(playlistName));
        }
        catch(IOException e)
        {
            System.out.println("(Error) cannot send Playlist: " + e.getMessage());
        }
    }

    public void updateToPlaylist(String command,String audioName)
    {

        try
        {
            var inputIdentifier = audioName.split(PATH_SEPARATOR);

            if(inputIdentifier.length != 2)
                response.writeObject("(ERROR) Not valid input");

            // playlist name + audio name
            var message = ServerApplication.musicLibrary.updatePlaylist(command,inputIdentifier[0], inputIdentifier[1]);

            response.writeObject(message + inputIdentifier[0]);
        }
        catch(IOException e)
        {
            System.out.println("client is disconnected cannot send message");
        }
    }

    public void removePlaylist(String playlistName)
    {
        try
        {

            ServerApplication.musicLibrary.updatePlaylist(playlistName);

            response.writeObject("Successfully deleted playlist " + playlistName);
        } catch(IOException e)
        {
            System.out.println("client is disconnected cannot send message");
        }
    }
}
