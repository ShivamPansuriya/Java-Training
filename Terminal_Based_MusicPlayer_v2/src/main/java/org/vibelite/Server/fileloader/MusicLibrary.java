package org.vibelite.Server.fileloader;

import static org.vibelite.Server.utils.Constants.*;
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class MusicLibrary
{

    // TODO :- remove redundant data structure : Done
    private static final Map<String, File> audioFileMapper;

    private static final Map<String, Set<String>> playlistMapper;

    static
    {
        //making arraylist(will store relative FILE path) of audio file that has .mp3 and .wav files
        // TODO - make constant for current_directory and path separator : Done
        //todo - make constant of .mp3 and .wav : Done
        // TODO - make static block : Done

        playlistMapper = new ConcurrentHashMap<>();

        audioFileMapper = new ConcurrentHashMap<>();

        var audioDirectory = new File(AUDIO_DIRECTORY);

        var audioFiles = Arrays.stream(audioDirectory.listFiles((dir, name) -> name.endsWith(WAV_EXTENSION))).toList();

        for (var file : audioFiles)
        {
            audioFileMapper.put(file.getName(), file);
        }
        System.out.println("Done");
    }
    public static void createPlaylist(String playlistName)
    {
        playlistMapper.put(playlistName,new HashSet<>());
    }

    public static List<String> getPlayListNames()
    {
        return playlistMapper.keySet().stream().toList();
    }

    public static List<String> getPlaylists(String playlistName)
    {
        return playlistMapper.get(playlistName).stream().toList();
    }

    public static String updatePlaylist(String command,String playlistName, String audioName)
    {
        var message = "";
        // TODO - check if object is null or not before performing operation
        if(command.equals(REQUEST_ADDTO_PLAYLIST))
        {
            playlistMapper.get(playlistName).add(audioName);

            message = "Music added to playlist ";
        }
        else if(command.equals(REQUEST_REMOVE_FROM_PLAYLIST))
        {
            playlistMapper.get(playlistName).remove(audioName);

            message = "Music removed from playlist ";
        }
        return  message;
    }

    public static void updatePlaylist(String playlistName)
    {
        playlistMapper.remove(playlistName);
    }

    public static void updateAudioFiles(String audioName)
    {
        //when user upload audio file we will update audioFileMapper
        audioFileMapper.put(audioName,new File(AUDIO_DIRECTORY+audioName));
    }
    public static List<String> getAudioFilename()
    {
        // TODO :- change this approach : Done
        return audioFileMapper.keySet().stream().toList();
    }

    //TODO - change this method : Done
    public static File getAudioFile(String audioName)
    {
        return audioFileMapper.get(audioName);
    }

}
