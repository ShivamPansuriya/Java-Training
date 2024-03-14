package org.vibelite.Server.handler;

import org.vibelite.Server.ServerApplication;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Map;

public class AudioStreamer
{
    private final Socket clientSocket;

    private final int BUFFER_SIZE = 4096; // Buffer size for reading and writing

    AudioStreamer(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public void sendAudioToClient(String audioName)
    {
        Map<String, File> audioFiles = ServerApplication.musicLibrary.getAudioFileMapper();

        File requested_file = audioFiles.get(audioName);

        //        File FILE_TO_SEND = new File(requested_file);
        try
        {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(requested_file));
            OutputStream os = clientSocket.getOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];

            int bytesRead;

            while((bytesRead = bis.read(buffer)) != -1)
            {
                os.write(buffer, 0, bytesRead);

                os.flush();
            }

            System.out.println("Sent " + requested_file + " to client.");
        } catch(IOException e)
        {
            System.out.println("(ERROR) cannot Stream audio: " + e.getMessage());
        }

    }
    public void saveAudioFromClient(String audioName)
    {
        try
        {
            String outputFilePath = "./src/main/resources/Audio/"+audioName;
            InputStream inputStream = clientSocket.getInputStream();
            // Write the received data to a file
            FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath);
            byte[] buffer = new byte[8192];
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
    }
}
