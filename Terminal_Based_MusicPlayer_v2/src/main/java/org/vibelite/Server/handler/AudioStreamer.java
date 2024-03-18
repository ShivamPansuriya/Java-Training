package org.vibelite.Server.handler;

import org.json.JSONObject;
import org.vibelite.Server.ServerApplication;

import static org.vibelite.Server.utils.Constants.*;

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
        var audioFiles = ServerApplication.musicLibrary.getAudioFileMapper();

        // TODO :- change variable name...  : Done
        var fileStreaming = audioFiles.get(audioName);

        try
        {
            // TODO :- change name...  : Done
            var fileBuffer = new BufferedInputStream(new FileInputStream(fileStreaming));

            var writer = clientSocket.getOutputStream();

            byte[] buffer = new byte[BUFFER_SIZE];

            int bytesRead;

            while((bytesRead = fileBuffer.read(buffer)) != -1)
            {
                writer.write(buffer, 0, bytesRead);

                writer.flush();
            }

            System.out.println("Sent " + audioName + " to client.");
        } catch(IOException e)
        {
            System.out.println("(ERROR) cannot Stream audio: " + e.getMessage());
        }

    }

    public void saveAudioFromClient(String audioName)
    {
        try
        {
            // Get the input stream from the server
            InputStream inputStream = clientSocket.getInputStream();

            // Write the received data to a file
            String outputFilePath = AUDIO_DIRECTORY + audioName;

            System.out.println(outputFilePath);

            FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath);
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while((bytesRead = inputStream.read(buffer)) != -1)
            {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            fileOutputStream.close();

            System.out.println("File received and saved to " + outputFilePath);
        } catch(IOException e)
        {
            System.out.println("(ERROR) cannot download file or data lost");
        }

        ServerApplication.musicLibrary.updateAudioFiles(audioName);
    }

    public void sendAudioToClients(String audioName)
    {
        var audioFiles = ServerApplication.musicLibrary.getAudioFileMapper();

        try(BufferedInputStream fileBuffer = new BufferedInputStream(new FileInputStream(audioFiles.get(audioName)));
            OutputStream outputStream = clientSocket.getOutputStream())
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
