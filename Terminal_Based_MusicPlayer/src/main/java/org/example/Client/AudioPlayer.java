package org.example.Client;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AudioPlayer
{
    private final Socket clientSocket;

    AudioPlayer(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public void receiveAndPlayAudio(TerminalUI terminalUI, String audioName, String playedFrom) {
        try
        {
            InputStream is = clientSocket.getInputStream();
            // Wrap the input stream with a BufferedInputStream to support mark/reset
            InputStream bufferedIn = new BufferedInputStream(is);

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);

            Clip clip = AudioSystem.getClip();

            clip.open(audioInputStream);


            terminalUI.AudioPlayerUI(clip, audioName, playedFrom);

        } catch(UnsupportedAudioFileException e)
        {
            System.out.println("(ERROR) audio file is not supported: " + e.getMessage());
        } catch(LineUnavailableException e)
        {
            System.out.println("(ERROR) audio file is corrupted: " + e.getMessage());
        } catch(IOException e)
        {
            System.out.println("(ERROR) cannot read audio file from server: " + e.getMessage());
        }
    }


}
