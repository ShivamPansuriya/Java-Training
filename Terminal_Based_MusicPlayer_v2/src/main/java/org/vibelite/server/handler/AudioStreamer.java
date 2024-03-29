package org.vibelite.server.handler;

import org.vibelite.server.fileloader.MusicLibrary;

import static org.vibelite.server.utils.Constants.*;

import java.io.*;
import java.net.Socket;

public class AudioStreamer
{
    private final Socket clientSocket;

    AudioStreamer(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public void streamAudioToClient(String audioName)
    {
        var fileStreaming = MusicLibrary.getAudioFile(audioName);

        try(var fileBuffer = new BufferedInputStream(new FileInputStream(fileStreaming));)
        {
            var writer = clientSocket.getOutputStream();

            byte[] buffer = new byte[BUFFER_SIZE];

            int bytesRead;

            while((bytesRead = fileBuffer.read(buffer)) != -1)
            {
                writer.write(buffer, 0, bytesRead);

                writer.flush();
            }

            System.out.println("Sent " + audioName + " to client.");
        }
        catch(IOException e)
        {
            System.out.println("(ERROR) cannot Stream audio: " + e.getMessage());
        }

    }

    public void saveAudioFromClient(String audioName)
    {
        // Write the received data to a file
        var outputFilePath = AUDIO_DIRECTORY + audioName;

        System.out.println(outputFilePath);

        try(var fileOutputStream = new FileOutputStream(outputFilePath))
        {
            // Get the input stream from the server
            var inputStream = clientSocket.getInputStream();

            byte[] buffer = new byte[BUFFER_SIZE];

            int bytesRead;

            while((bytesRead = inputStream.read(buffer)) != -1)
            {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("File received and saved to " + outputFilePath);

        } catch(IOException e)
        {
            System.out.println("(ERROR) cannot download file or data lost");
        }

        MusicLibrary.updateAudioFiles(audioName);
    }

    public void sendAudioToClients(String audioName)
    {

        try(var fileBuffer = new BufferedInputStream(new FileInputStream(MusicLibrary.getAudioFile(audioName)));
            var outputStream = clientSocket.getOutputStream())
        {
            byte[] buffer = new byte[BUFFER_SIZE];

            int bytesRead;

            while((bytesRead = fileBuffer.read(buffer)) != -1)
            {
                outputStream.write(buffer, 0, bytesRead);

                outputStream.flush();
            }

            System.out.println("Sent " + audioName + " to client.");

        } catch(FileNotFoundException e)
        {
            System.out.println("File not found");
        } catch(IOException e)
        {
            System.out.println("Socket is closed");
        }
    }
}
