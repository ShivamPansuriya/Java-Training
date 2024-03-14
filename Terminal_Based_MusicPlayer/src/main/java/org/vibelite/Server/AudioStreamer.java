package org.vibelite.Server;

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
            System.out.println("(ERROR) cannot Stream audio: " + e.getMessage());
        }

    }

    public void saveAudioFromClient(String audioName)
    {
        try
        {
            var outputFilePath = "./src/main/resources/Audio/"+audioName;
            var inputStream = clientSocket.getInputStream();
            // Write the received data to a file
            var fileOutputStream = new FileOutputStream(outputFilePath);
            var buffer = new byte[4096];
            var bytesRead =0;

            while((bytesRead = inputStream.read(buffer)) != -1)
            {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            System.out.println("File received and saved to " + outputFilePath);
        } catch(IOException e)
        {
            System.out.println("(ERROR) cannot download file or data lost");
        }
    }
}
