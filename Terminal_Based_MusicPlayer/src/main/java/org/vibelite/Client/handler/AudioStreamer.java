package org.vibelite.Client.handler;

import org.json.JSONObject;
import org.vibelite.Client.ui.TerminalUI;

import javax.sound.sampled.*;
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
        try
        {
            var is = clientSocket.getInputStream();
            // Wrap the input stream with a BufferedInputStream to support mark/reset
            var bufferedIn = new BufferedInputStream(is);

            //used to read audio file
            var audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);

            //This method is used to obtain a new Clip object.
            var clip = AudioSystem.getClip();

            //this will prepare audio to e ready to play
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


    public void downloadAudio(String audioJSON){
        try
        {
            var audioJson = new JSONObject(audioJSON);

            // Decode Base64 audio data
            var audioData = java.util.Base64.getDecoder().decode(audioJson.getString("content"));

            // Write audio data to a file
            var fos = new FileOutputStream("./"+audioJson.getString("filename"));

            fos.write(audioData);

            fos.close();
        } catch(IOException e)
        {
            System.out.println("(ERROR) cannot download file or data lost");
        }
    }

    public JSONObject sendAudio(String filePath, String fileName)
    {
        try
        {
            var file = new File(filePath);

            var fileData = new byte[(int) file.length()];

            var fileInputStream = new FileInputStream(file);

            fileInputStream.read(fileData);

            // Encode audio data as Base64
            String encodedAudio = java.util.Base64.getEncoder().encodeToString(fileData);

            // Prepare JSON object
            JSONObject audioJson = new JSONObject();

            audioJson.put("filename", fileName);

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
