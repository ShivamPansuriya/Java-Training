package org.example.Server;

import java.io.File;
import java.io.PushbackInputStream;
import java.util.*;

public class MusicLibrary
{
    private List<File> audioFile;

    private List<String> audioFilename;

    private Map<String, File> audioFileMapper;

    MusicLibrary()
    {

        this.audioFile = new ArrayList<>();

        this.audioFilename = new ArrayList<>();

        this.audioFileMapper = new HashMap<>();
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

    public List<File> getAudioFile()

    {
        return audioFile;
    }

    public List<String> getAudioFilename()
    {
        return audioFilename;
    }

    public Map<String,File> getAudioFileMapper()
    {
        return audioFileMapper;
    }

}
