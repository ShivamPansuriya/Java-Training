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
        }
        catch(IOException e)
        {
            System.out.println("(ERROR) cannot Stream audio: " + e.getMessage());
        }

    }
    public void saveAudioFromClient(String audioJson)
    {
        try
        {
            var audioJSON = new JSONObject(audioJson);

            // Decode Base64 audio data - gives array of byte
            var audioData = java.util.Base64.getDecoder().decode(audioJSON.getString("content"));

            // Write audio data to a file
            FileOutputStream fos = new FileOutputStream(AUDIO_DIRECTORY+audioJSON.getString("filename"));

            fos.write(audioData);

            fos.close();

            ServerApplication.musicLibrary.updateAudioFiles(audioJSON.getString("filename"));
        }
        catch(IOException e)
        {
            System.out.println("(ERROR) cannot download file or data lost");
        }
    }

    public JSONObject sendAudioToClient(String audioName)
    {
        var audioFiles = ServerApplication.musicLibrary.getAudioFileMapper();

        // Prepare JSON object
        var audioJson = new JSONObject();

        try
        {
            var file = audioFiles.get(audioName);

            // TODO :- change name...  : Done
            var fileDataBuffer = new byte[(int) file.length()];

            var fileInputStream = new FileInputStream(file);

            fileInputStream.read(fileDataBuffer);

            // Encode audio data as Base64
            var encodedAudio = java.util.Base64.getEncoder().encodeToString(fileDataBuffer);

            // Populating JSON object
            audioJson.put("filename", audioName);

            audioJson.put("content", encodedAudio);

            //Closing file after reading
            fileInputStream.close();

        }
        catch(IOException e)
        {
            System.out.println("(ERROR) Cannot read File it may be corrupted");
        }

        // Send JSON data
        return audioJson;
    }
}
