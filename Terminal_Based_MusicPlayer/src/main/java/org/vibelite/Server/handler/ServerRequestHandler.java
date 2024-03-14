package org.vibelite.Server.handler;

import org.vibelite.Server.ServerApplication;

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
        } catch(IOException e)
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
        } catch(IOException e)
        {
            System.out.println("(Error) cannot send Library: " + e.getMessage());
        }
    }

    public void streamAudioFile(String audioName){
        audioStreamer.streamAudioToClient(audioName);
    }

    public void sendAudioFile(String audioName){

        try
        {
            response.writeObject(audioStreamer.sendAudioToClient(audioName).toString());
        } catch(IOException e)
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
    public void sendPlaylistName()
    {
        try
        {
            response.writeObject(ServerApplication.musicLibrary.getPlayListNames());
        } catch(IOException e)
        {
            System.out.println("(Error) cannot send Playlist: " + e.getMessage());
        }
    }

    public void sendPlaylist(String playlistName)
    {
        try
        {
            response.writeObject(ServerApplication.musicLibrary.getPlaylists(playlistName));
        } catch(IOException e)
        {
            System.out.println("(Error) cannot send Playlist: " + e.getMessage());
        }
    }

    public void addToPlaylist(String audioName){

        var inputIdentifier = audioName.split(" ");
        // playlist name + audio name
        ServerApplication.musicLibrary.addToPlaylist(inputIdentifier[0], inputIdentifier[1]);
    }

}
