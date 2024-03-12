package org.example.Server;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class ServerRequestHandler
{
    private final Socket clientSocket;

    private ObjectOutputStream response;

    private final AudioStreamer audioStreamer;

    ServerRequestHandler(Socket socket)
    {
        this.clientSocket = socket;

        audioStreamer = new AudioStreamer(clientSocket);

        try
        {
            this.response = new ObjectOutputStream(clientSocket.getOutputStream());
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
