package org.vibelite.Server.handler;

import org.json.JSONObject;
import org.vibelite.Server.ServerApplication;
import static org.vibelite.Server.utils.Constants.*;

import java.io.*;
import java.net.Socket;

public class ServerRequestHandler
{
    private PrintWriter writer;

    private final AudioStreamer audioStreamer;

    ServerRequestHandler(Socket socket)
    {
        audioStreamer = new AudioStreamer(socket);

        try
        {
            this.writer = new PrintWriter(socket.getOutputStream(), true);
        }
        catch(IOException e)
        {
            System.out.println("(Error) cannot write on outputStream: " + e.getMessage());
        }

    }

    public void sendLibrary()
    {

        JSONObject fileJSON = new JSONObject();

        fileJSON.put(REQUEST_LIBRARY,ServerApplication.musicLibrary.getAudioFilename());

        writer.println(fileJSON);
    }

    public void streamAudioFile(String audioName)
    {
        audioStreamer.streamAudioToClient(audioName);
    }

    public void sendAudioFile(String audioName)
    {

        // TODO:- audioStreamer.sendAudioToClient() may return null...
        audioStreamer.sendAudioToClients(audioName);
    }

    public void createPlaylist(String playlistName)
    {
        ServerApplication.musicLibrary.createPlaylist(playlistName);
    }

    public void saveAudioFile(String audioName)
    {
        audioStreamer.saveAudioFromClient(audioName);
    }
    public void sendPlaylistNames()
    {
        JSONObject json = new JSONObject();
        json.put(REQUEST_PLAYLIST_NAMES, ServerApplication.musicLibrary.getPlayListNames());

        writer.println(json);
    }

    public void sendPlaylist(String playlistName)
    {
        JSONObject json = new JSONObject();
        json.put(REQUEST_PLAYLIST,ServerApplication.musicLibrary.getPlaylists(playlistName));

        writer.println(json);
    }

    public void updateToPlaylist(String command,String audioName)
    {

        var inputIdentifier = audioName.split(PATH_SEPARATOR);

        if(inputIdentifier.length != 2)
        {
            writer.println("(ERROR) Not valid input");

            return;
        }

        // playlist name + audio name
        var message = ServerApplication.musicLibrary.updatePlaylist(command,inputIdentifier[0], inputIdentifier[1]);

        writer.println(message + inputIdentifier[0]);
    }

    public void removePlaylist(String playlistName)
    {
        ServerApplication.musicLibrary.updatePlaylist(playlistName);

        writer.println("Successfully deleted playlist " + playlistName);
    }
}
