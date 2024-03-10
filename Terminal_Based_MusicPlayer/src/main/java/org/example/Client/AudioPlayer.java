package org.example.Client;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class AudioPlayer
{
    private final Socket clientSocket;

    AudioPlayer(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public void receiveAndPlay(){
        try
        {
            InputStream is = clientSocket.getInputStream();
            // Wrap the input stream with a BufferedInputStream to support mark/reset
            InputStream bufferedIn = new BufferedInputStream(is);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
            auline.start();

            byte[] buffer = new byte[4096];
            int bytesRead;

            while((bytesRead = audioInputStream.read(buffer)) != -1)
            {
                auline.write(buffer, 0, bytesRead);
            }

            auline.drain();
            auline.close();
        } catch(UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            throw new RuntimeException(e);
        }
    }


}