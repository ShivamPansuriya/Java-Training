package org.vibelite.Server.handler;

import org.json.JSONObject;
import org.vibelite.Server.ServerApplication;
import org.vibelite.Server.fileloader.MusicLibrary;

import static org.vibelite.Server.utils.Constants.*;

import java.io.*;
import java.net.Socket;

public class ServerRequestHandler
{
    private PrintWriter writer;

    private JSONObject responseJson = new JSONObject();
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


        responseJson.put(REQUEST_LIBRARY, MusicLibrary.getAudioFilename());

        writer.println(responseJson);

        ServerApplication.logger.info(responseJson.toString());

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

    public void createPlaylist(String playlistName, String username)
    {
        if(MusicLibrary.createPlaylist(playlistName, username))
        {
            responseJson.put(STATUS_CODE, "success");

            responseJson.put(MESSAGE, "Playlist created successfully");
        }
        else
        {
            responseJson.put(STATUS_CODE, "fail");

            responseJson.put(MESSAGE, "Cannot create playlist with this name");
        }

        writer.println(responseJson);

        ServerApplication.logger.info(responseJson.toString());
    }

    public void saveAudioFile(String audioName)
    {
        audioStreamer.saveAudioFromClient(audioName);
    }
    public void sendPlaylistNames()
    {
        var json = new JSONObject();

        json.put(REQUEST_PLAYLIST_NAMES, MusicLibrary.getPlayListNames());

        writer.println(json);

        ServerApplication.logger.info(responseJson.toString());
    }

    public void sendPlaylist(String playlistName)
    {
        var playlistIdentifier = playlistName.split(PLAYLIST_SEPARATOR);

        if(playlistIdentifier.length != 2)
        {
            responseJson.put(STATUS_CODE, "fail");

            responseJson.put(MESSAGE, "(ERROR) Not valid input");

            writer.println(responseJson);

            return;
        }
        responseJson = new JSONObject();

        responseJson.put(REQUEST_PLAYLIST,MusicLibrary.getPlaylists(playlistName,playlistIdentifier[1]));

        writer.println(responseJson);

        ServerApplication.logger.info(responseJson.toString());
    }

    public void updateToPlaylist(String command,String audioName, String username)
    {

        var inputIdentifier = audioName.split(PATH_SEPARATOR);

        if(inputIdentifier.length != 2)
        {
            responseJson.put(STATUS_CODE, "fail");

            responseJson.put(MESSAGE, "(ERROR) Not valid input");

            writer.println(responseJson);

            return;
        }

        // playlist name + audio name
        if(MusicLibrary.updatePlaylist(command,inputIdentifier[0], inputIdentifier[1], username) && command.equals(REQUEST_ADDTO_PLAYLIST))
        {
            responseJson.put(STATUS_CODE, "success");

            responseJson.put(MESSAGE, "Music added to playlist " + inputIdentifier[0]);
        }
        else if(MusicLibrary.updatePlaylist(command, inputIdentifier[0], inputIdentifier[1], username) && command.equals(REQUEST_REMOVE_FROM_PLAYLIST))
        {
            responseJson.put(STATUS_CODE, "success");

            responseJson.put(MESSAGE, "Music removed to playlist " + inputIdentifier[0]);
        }
        else
        {
            responseJson.put(STATUS_CODE, "fail");

            responseJson.put(MESSAGE, "You are not the owner of " + inputIdentifier[0]);
        }

        writer.println(responseJson);

        ServerApplication.logger.info(responseJson.toString());
    }

    public void removePlaylist(String playlistName, String username)
    {
        if(MusicLibrary.updatePlaylist(playlistName, username))
        {
            responseJson.put(STATUS_CODE, "success");

            responseJson.put(MESSAGE, "Successfully deleted playlist " + playlistName);
        }
        else
        {
            responseJson.put(STATUS_CODE, "fail");

            responseJson.put(MESSAGE, "You are not the owner of " + playlistName);
        }

        writer.println(responseJson);

        ServerApplication.logger.info(responseJson.toString());
    }

    public void addNewUser(String username, String password)
    {
        if(MusicLibrary.addUser(username,password))
        {
            responseJson.put(STATUS_CODE, "success");

            responseJson.put(MESSAGE, "user added Successfully");
        }
        else
        {
            responseJson.put(STATUS_CODE, "fail");

            responseJson.put(MESSAGE, "user already exists");
        }
        writer.println(responseJson);

        ServerApplication.logger.info(responseJson.toString());

    }

    public void validateUser(String username, String password)
    {
        if(MusicLibrary.validateUser(username,password))
        {
            responseJson.put(STATUS_CODE, "success");

            responseJson.put(MESSAGE, "login Successfully");
        }
        else
        {
            responseJson.put(STATUS_CODE, "fail");

            responseJson.put(MESSAGE, "Invalid credentials");
        }

        writer.println(responseJson);

        ServerApplication.logger.info(responseJson.toString());

    }
}
