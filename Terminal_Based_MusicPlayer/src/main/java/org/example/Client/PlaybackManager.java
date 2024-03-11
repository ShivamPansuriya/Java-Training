package org.example.Client;

import javax.sound.sampled.*;
import java.util.Scanner;
import java.util.Stack;

public class PlaybackManager
{
    private Clip clip;
    private boolean isPaused = false;

    private long pausePosition = 0;

    private Stack<String> musicQueue = new Stack<>();

    public void setClip(Clip clip){
        this.clip = clip;
    }
    public void playAudio()
    {
        if(clip != null && !clip.isRunning())
        {
            if(isPaused)
            {
                clip.setMicrosecondPosition(pausePosition);

                isPaused = false;
            }
            clip.start();
        }
    }

    public void pauseAudio()
    {
        if(clip != null && clip.isRunning())
        {
            pausePosition = clip.getMicrosecondPosition();

            clip.stop();

            isPaused = true;
        }
    }

    public void closeAudio()
    {
        clip.close();
    }

    public void setVolume() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Entre the volume level(0 to 1): ");

        float volume = sc.nextFloat();

        if (volume < 0f || volume > 1f)
        {
            throw new IllegalArgumentException("Volume not valid: " + volume);
        }

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);

        gainControl.setValue(dB);
    }

    public void setAudio(String audioName){
        musicQueue.push(audioName);
    }

    public String getAudio(){
        return musicQueue.peek();
    }

    public String getPreviousAudio()
    {
        closeAudio();

        String lastAudio = musicQueue.pop();

        if(musicQueue.isEmpty())
        {
            return lastAudio;
        }
        else
        {
            return musicQueue.peek();
        }
    }

}
