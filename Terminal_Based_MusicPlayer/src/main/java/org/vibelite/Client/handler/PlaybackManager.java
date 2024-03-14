package org.vibelite.Client.handler;

import javax.sound.sampled.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

public class PlaybackManager
{
    private Clip clip;
    private boolean isPaused = false;

    private long pausePosition = 0;

    private final Stack<String> musicQueue = new Stack<>();

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

        System.out.println("Enter the volume level(0 to 1): ");

        float volume;
        try
        {
            volume = sc.nextFloat();
        }catch(InputMismatchException e){
            System.out.println("Entre proper input");
            return;
        }

        if (volume < 0f || volume > 1f)
        {
            System.out.println("Entre valid input between 0-1");
            return;
        }

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);

        gainControl.setValue(dB);
    }

    public void setAudio(String audioName){
        musicQueue.push(audioName);
    }

    public void resetAudio(){
        clip.setFramePosition(0);
    }

    public String getAudio(){
        return musicQueue.peek();
    }

    public String getPreviousAudio()
    {
        closeAudio();

        System.out.println(musicQueue);

        String lastAudio = musicQueue.pop();

        if(musicQueue.isEmpty())
        {
            return lastAudio;
        }
        else
        {
            return musicQueue.pop();
        }
    }

}
