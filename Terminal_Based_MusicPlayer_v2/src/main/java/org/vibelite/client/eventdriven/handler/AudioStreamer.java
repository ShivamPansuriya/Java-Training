package org.vibelite.client.eventdriven.handler;

import org.vibelite.client.eventdriven.ClientApplication;
import org.vibelite.client.eventdriven.ui.TerminalUI;
import static org.vibelite.client.eventdriven.utils.Constants.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.net.Socket;



public class AudioStreamer
{
    private final Socket clientSocket;

    AudioStreamer(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public void receiveAndPlayAudio(TerminalUI terminalUI, String audioName, String playedFrom)
    {
        try(var bufferedIn = new BufferedInputStream(clientSocket.getInputStream()))
        {
            // Wrap the input stream with a BufferedInputStream to support mark/reset

            //used to read audio file
            var audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);

            //This method is used to obtain a new Clip object.
            var clip = AudioSystem.getClip();

            //this will prepare audio to e ready to play
            clip.open(audioInputStream);

            terminalUI.audioPlayerUI(clip, audioName, playedFrom);

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

    public void downloadAudios(String audioName)
    {

        // Write the received data to a file
        String outputFilePath = "./" + audioName;

        // Get the input stream from the server
        try(InputStream inputStream = clientSocket.getInputStream() ;
            FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath))
        {

            byte[] buffer = new byte[BUFFER_SIZE];

            int bytesRead;

            while((bytesRead = inputStream.read(buffer)) != -1)
            {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("File received and saved to " + outputFilePath);

        } catch(IOException e)
        {
            System.out.println(SERVER_DOWN_MESSAGE);

            ClientApplication.logger.error(SERVER_DOWN_MESSAGE);
        }
    }



    public void sendAudioToServer(String filePath)
    {
        File fileToSend = new File(filePath);

        try(BufferedInputStream fileBuffer = new BufferedInputStream(new FileInputStream(fileToSend));
            OutputStream os = clientSocket.getOutputStream())
        {
            byte[] buffer = new byte[BUFFER_SIZE];

            int bytesRead;

            while((bytesRead = fileBuffer.read(buffer)) != -1)
            {
                os.write(buffer, 0, bytesRead);

                os.flush();
            }

            System.out.println("Sent " + fileToSend + " to client.");

        } catch(FileNotFoundException e)
        {
            System.out.println("File not found");
        } catch(IOException e)
        {
            System.out.println("Socket is closed");
        }
    }

}
