package org.vibelite.Client.ui;

import org.vibelite.Client.handler.ClientRequestHandler;

import java.util.ArrayList;
import java.util.List;

import static org.vibelite.Client.utils.Constants.*;

public class LibraryUI
{
    private final ClientRequestHandler clientRequestHandler;

    LibraryUI(ClientRequestHandler clientRequestHandler)
    {
        this.clientRequestHandler = clientRequestHandler;
    }

    public void library(String playerName)
    {
        while(true)
        {
            List<String> audioFiles;

            if(playerName.equals(LIBRARY_PLAYER_ID))
            {
                // list all files that are available in library;
                audioFiles = clientRequestHandler.requestLibrary();

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
                audioFiles = clientRequestHandler.playPlaylist(playerName);

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
                    if(!clientRequestHandler.requestUpdatePlaylist(REQUEST_REMOVE_FROM_PLAYLIST,playerName, audioName+".wav"))
                        return;
            }
            else
            {
                audioName = validateAudioName(audioName, audioFiles);

                //request audio file from server
                if(!audioName.equals("0"))
                    clientRequestHandler.requestAudio(audioName + ".wav", playerName);
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
