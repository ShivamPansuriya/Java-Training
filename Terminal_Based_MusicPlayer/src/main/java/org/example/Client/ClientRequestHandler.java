package org.example.Client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientRequestHandler
{
    private final PrintWriter request;

    private final Socket clientSocket;

    private final ObjectInputStream response;

    private List<String> audioFiles;

    private AudioPlayer audioPlayer;

    ClientRequestHandler(Socket clientSocket)
    {
        this.clientSocket =clientSocket;

        audioPlayer = new AudioPlayer(clientSocket);

        audioFiles = new ArrayList<>();
        try
        {
            this.request = new PrintWriter(clientSocket.getOutputStream() , true);

            this.response = new ObjectInputStream(clientSocket.getInputStream());

        } catch(IOException e)
        {
            throw new RuntimeException(e);
        }


    }

    public List<String> requestLibrary(){
        request.println("library");
        try
        {
            audioFiles = (List<String>) response.readObject();

        } catch (EOFException e) {
            // Handle EOFException gracefully, perhaps by logging an error message
            System.err.println("Unexpected end of stream while reading audio files");

        }
        catch(IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }

        return audioFiles;
    }

    public void requestAudio(String audioName, TerminalUI terminalUI)
    {
            request.println("audioFile");

            request.println(audioName);

        audioPlayer.receiveAndPlayAudio(terminalUI , audioName);

        System.out.println("audio complete");

    }

    public void createPlaylist(String playlistName){
        request.println("createPlaylist");

        request.println(playlistName);
    }

    public Map<String,List<String>> requestPlaylist(){
        request.println("getPlaylist");
        Map<String, List<String>> availablePlaylist;
        try
        {
            availablePlaylist = (Map<String, List<String>>)response.readObject();
        } catch(IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        return availablePlaylist;
    }
    public void closeConnection(){

        // because closing the connection needs time and without it server application reader thread was showing exception(never stops programing)
        request.println("exit");

    }

}
