package org.vibelite.Client.handler;

import org.vibelite.Client.ui.TerminalUI;

import javax.sound.sampled.*;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class AudioPlayer
{
    private final Socket clientSocket;

    AudioPlayer(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public void receiveAndPlayAudio(TerminalUI terminalUI, String audioName, String playedFrom)
    {
        try
        {
            InputStream is = clientSocket.getInputStream();
            // Wrap the input stream with a BufferedInputStream to support mark/reset
            InputStream bufferedIn = new BufferedInputStream(is);

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);

            Clip clip = AudioSystem.getClip();

            clip.open(audioInputStream);


            terminalUI.AudioPlayerUI(clip, audioName, playedFrom);

        } catch(IOException e)
        {
            System.out.println("(ERROR) cannot read audio file from server: " + e.getMessage());
        }
        catch(UnsupportedAudioFileException e)
        {
            System.out.println("(ERROR) audio file is not supported: " + e.getMessage());
        } catch(LineUnavailableException e)
        {
            System.out.println("(ERROR) audio file is corrupted: " + e.getMessage());
        }
    }


    public void sendAudio(String filePath)
    {
        try
        {
            OutputStream outputStream = clientSocket.getOutputStream();


            InputStream in = clientSocket.getInputStream();

            // Read the audio file and send it to the client
            String audioFilePath = filePath.toString();
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(filePath));
            byte[] buffer = new byte[8192];
            int bytesRead;

            while((bytesRead = fileInputStream.read(buffer)) != -1)
            {
                outputStream.write(buffer, 0, bytesRead);

                outputStream.flush();
            }

            System.out.println("File transfer completed.");
        } catch(IOException e)
        {
            System.out.println("(ERROR) cannot upload file to server");
        }
    }


}
