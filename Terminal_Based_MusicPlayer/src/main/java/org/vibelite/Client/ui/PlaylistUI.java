package org.vibelite.Client.ui;

import org.vibelite.Client.handler.ClientRequestHandler;
import static org.vibelite.Client.utils.Constants.*;
public class PlaylistUI
{
    private final ClientRequestHandler clientRequestHandler;

    PlaylistUI(ClientRequestHandler clientRequestHandler)
    {

        this.clientRequestHandler = clientRequestHandler;
    }

    public void playlist()
    {
        while(true)
        {
            var playlist = clientRequestHandler.requestPlaylist();

//            Scanner input = new Scanner(System.in);
            System.out.println("-------------------------------");
            System.out.println(TAB + TAB +TAB +"PLAYLIST");
            System.out.println("-------------------------------");

            System.out.println("1) Show playlists" + TAB + "2) Create playlist" + TAB + "3) Listen playlist" + TAB + "0) Exit playlist");

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
                    while(playlist.contains(playlistName))
                    {
                        System.out.print("Playlist already available please entre another name to create playlist: ");

                        playlistName = INPUT.nextLine();
                    }

                    clientRequestHandler.createPlaylist(playlistName);

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

                    // start playing playlist
                    if(!playlistName.equals("0"))
                        clientRequestHandler.playPlaylist(playlistName);

                    break;

                default:
                    System.out.println("please Enter valid command!!!!");

                    break;
            }
        }
    }
}
