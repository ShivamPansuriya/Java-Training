package org.example.Client;

import javax.sound.sampled.Clip;
import java.security.PublicKey;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TerminalUI
{
    private final ClientSocket clientSocket;

    private  PlaybackManager playbackManager;

    private ClientRequestHandler clientRequestHandler;

    private final String newLine = "\n";

    private final String tab = "\t";

    private final Scanner input;

    TerminalUI(ClientSocket clientSocket, ClientRequestHandler clientRequestHandler)
    {
        this.clientSocket = clientSocket;

        this.clientRequestHandler = clientRequestHandler;

        this.input = new Scanner(System.in);

        playbackManager = new PlaybackManager();
    }

    public void start()
    {
        while(true)
        {
            String commandOption = "Entre Command:" + newLine +"1) Playlist"  + newLine +"2) Library" + newLine +"3) Exit from application";

            System.out.println(commandOption);

            String command = input.nextLine();

            switch(command)
            {
                case "1":
                    System.out.println("Playlist");

                    playlist();

                    break;

                case "2":
                    System.out.println("Library");

                    library();

                    break;

                case "3":
                    System.out.println("Exit");

                    clientRequestHandler.closeConnection();

                    clientSocket.disconnect();

                    return;

                default:
                    System.out.println("entre correct option");

                    break;
            }
        }
    }

    public void playlist()
    {
        System.out.println("Entre Command: 1) EXIT playlist"+ tab + "2) CREATE playlist" + tab + "3) WATCH playlist");

        var playlist = clientRequestHandler.requestPlaylist();

        if(playlist.isEmpty())
        {
            System.out.println("No Playlist available");
        }
        else
        {
            for(var playlistName : playlist.keySet())
            {
                System.out.println(playlistName);
            }
        }
        String command = input.nextLine();

        switch(command)
        {
            case "1":
                start();

            case "2":
                System.out.print("Entre Playlist name: ");

                String playlistName = input.nextLine();

                clientRequestHandler.createPlaylist(playlistName);

            case "3":
                System.out.print("Entre the music name: ");
        }


    }
    public void library()
    {
        System.out.println("Entre Command: 1) EXIT library");

        var audioFiles = clientRequestHandler.requestLibrary();

        for(var audioFile : audioFiles){
            System.out.println(audioFile);
        }
        System.out.println();
        System.out.print("Entre the music name: ");

        String audioName = input.nextLine();

        if(!audioName.equals("1"))
        {
            while(!audioFiles.contains(audioName+".wav"))
            {
                System.out.print("Please entre valid audio file name: ");

                audioName = input.nextLine();
            }

            clientRequestHandler.requestAudio(audioName+".wav", this);

            library();
        }
    }

    public void AudioPlayerUI(Clip clip, String audioName)
    {
        playbackManager.setClip(clip);
        playbackManager.setAudio(audioName);
        System.out.println("Playing audio: " + playbackManager.getAudio());
        Scanner sc = new Scanner(System.in);
        clip.start();

        // Start playing the audio
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        // Define the task to be executed
        Runnable task = ()-> playbackManager.closeAudio();

        // Schedule the task to run after a delay of audiolength seconds
        System.out.println(clip.getFrameLength());
        executorService.schedule(task, clip.getMicrosecondLength(), TimeUnit.MICROSECONDS);

        // Shutdown the executor service after the task has been scheduled
        executorService.shutdown();

        while(true)
        {
            System.out.println();
            System.out.println("Entre command: 1) Play music" + tab + "2) Pause music" + tab + "3) Set volume"+ tab + "4) Previous music "+ tab + "5) Close music");
            String command = sc.nextLine();

            switch(command)
            {
                case "1":
                    playbackManager.playAudio();
                    break;

                case "2":
                    playbackManager.pauseAudio();
                    break;

                case "3":
                    playbackManager.setVolume();
                    break;

                case "4":
                    clientRequestHandler.requestAudio(playbackManager.getPreviousAudio(),this);
                    return;

                case "5":
                    playbackManager.closeAudio();
                    return;

                default:
                    System.out.println("not valid");
                    break;
            }
        }
    }
}
