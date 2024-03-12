package org.example.Server;

import java.io.File;
import java.io.PushbackInputStream;
import java.util.*;

public class MusicLibrary
{
    private List<File> audioFile;

    private Set<String> audioFilename;

    private Map<String, File> audioFileMapper;

    private Map<String, Set<String>> playlistMapper;

    MusicLibrary()
    {

        this.audioFile = new ArrayList<>();

        this.audioFilename = new HashSet<>();

        this.audioFileMapper = new HashMap<>();

        this.playlistMapper = new HashMap<>();
    }

    public void loadAudioFile()
    {
        //making arraylist(will store relative FILE path) of audio file that has .mp3 and .wav files
        File audioDirectory = new File("./src/main/resources/Audio");

        audioFile = Arrays.stream(audioDirectory.listFiles((dir, name) -> name.endsWith(".mp3") || name.endsWith(".wav"))).toList();

        for(var audioName : audioFile){
            audioFilename.add(audioName.getName());
            audioFileMapper.put(audioName.getName(), audioName);
        }
    }

    public void createPlaylist(String playlistName)
    {
        Set<String> musicInPlaylist = new HashSet<>();
        playlistMapper.put(playlistName,musicInPlaylist);
        System.out.println(playlistName);
    }

    public List<String> getPlayListName()
    {
        return playlistMapper.keySet().stream().toList();
    }

    public List<String> getPlaylist(String playlistName){
        return playlistMapper.get(playlistName).stream().toList();
    }

    public void addToPlaylist(String playlistName, String audioName){
        playlistMapper.get(playlistName).add(audioName);
    }

    public List<String> getAudioFilename()
    {
        loadAudioFile();
        return audioFilename.stream().toList();
    }

    public Map<String,File> getAudioFileMapper()
    {
        return audioFileMapper;
    }

}
