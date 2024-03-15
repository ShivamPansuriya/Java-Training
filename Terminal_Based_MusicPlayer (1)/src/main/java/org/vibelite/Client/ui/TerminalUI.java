package org.vibelite.Client.ui;

import org.vibelite.Client.handler.ClientRequestHandler;
import org.vibelite.Client.connection.ClientSocket;
import org.vibelite.Client.handler.PlaybackManager;
import static org.vibelite.Client.utils.Constants.*;

import javax.sound.sampled.Clip;
import java.util.Scanner;

public class TerminalUI
{
    private final ClientSocket clientSocket;

    private static PlaybackManager playbackManager;

    private final ClientRequestHandler clientRequestHandler;

    // used to decide whether to empty playing queue or not.
    private String previousPlayerName = LIBRARY_PLAYER_ID;

    public TerminalUI(ClientSocket clientSocket, ClientRequestHandler clientRequestHandler)
    {
        this.clientSocket = clientSocket;

        this.clientRequestHandler = clientRequestHandler;

        clientRequestHandler.setTerminalUI(this);

        playbackManager = new PlaybackManager();
    }

    public void start()
    {
        var playlistUI = new PlaylistUI(clientRequestHandler);

        var libraryUI = new LibraryUI(clientRequestHandler);

        while(true)
        {
            System.out.println("-------------------------------");
            System.out.println(TAB + TAB +TAB +"Menu");
            System.out.println("-------------------------------");

            var commandOption = "1) "+PLAYLIST_PLAYER_ID.toLowerCase()  + NEWLINE +"2) " + LIBRARY_PLAYER_ID.toLowerCase() + NEWLINE +"3) Upload music" + NEWLINE + "0) Exit from application" +NEWLINE+ "Enter Command:";

            System.out.print(commandOption);

            var command = INPUT.nextLine();

            switch(command)
            {
                case "1":
                    System.out.println();

                    playlistUI.playlist();

                    break;

                case "2":
                    System.out.println();

                    libraryUI.library(LIBRARY_PLAYER_ID);

                    break;

                case "3":
                    clientRequestHandler.uploadAudio();
                    break;

                case "0":
                    System.out.println("Exiting....");

                    clientRequestHandler.closeConnection();

                    clientSocket.disconnect();

                    return;

                default:
                    System.out.println("Enter correct option");

                    break;
            }
        }
    }

    public static void menu(String playerName){
        System.out.println("-------------------------------");
        System.out.println(TAB + TAB + TAB + playerName);
        System.out.println("-------------------------------");
    }

    public void AudioPlayerUI(Clip clip, String audioName, String playedFrom)
    {
        if(!previousPlayerName.equals(playedFrom))
        {
            playbackManager.resetQueue();
            previousPlayerName = playedFrom;
        }

        // to enable playbackManager to handle audio file
        playbackManager.setClip(clip);

        // set the name of current playing audio
        playbackManager.setAudio(audioName);

        System.out.println(NEWLINE + "Playing audio: " + playbackManager.getAudio());

        //display audio length in seconds
        System.out.println("Audio length: " + clip.getMicrosecondLength()/1000000 + "sec");

        var sc = new Scanner(System.in);

        clip.start();

        while(true)
        {
            System.out.println(NEWLINE + "Enter command: 1) Play music" + TAB + "2) Pause music" + TAB + "3) Set volume"+ TAB + "4) Previous music "+ TAB + "5) Next music"+ TAB +"6) Add to playlist" + TAB + "7)reset music" + TAB + "8) Download music" + TAB + "0) Close music" );

            var command = sc.nextLine();

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

                    command = INPUT.nextLine();

                    //check if the given input is valid playlist name
                    while(!clientRequestHandler.requestUpdatePlaylist(REQUEST_ADDTO_PLAYLIST,command,playbackManager.getAudio()))
                    {
                        System.out.print("enter correct playlist name or 0 to exit: ");

                        command = INPUT.nextLine();

                        if(command.equals("0"))
                            break;
                    }
                    break;

                case "7":
                    playbackManager.resetAudio();

                    break;

                case "8":
                    clientRequestHandler.downloadAudio(playbackManager.getAudio());

                    System.out.println("Download Successful");

                    break;

                case "0":
                    playbackManager.closeAudio();

                    return;

                default:
                    System.out.println("not valid");

                    break;
            }
        }
    }
}
