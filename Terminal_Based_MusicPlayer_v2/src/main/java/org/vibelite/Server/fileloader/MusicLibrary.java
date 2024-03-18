package org.vibelite.Server.fileloader;

import static org.vibelite.Server.utils.Constants.*;
import java.io.File;
import java.util.*;


public class MusicLibrary
{

    // TODO :- remove redundant data structure : Done
    private final Map<String, File> audioFileMapper;

    private final Map<String, Set<String>> playlistMapper;

    public MusicLibrary()
    {
        this.audioFileMapper = new HashMap<>();

        this.playlistMapper = new HashMap<>();
    }

    public void loadAudioFile()
    {
        //making arraylist(will store relative FILE path) of audio file that has .mp3 and .wav files
        // TODO - make constant for current_directory and path separator : Done
        var audioDirectory = new File(AUDIO_DIRECTORY);

        var audioFiles = Arrays.stream(audioDirectory.listFiles((dir, name) -> name.endsWith(".mp3") || name.endsWith(".wav"))).toList();

        for (var file : audioFiles)
        {
            audioFileMapper.put(file.getName(), file);
        }
    }

    public void createPlaylist(String playlistName)
    {
        playlistMapper.put(playlistName,new HashSet<>());
    }

    public List<String> getPlayListNames()
    {
        return playlistMapper.keySet().stream().toList();
    }

    public List<String> getPlaylists(String playlistName)
    {
        return playlistMapper.get(playlistName).stream().toList();
    }

    public String updatePlaylist(String command,String playlistName, String audioName)
    {
        var message="";
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

    public void updatePlaylist(String playlistName)
    {
        playlistMapper.remove(playlistName);
    }

    public void updateAudioFiles(String audioName)
    {
        //when user upload audio file we will update audioFileMapper
        audioFileMapper.put(audioName,new File(AUDIO_DIRECTORY+audioName));
    }
    public List<String> getAudioFilename()
    {
        // TODO :- change this approach : Done
        return audioFileMapper.keySet().stream().toList();
    }

    public Map<String,File> getAudioFileMapper()
    {
        return audioFileMapper;
    }

}
