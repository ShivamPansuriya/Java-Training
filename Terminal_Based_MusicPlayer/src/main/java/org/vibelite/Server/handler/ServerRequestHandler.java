package org.vibelite.Server.handler;

import org.vibelite.Server.ServerApplication;

import java.io.*;
import java.net.Socket;
import java.util.List;

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
        List<String> files = ServerApplication.musicLibrary.getAudioFilename();

        try
        {
            response.writeObject(files);
        } catch(IOException e)
        {
            System.out.println("(Error) cannot send Library: " + e.getMessage());
        }
    }

    public void sendAudioFile(String audioName){
        audioStreamer.sendAudioToClient(audioName);
    }

    public void createPlaylist(String playlistName)
    {
        ServerApplication.musicLibrary.createPlaylist(playlistName);
    }

    public void saveAudio(String audioName)
    {
        audioStreamer.saveAudioFromClient(audioName);
    }
    public void sendPlaylistName()
    {
        try
        {
            response.writeObject(ServerApplication.musicLibrary.getPlayListName());
        } catch(IOException e)
        {
            System.out.println("(Error) cannot send Playlist: " + e.getMessage());
        }
    }

    public void sendPlaylist(String playlistName)
    {
        try
        {
            response.writeObject(ServerApplication.musicLibrary.getPlaylist(playlistName));
        } catch(IOException e)
        {
            System.out.println("(Error) cannot send Playlist: " + e.getMessage());
        }
    }

    public void addToPlaylist(String audioName){
        String[] inputIdentifier = audioName.split(" ");
        ServerApplication.musicLibrary.addToPlaylist(inputIdentifier[0], inputIdentifier[1]);
    }

}
