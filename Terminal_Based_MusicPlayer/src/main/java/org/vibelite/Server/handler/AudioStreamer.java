package org.vibelite.Server.handler;

import org.json.JSONObject;
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

    public void streamAudioToClient(String audioName)
    {
        var audioFiles = ServerApplication.musicLibrary.getAudioFileMapper();

        var requested_file = audioFiles.get(audioName);

        try
        {
            var bis = new BufferedInputStream(new FileInputStream(requested_file));

            var os = clientSocket.getOutputStream();

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
    public void saveAudioFromClient(String audioJSON)
    {
        try
        {
            var audioJson = new JSONObject(audioJSON);

            // Decode Base64 audio data - gives array of byte
            var audioData = java.util.Base64.getDecoder().decode(audioJson.getString("content"));

            // Write audio data to a file
            FileOutputStream fos = new FileOutputStream("./src/main/resources/Audio/"+audioJson.getString("filename"));

            fos.write(audioData);

            fos.close();
        } catch(IOException e)
        {
            System.out.println("(ERROR) cannot download file or data lost");
        }
    }

    public JSONObject sendAudioToClient(String audioName)
    {
        var audioFiles = ServerApplication.musicLibrary.getAudioFileMapper();
        try
        {
            File file = audioFiles.get(audioName);

            var fileData = new byte[(int) file.length()];

            var  fileInputStream = new FileInputStream(file);

            fileInputStream.read(fileData);

            // Encode audio data as Base64
            String encodedAudio = java.util.Base64.getEncoder().encodeToString(fileData);

            // Prepare JSON object
            var audioJson = new JSONObject();

            audioJson.put("filename", audioName);

            audioJson.put("content", encodedAudio);



            fileInputStream.close();

            // Send JSON data
            return audioJson;

        } catch(IOException e)
        {
            System.out.println("(ERROR) Cannot read File it may be corrupted");
        }
        return null;
    }
}
