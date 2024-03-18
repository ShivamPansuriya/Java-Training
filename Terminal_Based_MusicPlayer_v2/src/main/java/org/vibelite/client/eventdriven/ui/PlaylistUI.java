package org.vibelite.client.eventdriven.ui;

import org.vibelite.client.eventdriven.connection.ClientSocket;
import static org.vibelite.client.eventdriven.utils.Constants.*;

import java.io.IOException;

public class PlaylistUI extends TerminalUI
{
    public void playlist() throws IOException, NullPointerException
    {
        while(true)
        {
            var playlist = clientSocket.connect().requestPlaylist();

            menu(PLAYLIST_PLAYER_ID);

            System.out.println("1) Show playlists" + TAB + "2) Create playlist" + TAB + "3) Listen playlist" + TAB+ "4) Delete playlist"+ TAB + "0) Exit playlist");

            System.out.print("Enter Command: ");

            var command = INPUT.nextLine();

            switch(command)
            {
                case "0":
                    return;

                case "1":
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

                case "2":
                    System.out.print("Enter Playlist name: ");

                    var playlistName = INPUT.nextLine();

                    //validate if playlist is already available or not
                    while(playlist.contains(playlistName) || playlistName.contains(PATH_SEPARATOR))
                    {
                        System.out.print("Playlist already available or '/' is not allowed"+NEWLINE+"please enter another name to create playlist: ");

                        playlistName = INPUT.nextLine();
                    }

                    clientSocket.connect().createPlaylist(playlistName);

                    break;

                case "3":
                    System.out.print("Enter the Playlist name you want to play: ");

                    playlistName = INPUT.nextLine();

                    //validate if playlist is available or not
                    while(!playlist.contains(playlistName))
                    {

                        System.out.print("Playlist not found please enter correct name or 0 to exit: ");

                        // giving option to user to exit from listening playlist
                        playlistName = INPUT.nextLine();

                        if(playlistName.equals("0"))
                        {
                            break;
                        }
                    }

                    if(playlistName.equals("0"))
                        break;

                    LibraryUI libraryUI = new LibraryUI();

                    libraryUI.library(playlistName);

                    break;

                case "4":
                    System.out.print("Enter playlist name you want to delete: ");

                    playlistName = INPUT.nextLine();

                    playlist = clientSocket.connect().requestPlaylist();

                    //validate if playlist is already available or not
                    if(playlist.contains(playlistName))
                    {
                        while(!clientSocket.connect().requestUpdatePlaylist(playlistName))
                        {
                            System.out.print("enter correct playlist name or 0 to exit: ");

                            command = INPUT.nextLine();

                            if(command.equals("0"))
                                break;
                        }
                    }
                    else
                    {
                        System.out.println("No such playlist exists");
                    }
                    break;

                default:
                    System.out.println("please Enter valid command!!!!");

                    break;
            }
        }
    }
}
