package org.vibelite.client.eventdriven.ui;

import org.vibelite.client.eventdriven.connection.ClientSocket;
import static org.vibelite.client.eventdriven.utils.Constants.*;

import java.io.IOException;
import java.util.List;


public class LibraryUI
{
    private ClientSocket clientSocket;
    public LibraryUI(ClientSocket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public void library(String playerName) throws IOException, NullPointerException
    {
        while(true)
        {
            List<String> audioFiles;

            if(playerName.equals(LIBRARY_PLAYER_ID))
            {
                // list all files that are available in library;
                audioFiles = clientSocket.connect().requestLibrary();

                TerminalUI.menu(LIBRARY_PLAYER_ID);
                if(audioFiles.isEmpty())
                {
                    System.out.println("Library is empty!!");
                    return;
                }
            }
            else
            {
                // list all files that are available in selected playlist
                audioFiles = clientSocket.connect().playPlaylist(playerName);

                TerminalUI.menu(playerName);

                if(audioFiles.isEmpty())
                {
                    System.out.println("Playlist is empty!!");
                    return;
                }
                System.out.print("1) remove music from playlist"+ TAB );
            }

            System.out.println("0) EXIT " + playerName);

            // printing audio file name
            for(var audioFile : audioFiles)
            {
                System.out.println(audioFile.substring(0,audioFile.length()-4));
            }

            System.out.print("Enter the music name or command: ");

            var audioName = INPUT.nextLine();

            // exit from library
            if(audioName.equals("0"))
            {
                return;
            }

            // remove music from playlist
            else if(audioName.equals("1"))
            {
                System.out.print("Enter music name you want to remove from playlist: ");

                audioName = INPUT.nextLine();

                // checking if music(audio name) actually exists or not
                audioName = validateAudioName(audioName,audioFiles);

                if(!audioName.equals("0"))
                    if(!clientSocket.connect().requestUpdatePlaylist(REQUEST_REMOVE_FROM_PLAYLIST,playerName, audioName+".wav"))
                        return;
            }
            else
            {
                audioName = validateAudioName(audioName, audioFiles);


                //request audio file from server
                if(!audioName.equals("0"))
                {
                    clientSocket.connect().requestAudio(audioName + ".wav", playerName);
                }
            }
        }
    }

    public String validateAudioName(String audioName, List<String> audioFiles){
        // validate audio name
        while(!audioFiles.contains(audioName + ".wav"))
        {
            System.out.print("Please enter valid audio file name or 0 to exit: ");

            audioName = INPUT.nextLine();

            if(audioName.equals("0"))
                return audioName;
        }
        return audioName;
    }
}
