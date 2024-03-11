package org.example.Server;

import java.io.*;
import java.net.Socket;
import java.util.List;

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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

    public void sendAudioFile(String audioName){
        audioStreamer.sendAudioToClient(audioName);
    }

    public void createPlaylist(String playlistName)
    {
        ServerApplication.musicLibrary.createPlaylist(playlistName);
    }

    public void sendPlaylist()
    {
        try
        {
            response.writeObject(ServerApplication.musicLibrary.getPlayList());
        } catch(IOException e)
        {
            System.out.println("Playlist cannot be send!!");
        }
    }

}
