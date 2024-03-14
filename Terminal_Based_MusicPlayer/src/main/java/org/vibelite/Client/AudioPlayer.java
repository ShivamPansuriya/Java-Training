package org.vibelite.Client;

import org.vibelite.Client.ui.TerminalUI;

import javax.sound.sampled.*;
import java.io.*;
import java.net.Socket;
import java.nio.file.Path;

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
            var file = new File(filePath);

            var dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

            FileInputStream fileInputStream = new FileInputStream(file);

            // Here we send the File to Server
            dataOutputStream.writeLong(file.length());

            // Send the file to the client
            byte[] buffer = new byte[4096];
            int bytesRead;

            while((bytesRead = fileInputStream.read(buffer)) != -1)
            {
                // Send the file to Server Socket
                dataOutputStream.write(buffer, 0, bytesRead);

                dataOutputStream.flush();
            }

            // close the file here
            fileInputStream.close();



            System.out.println("File sent to server.");

        } catch(IOException e)
        {
            System.out.println("(ERROR) cannot upload file to server");
        }
    }


}
