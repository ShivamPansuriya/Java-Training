package org.vibelite.client.eventdriven.handler;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
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
        // checking if clip is not finished and is not running
        if(clip != null && !clip.isRunning())
        {
            if(isPaused)
            {
                // resume playing from paused position
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
            // set pause position to current position
            pausePosition = clip.getMicrosecondPosition();

            //pausing audio
            clip.stop();

            isPaused = true;
        }
    }

    public void closeAudio()
    {
        clip.close();
    }

    public void setVolume() {

        System.out.println("Enter the volume level(0 to 1): ");

        Scanner INPUT = new Scanner(System.in);
        float volume;
        try
        {
            volume = INPUT.nextFloat();
        }catch(InputMismatchException e){
            System.out.println("Enter proper input");
            return;
        }

        if (volume < 0f || volume > 1f)
        {
            System.out.println("Entre valid input between 0-1");
            return;
        }

        //This retrieves the control for adjusting the master gain (volume) of the audio clip
        var gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        //This calculates the decibel (dB) value corresponding to the entered volume level
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

        var lastAudio = musicQueue.pop();

        if(musicQueue.isEmpty())
        {
            return lastAudio;
        }
        else
        {
            return musicQueue.pop();
        }
    }

    public void resetQueue(){
        musicQueue.clear();
    }

}
