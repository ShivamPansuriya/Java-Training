package org.example.Client;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientRequestHandler
{
    private final PrintWriter request;

    private final ObjectInputStream response;

    private List<String> audioFiles;

    private AudioPlayer audioPlayer;

    ClientRequestHandler(Socket clientSocket)
    {
        audioPlayer = new AudioPlayer(clientSocket);
        try
        {
            this.request = new PrintWriter(clientSocket.getOutputStream() , true);

            this.response = new ObjectInputStream(clientSocket.getInputStream());

        } catch(IOException e)
        {
            throw new RuntimeException(e);
        }


    }

    public void requestLibrary(){
        request.println("library");

        try
        {
            audioFiles = (List<String>) response.readObject();
            for(var audioName : audioFiles)
            {
                System.out.println(audioName);
            }
        } catch(IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }

    }

    public boolean requestAudio(String audioName)
    {
        audioName = audioName+".wav";
        if(audioFiles.contains(audioName))
        {
            request.println("audioFile");

            request.println(audioName);
        }
        else
        {
            return false;
        }

        audioPlayer.receiveAndPlay();
        return true;

    }

    public void closeConnection(){

        // because closing the connection needs time and without it server application reader thread was showing exception(never stops programing)
        request.println("exit");
        try
        {
            Thread.sleep(2000);
        } catch(InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

}
