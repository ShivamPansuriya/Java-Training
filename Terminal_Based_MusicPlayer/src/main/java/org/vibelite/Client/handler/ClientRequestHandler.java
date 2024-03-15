package org.vibelite.Client.handler;

import org.vibelite.Client.ui.TerminalUI;
import static org.vibelite.Client.utils.Constants.*;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ClientRequestHandler
{
    private PrintWriter request;

    private TerminalUI terminalUI;

    private ObjectInputStream response;

    private AudioStreamer audioStreamer;

    public ClientRequestHandler(Socket clientSocket)
    {
        audioStreamer = new AudioStreamer(clientSocket);

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
        List<String> audioFiles = new ArrayList<>();
        //sending request type
        request.println(REQUEST_LIBRARY);
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

    public void requestNextAudio(String currentAudio, String playedFrom)
    {

        int size,currentIndex, nextIndex;

        if(playedFrom.equals(LIBRARY_PLAYER_ID)){

            var audioFiles = requestLibrary();
            //get size of library
            size= audioFiles.size();

            //finding index of current playing audio
            currentIndex = audioFiles.indexOf(currentAudio);

            //determining next audio index
            nextIndex = (currentIndex < size-1)? ++currentIndex : 0;

            //finding next audio name
            String audioName = audioFiles.get(nextIndex);

            //request server for audio file
            requestAudio(audioName, playedFrom);
        }
        else
        {
            var playlistTracks = playPlaylist(playedFrom);
            //get size of playlist
            size = playlistTracks.size();

            //finding index of current playing audio
            currentIndex = playlistTracks.indexOf(currentAudio);

            //determining next audio index
            nextIndex = (currentIndex < size-1)? ++currentIndex : 0;

            //finding next audio name
            String audioName = playlistTracks.get(nextIndex);

            //request server for audio file
            requestAudio(audioName, playedFrom);
        }
    }

    public void requestAudio(String audioName, String playedFrom)
    {
        //sending request type
        request.println(REQUEST_AUDIO_STREAMING);

        //sending actual request
        request.println(audioName);

        //start playing audio
        audioStreamer.receiveAndPlayAudio(terminalUI , audioName, playedFrom);

    }

    public void uploadAudio()
    {
        System.out.println("Enter audio file path: ");

        Scanner INPUT = new Scanner(System.in);

        var filePath = INPUT.next();

        //splitting file path in directories to get filename
        var directories = filePath.trim().split(PATH_SEPARATOR);

        //extracting file name from file path array
        var fileName = directories[directories.length -1];

        //request library to check if audio you are trying is upload is already present or not
        if(requestLibrary().contains(fileName)){
            System.out.println("File already exists");
            return;
        }

        // checking if file actually exists or not and whether it is executable
        if(Files.exists(Paths.get(filePath)) && fileName.contains(FILE_IDENTIFIER))
        {
            try
            {
                //MIME type output for .wav file  = audio/wav
                var fileType = Files.probeContentType(Path.of(filePath)).split(PATH_SEPARATOR,2);

                if(!(fileType[0].equals("audio") && fileType[1].contains("wav")))
                {
                    System.out.println("only supported wav files you are trying to upload: " + fileType[1] +" " + fileType[0]);

                    return;
                }
            }
            catch (IOException e)
            {
                System.out.println("(ERROR) File with improper file format");
            }
        }
        else
        {
            System.out.println("file not found");
            return;
        }

        //sending request type
        request.println(REQUEST_UPLOAD);

        //sending audio file in json string
        request.println(audioStreamer.sendAudio(filePath, fileName).toString());

        System.out.println("success");

    }

    public void downloadAudio(String audioName)
    {
        //sending request type
        request.println(REQUEST_DOWNLOAD);

        //sending audio name to server that user wants to download
        request.println(audioName);

        //receive json object
        try
        {
            //receiving json object of audio file
            String audioJSON = (String) response.readObject();

            //converting json object to audio file
            audioStreamer.downloadAudio(audioJSON);
        }
        catch(IOException e)
        {
            System.out.println("Server down");

            System.out.println("Retrying to establish connection");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("(ERROR) cannot assign object to string type:");
        }

    }

    public void createPlaylist(String playlistName)
    {
        //sending request type
        request.println(REQUEST_CREATE_PLAYLIST);

        //sending actual request
        request.println(playlistName);
    }

    public List<String> requestPlaylist()
    {

        //sending request type
        request.println(REQUEST_PLAYLIST_NAMES);

        List<String> playlistNames = new ArrayList<>();
        try
        {
            //response from server
            playlistNames = (List<String>) response.readObject();


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

        return playlistNames;
    }

    public boolean requestUpdatePlaylist(String requestType, String playlistName, String audioName)
    {
        //request for available playlists
        var playlistNames = requestPlaylist();

        //check whether playlist exist or not
        if(playlistNames.contains(playlistName))
        {
            request.println(requestType);

            request.println(playlistName+ PATH_SEPARATOR +audioName);

            try
            {
                System.out.println((String) response.readObject());
            }
            catch(IOException e)
            {
                System.out.println("Server down");

                System.out.println("Retrying to establish connection");

            }
            catch(ClassNotFoundException e)
            {
                System.out.println("(ERROR) cannot print object");
            }

            return true;
        }
        else
        {
            System.out.print("No such playlist exist ");
        }

        return false;
    }

    public boolean requestUpdatePlaylist(String playlistName)
    {
        //request for available playlists
        var playlistNames = requestPlaylist();

        //check whether playlist exist or not
        if(playlistNames.contains(playlistName))
        {
            request.println(REQUEST_REMOVE_PLAYLIST);

            request.println(playlistName);

            try
            {
                System.out.println((String) response.readObject());
            }
            catch(IOException e)
            {
                System.out.println("Server down");

                System.out.println("Retrying to establish connection");

            }
            catch(ClassNotFoundException e)
            {
                System.out.println("(ERROR) cannot print object");
            }
            return true;
        }
        else
        {
            System.out.println("No such playlist exists");
        }
        return false;
    }

    public List<String> playPlaylist(String playlistName)
    {
        //request type
        request.println(REQUEST_PLAYLIST);

        //sending actual request
        request.println(playlistName);

        List<String> playlistTracks = new ArrayList<>();
        try
        {
            //response from server
            playlistTracks = (List<String>) response.readObject();

        }
        catch(IOException e)
        {
            System.out.println("(ERROR) Cannot read file: ");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("(ERROR) incompatible object types");
        }
        return playlistTracks;
    }
    public void closeConnection(){

        // because closing the connection needs time and without it server application reader thread was showing exception(never stops programing)
        request.println(REQUEST_EXIT);

    }

}
