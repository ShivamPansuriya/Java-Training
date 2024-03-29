package org.vibelite.client.eventdriven.ui;

import org.json.JSONException;
import org.vibelite.client.eventdriven.ClientApplication;
import org.vibelite.client.eventdriven.connection.ClientSocket;
import org.vibelite.client.eventdriven.handler.PlaybackManager;

import static org.vibelite.client.eventdriven.utils.Constants.*;

import javax.sound.sampled.Clip;
import java.io.IOException;
import java.util.Scanner;


public class TerminalUI
{
    protected final ClientSocket clientSocket;

    protected static String user;
    private static final PlaybackManager playbackManager;

    // used to decide whether to empty playing queue or not.
    private String previousPlayerName = LIBRARY_PLAYER_ID;

    LibraryUI libraryUI;

    PlaylistUI playlistUI;

    static
    {
        playbackManager = new PlaybackManager();
    }

    public TerminalUI()
    {
        this.clientSocket = new ClientSocket(this);
    }

    public void start()
    {
        libraryUI = new LibraryUI();

        playlistUI = new PlaylistUI();

        while(true)
        {
            System.out.println("--------------------------------");
            System.out.println("\tVIBELITE");
            System.out.println("--------------------------------");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try
            {

                Scanner input = new Scanner(System.in);

                var choice = input.nextLine();

                var username = "";

                var password = "";

                switch(choice)
                {
                    // LOGIN
                    case "1":
                        menu("LOGIN");

                        System.out.print("Enter username: ");

                        username = input.next();

                        System.out.print("Enter password: ");

                        password = input.next();

                        if(clientSocket.connect().authenticateUser(LOGIN, username, password))
                        {
                            user = username;

                            ClientApplication.logger.info(user, "{} logged in");

                            startTerminal();

                        }

                        break;

                    case "2":
                        menu("REGISTER");

                        System.out.print("Enter username: ");

                        username = input.next();

                        if(username.length() >= 6)
                        {
                            System.out.print("Enter password: ");

                            password = input.next();

                            if(password.length() >= 6)
                            {
                                clientSocket.connect().authenticateUser(REGISTER, username, password);
                            }
                            else
                            {
                                System.out.println("Invalid Input");
                                ClientApplication.logger.info(user, "{} password length less than 6");
                            }

                        }
                        else
                        {
                            System.out.println("Invalid Input");
                            ClientApplication.logger.info(user, "{} username length less than 6");
                        }
                        break;

                    case "0":

                        System.out.println("Exiting client...");

                        return;

                    default:

                        System.out.println("Enter correct option");

                }

            } catch(JSONException jsonException)
            {
                System.out.println("improper json object");
                ClientApplication.logger.error("improper json object");

            } catch(IOException e)
            {
                System.out.println("cannot able to connect server");

                ClientApplication.logger.error("cannot able to connect server");

                break;
            }
        }
    }

    public void startTerminal() throws IOException
    {
        while(true)
        {
            menu("MENU");

            var commandOption = "1) " + PLAYLIST_PLAYER_ID.toLowerCase() + NEWLINE + "2) " + LIBRARY_PLAYER_ID.toLowerCase() + NEWLINE + "3) Upload music" + NEWLINE + "0) Exit from application" + NEWLINE + "Enter Command:";

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
                    var library = clientSocket.connect().requestLibrary();
                    clientSocket.connect().uploadAudio(library);
                    break;

                case "0":
                    System.out.println("Logging out....");

                    clientSocket.connect().closeConnection();

                    clientSocket.disconnect();

                    return;

                default:
                    System.out.println("Enter correct option");

                    break;
            }
        }
    }

    public void menu(String playerName)
    {
        System.out.println("-------------------------------");
        System.out.println(TAB + TAB + TAB + playerName);
        System.out.println("-------------------------------");
    }

    public void audioPlayerUI(Clip clip, String audioName, String playedFrom) throws IOException
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
        System.out.println("Audio length: " + clip.getMicrosecondLength() / 1000000 + "sec");

        var sc = new Scanner(System.in);

        clip.start();

        while(true)
        {
            System.out.println(NEWLINE + "Enter command: 1) Play music" + TAB + "2) Pause music" + TAB + "3) Set volume" + TAB + "4) Previous music " + TAB + "5) Next music" + TAB + "6) Add to playlist" + TAB + "7)reset music" + TAB + "8) Download music" + TAB + "0) Close music");

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
                    clientSocket.connect().requestAudio(playbackManager.getPreviousAudio(), playedFrom);

                    return;

                case "5":
                    playbackManager.closeAudio();

                    var nextAudioName = clientSocket.connect().requestNextAudio(playbackManager.getAudio(), playedFrom);

                    if(nextAudioName == null)
                    {
                        return;
                    }
                    //request server for audio file
                    clientSocket.connect().requestAudio(nextAudioName, playedFrom);

                    return;

                case "6":
                    System.out.print("Enter playlist name: ");

                    command = INPUT.nextLine();

                    //request for available playlists
                    var playlistNames = clientSocket.connect().requestPlaylist();

                    //check whether playlist exist or not
                    if(playlistNames.contains(command + ":" + user))
                    {
                        //check if the given input is valid playlist name
                        while(!clientSocket.connect().requestUpdatePlaylist(REQUEST_ADDTO_PLAYLIST, command, playbackManager.getAudio(), user))
                        {
                            System.out.print("enter correct playlist name or 0 to exit: ");

                            command = INPUT.nextLine();

                            if(command.equals("0"))
                                break;
                        }
                    }
                    else
                    {
                        System.out.print("No such playlist exist ");
                    }
                    break;

                case "7":
                    playbackManager.resetAudio();

                    break;

                case "8":
                    clientSocket.connect().downloadAudio(playbackManager.getAudio());

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
