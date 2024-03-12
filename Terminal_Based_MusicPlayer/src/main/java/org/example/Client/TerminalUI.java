package org.example.Client;

import javax.sound.sampled.Clip;
import java.util.Scanner;

public class TerminalUI
{
    private final ClientSocket clientSocket;

    private static  PlaybackManager playbackManager;

    private ClientRequestHandler clientRequestHandler;

    private final String NEWLINE = "\n";

    private final String TAB = "\t";

    private final Scanner input;

    TerminalUI(ClientSocket clientSocket, ClientRequestHandler clientRequestHandler)
    {
        this.clientSocket = clientSocket;

        this.clientRequestHandler = clientRequestHandler;

        clientRequestHandler.setTerminalUI(this);

        this.input = new Scanner(System.in);

        playbackManager = new PlaybackManager();
    }

    public void start()
    {
        while(true)
        {
            menu("MENU");
            String commandOption = "1) Playlist"  + NEWLINE +"2) Library" + NEWLINE +"3) Exit from application" +NEWLINE+ "Enter Command:";

            System.out.print(commandOption);

            String command = input.nextLine();

            switch(command)
            {
                case "1":
                    System.out.println("Playlist");

                    playlist();

                    break;

                case "2":
                    System.out.println();

                    library();

                    break;

                case "3":
                    System.out.println("Exit");

                    clientRequestHandler.closeConnection();

                    clientSocket.disconnect();

                    return;

                default:
                    System.out.println("Enter correct option");

                    break;
            }
        }
    }

    public void menu(String heading){
        System.out.println("-------------------------------");
        System.out.println(TAB + TAB +TAB +heading);
        System.out.println("-------------------------------");
    }

    public void playlist()
    {

        var playlist = clientRequestHandler.requestPlaylist();

        menu("PLAYLIST");

        System.out.println("1) Exit playlist"+ TAB +"2) Show playlists"+ TAB + "3) Create playlist" + TAB + "4) Listen playlist");

        System.out.print("Enter Command: ");

        String command = input.nextLine();

        switch(command)
        {
            case "1":
                return;

            case "2":
                if(playlist.isEmpty())
                {
                    System.out.println(NEWLINE + "No Playlist available");
                }
                else
                {
                    System.out.println(NEWLINE + "Available playlist are:");

                    for(var playlistName : playlist)
                    {
                        System.out.println(playlistName);
                    }
                }
                break;

            case "3":
                System.out.print("Enter Playlist name: ");

                String playlistName = input.nextLine();

                //validate if playlist is already available or not
                while(playlist.contains(playlistName)){
                    System.out.print("Playlist already available please entre another name to create playlist: ");

                    playlistName = input.nextLine();
                }

                clientRequestHandler.createPlaylist(playlistName);

                break;

            case "4":
                System.out.print("Enter the Playlist name you want to play: ");

                playlistName = input.nextLine();

                //validate if playlist is available or not
                while(!playlist.contains(playlistName))
                {

                    System.out.print("Playlist not found please enter correct name or 1 to exit: ");

                    playlistName = input.nextLine();
                    if(playlistName.equals("1")){
                        playlist();
                    }
                }

                // start playing playlist
                clientRequestHandler.playPlaylist(playlistName);

                break;

            default:
                System.out.println("please Enter valid command!!!!");

                break;
        }

        playlist();

    }
    public void library()
    {
        // list all files that are available in library;
        var audioFiles = clientRequestHandler.requestLibrary();

        for(var audioFile : audioFiles)
        {
            System.out.println(audioFile);
        }

        menu("LIBRARY");

        System.out.println("1) EXIT library");

        System.out.print("Enter the music name or 1 to exit: ");

        String audioName = input.nextLine();

        // exit from library
        if(!audioName.equals("1"))
        {
            // validate audio name
            while(!audioFiles.contains(audioName+".wav"))
            {
                System.out.print("Please Enter valid audio file name: ");

                audioName = input.nextLine();
            }

            //request audio file from server
            clientRequestHandler.requestAudio(audioName+".wav" , "library");

            library();
        }
    }

    public void AudioPlayerUI(Clip clip, String audioName, String playedFrom)
    {
        // to enable playbackManager to handle audio file
        playbackManager.setClip(clip);

        // set the name of current playing audio
        playbackManager.setAudio(audioName);

        System.out.println("Playing audio: " + playbackManager.getAudio());

        //display audio length in seconds
        System.out.println(clip.getMicrosecondLength()/1000000);

        Scanner sc = new Scanner(System.in);

        clip.start();

        while(true)
        {
            System.out.println(NEWLINE + "Enter command: 1) Play music" + TAB + "2) Pause music" + TAB + "3) Set volume"+ TAB + "4) Previous music "+ TAB + "5) Next music"+ TAB +"6) Add to playlist" + TAB + "7)close music" );

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
                    clientRequestHandler.requestAudio(playbackManager.getPreviousAudio(),playedFrom);
                    return;

                case "5":
                    playbackManager.closeAudio();
                    clientRequestHandler.requestNextAudio(playbackManager.getAudio(), playedFrom);
                    return;

                case "6":
                    System.out.print("Enter playlist name: ");

                    command = input.nextLine();

                    //check if the given input is valid playlist name
                    while(!clientRequestHandler.requestAddToPlaylist(command,playbackManager.getAudio()))
                    {
                        System.out.print("enter correct playlist name: ");

                        command = input.nextLine();
                    }
                    break;

                case "7":
                    playbackManager.closeAudio();
                    return;

                default:
                    System.out.println("not valid");
                    break;
            }
        }
    }
}
