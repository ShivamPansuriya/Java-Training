package org.vibelite.Server.fileloader;

import org.vibelite.Server.ServerApplication;

import static org.vibelite.Server.utils.Constants.*;
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class MusicLibrary
{

    // TODO :- remove redundant data structure : Done
    private static final Map<String, File> audioFileMapper;

    private static final Map<String,Map<String, Set<String>>> playlistMapper;

    private static final Map<String,String> userAuthData;

    static
    {
        //making arraylist(will store relative FILE path) of audio file that has .mp3 and .wav files
        // TODO - make constant for current_directory and path separator : Done
        //todo - make constant of .mp3 and .wav : Done
        // TODO - make static block : Done
        userAuthData = new ConcurrentHashMap<>();

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
    public static boolean createPlaylist(String playlistName, String username)
    {
        Map<String, Set<String>> playlistMap = new ConcurrentHashMap<>();
        playlistMap.put(playlistName + PLAYLIST_SEPARATOR + username, new HashSet<>());
        playlistMapper.put(username,playlistMap);

        ServerApplication.logger.info("playlist created "+ playlistName);

        return true;

    }

    public static List<String> getPlayListNames()
    {
        return playlistMapper.values().stream()           // Stream<Map<String, Set<String>>>
                .flatMap(userPlaylists -> userPlaylists.keySet().stream())  // Stream<String> of playlist names
                .collect(Collectors.toList());  // Collect all playlist names into a List
    }

    public static List<String> getPlaylists(String playlistName, String username)
    {

        return playlistMapper.get(username).get(playlistName).stream().toList();
    }

    public static boolean updatePlaylist(String command,String playlistName, String audioName, String username)
    {
        // TODO - check if object is null or not before performing operation
        if(command.equals(REQUEST_ADDTO_PLAYLIST))
        {
            if(playlistMapper.containsKey(username))
            {
                playlistMapper.get(username).get(playlistName + PLAYLIST_SEPARATOR + username).add(audioName);

                System.out.println(playlistMapper);

                ServerApplication.logger.info(audioName + " added to " + playlistName);
                return true;
            }
            else
            {
                return false;
            }
        }
        else if(command.equals(REQUEST_REMOVE_FROM_PLAYLIST))
        {
            if(playlistMapper.containsKey(username))
            {
                playlistMapper.get(username).get(playlistName).remove(audioName);

                ServerApplication.logger.info(audioName + " removed from " + playlistName);

                System.out.println(playlistMapper);

                return true;
            }
            else
            {
                return false;
            }
        }
        return  false;
    }

    public static boolean updatePlaylist(String playlistName, String username)
    {
        if(!playlistMapper.containsKey(username))
            return false;

        playlistMapper.get(username).remove(playlistName);

        ServerApplication.logger.info(username + " deleted playlist " + playlistName);

        return true;
    }

    public static void updateAudioFiles(String audioName)
    {
        //when user upload audio file we will update audioFileMapper
        audioFileMapper.put(audioName,new File(AUDIO_DIRECTORY+audioName));

        ServerApplication.logger.info(audioName + " added to library");
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

    public static boolean addUser(String username, String password)
    {
        if(userAuthData.containsKey(username))
            return false;

        userAuthData.put(username,password);

        ServerApplication.logger.info(username + " registered to application");

        return true;
    }

    public static boolean validateUser(String username, String password)
    {
        if(userAuthData.containsKey(username))
            if(userAuthData.get(username).equals(password))
            {
                ServerApplication.logger.info(username + " logged into application");
                return true;
            }
        return false;
    }
}
