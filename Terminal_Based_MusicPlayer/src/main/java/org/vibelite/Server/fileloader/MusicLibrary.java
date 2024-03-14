package org.vibelite.Server.fileloader;

import java.io.File;
import java.util.*;

public class MusicLibrary
{
    private List<File> audioFiles;

    // TODO :- remove redundant data structure
    private final Set<String> audioFilename;

    private final Map<String, File> audioFileMapper;

    private final Map<String, Set<String>> playlistMapper;

    public MusicLibrary()
    {

        this.audioFiles = new ArrayList<>();

        this.audioFilename = new HashSet<>();

        this.audioFileMapper = new HashMap<>();

        this.playlistMapper = new HashMap<>();
    }

    public void loadAudioFile()
    {
        //making arraylist(will store relative FILE path) of audio file that has .mp3 and .wav files
        // TODO - make constant for current_directory and path separator
        var audioDirectory = new File("./src/main/resources/Audio");

        audioFiles = Arrays.stream(audioDirectory.listFiles((dir, name) -> name.endsWith(".mp3") || name.endsWith(".wav"))).toList();

        for (var file : audioFiles)
        {
            audioFilename.add(file.getName());

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

    public void addToPlaylist(String playlistName, String audioName)
    {
        playlistMapper.get(playlistName).add(audioName);
    }

    public List<String> getAudioFilename()
    {
        audioFilename.clear();

        loadAudioFile();

        return audioFilename.stream().toList();
    }

    public Map<String,File> getAudioFileMapper()
    {
        return audioFileMapper;
    }

}
