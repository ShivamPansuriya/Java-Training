package org.example.Server;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class AudioStreamer
{
    private final Socket clientSocket;

    private final int BUFFER_SIZE = 4096; // Buffer size for reading and writing

    AudioStreamer(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public void sendAudioToClient(String audioName){
        Map<String,File> audioFiles = ServerApplication.musicLibrary.getAudioFileMapper();

        File requested_file = audioFiles.get(audioName);

//        File FILE_TO_SEND = new File(requested_file);
        try
        {
            BufferedInputStream bis =new BufferedInputStream(new FileInputStream(requested_file));
            OutputStream os = clientSocket.getOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];

            int bytesRead;

            while ((bytesRead = bis.read(buffer)) != -1)
            {
                os.write(buffer, 0, bytesRead);

                os.flush();
            }

            System.out.println("Sent " + requested_file + " to client.");
        } catch(IOException e)
        {
            throw new RuntimeException(e);
        }

    }
}
