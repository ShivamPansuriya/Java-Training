package org.vibelite.Client;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ClientRequestHandler
{
    private PrintWriter request;

    private final Socket clientSocket;

    private TerminalUI terminalUI;

    private ObjectInputStream response;

    private List<String> audioFiles;

    private List<String> playlist;


    private List<String> availablePlaylist;

    private AudioPlayer audioPlayer;

    ClientRequestHandler(Socket clientSocket)
    {
        this.clientSocket =clientSocket;

        audioPlayer = new AudioPlayer(clientSocket);

        audioFiles = new ArrayList<>();

        availablePlaylist = new ArrayList<>();

        try
        {
            this.request = new PrintWriter(clientSocket.getOutputStream() , true);

            this.response = new ObjectInputStream(clientSocket.getInputStream());
        } catch(IOException e)
        {
            System.out.println("(ERROR) cannot read or write from socket stream: "+ e.getMessage());
        }
    }

    public void setTerminalUI(TerminalUI terminalUI)
    {
        this.terminalUI = terminalUI;
    }

    public List<String> requestLibrary()
    {
        //sending request type
        request.println("library");
        try
        {
            audioFiles = (List<String>) response.readObject();
        }
        catch(IOException e)
        {
            System.out.println("Server down");

            System.out.println("Retrying to establish connection");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("(ERROR) cannot assign object of other type:");
        }

        return audioFiles;
    }

    public void requestNextAudio(String currentAudio, String playedFrom){
        int size,currentIndex, nextIndex;

        if(playedFrom.equals("playlist")){
            //get size of playlist
            size = playlist.size();

            //finding index of current playing audio
            currentIndex = playlist.indexOf(currentAudio);

            //determining next audio index
            nextIndex = (currentIndex < size-1)? ++currentIndex : 0;

            String audioName = playlist.get(nextIndex);

            requestAudio(audioName, playedFrom);
        }
        else
        {
            size= audioFiles.size();

            currentIndex = audioFiles.indexOf(currentAudio);

            nextIndex = (currentIndex < size-1)? ++currentIndex : 0;

            String audioName = audioFiles.get(nextIndex);

            requestAudio(audioName, playedFrom);
        }
    }

    public void requestAudio(String audioName, String playedFrom)
    {
        //sending request type
        request.println("audioFile");

        //sending actual request
        request.println(audioName);

        audioPlayer.receiveAndPlayAudio(terminalUI , audioName, playedFrom);

    }

    public void uploadAudio(){
        System.out.println("Enter audio file path: ");
        Scanner INPUT = new Scanner(System.in);

        var filePath = INPUT.next();

        var directories = filePath.trim().split("/");

        var fileName = directories[directories.length -1];

        if(Files.exists(Paths.get(filePath)) && fileName.contains("."))
        {
            try
            {
                var fileType = Files.probeContentType(Path.of(filePath)).split("/",2);

                if(!(fileType[0].equals("audio") && fileType[1].contains("wav")))
                {
                    System.out.println("only supported wav files you are trying to upload: " + fileType[1] +" " + fileType[0]);

                    return;
                }
            } catch (IOException e)
            {
                System.out.println("(ERROR) File with improper file format");
            }
        } else {
            System.out.println("file not found");
            return;
        }

        request.println("uploadAudio");

        request.println(fileName);

        audioPlayer.sendAudio((filePath));
    }

    public void createPlaylist(String playlistName)
    {
        //sending request type
        request.println("createPlaylist");

        //sending actual request
        request.println(playlistName);
    }

    public List<String> requestPlaylist(){

        //sending request type
        request.println("getPlaylistName");

        try
        {
            //response from server
            availablePlaylist = (List<String>) response.readObject();


        } catch(IOException e)
        {
            System.out.println("Server down");

            System.out.println("Retrying to establish connection");

        }
        catch(ClassNotFoundException e)
        {
            System.out.println("(ERROR) cannot assign object of other type:");
        }

        return availablePlaylist;
    }

    public boolean requestAddToPlaylist(String playlistName, String audioName)
    {
        //request for available playlists
        var playlistNames = requestPlaylist();

        //check whether playlist exist or not
        if(playlistNames.contains(playlistName))
        {
            request.println("addToPlaylist");

            request.println(playlistName+" "+audioName);

            return true;
        }
        else
        {
            System.out.print("No such playlist exist ");
        }

        return false;

    }

    public void playPlaylist(String playlistName)
    {
        //request type
        request.println("getPlaylist");

        //sending actual request
        request.println(playlistName);

        try
        {
            //response from server
            playlist = (List<String>) response.readObject();

            if(playlist.size()==0)
            {
                System.out.println("Playlist is Empty");
            }
            else
            {
                requestAudio(playlist.get(0), "playlist");
            }


        } catch(IOException e)
        {
            System.out.println("(ERROR) Cannot read file: ");
        } catch(ClassNotFoundException e)
        {
            System.out.println("(ERROR) incompatible object types");
        }
    }
    public void closeConnection(){

        // because closing the connection needs time and without it server application reader thread was showing exception(never stops programing)
        request.println("exit");

    }

}
