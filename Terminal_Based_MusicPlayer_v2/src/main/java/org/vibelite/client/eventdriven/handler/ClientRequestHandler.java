package org.vibelite.client.eventdriven.handler;

import org.json.JSONException;
import org.json.JSONObject;
import org.vibelite.client.eventdriven.ClientApplication;
import org.vibelite.client.eventdriven.ui.TerminalUI;
import static org.vibelite.client.eventdriven.utils.Constants.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ClientRequestHandler
{
    private PrintWriter request;

    private BufferedReader reader;

    public static TerminalUI terminalUI;

    private AudioStreamer audioStreamer;

    public ClientRequestHandler(Socket clientSocket)
    {
        audioStreamer = new AudioStreamer(clientSocket);

        try
        {
            this.request = new PrintWriter(clientSocket.getOutputStream(), true);

            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch(IOException e)
        {
            System.out.println("(ERROR) cannot read or write from socket stream: " + e.getMessage());
        }
    }

    public List<String> requestLibrary() throws IOException, NullPointerException
    {
        List<String> audioFiles = new ArrayList<>();

        var jsonRequest = new JSONObject();

        jsonRequest.put(COMMAND,REQUEST_LIBRARY);
        //sending request type
        request.println(jsonRequest);

        ClientApplication.logger.info("request send: " + jsonRequest);

        try
        {
            var response = reader.readLine();

            var responseJson = new JSONObject(response);

            var jsonArray = responseJson.getJSONArray(REQUEST_LIBRARY);

            for(int i = 0; i < jsonArray.length(); i++)
            {
                audioFiles.add(jsonArray.getString(i));
            }
        } catch(IOException e)
        {
            System.out.println("Server down");

            System.out.println("Retrying to establish connection");

            ClientApplication.logger.error("Server down" + NEWLINE + "Retrying to establish connection");
        } finally
        {
            request.close();

            reader.close();

        }

        return audioFiles;
    }

    public String requestNextAudio(String currentAudio, String playedFrom) throws IOException
    {

        int size, currentIndex, nextIndex;

        String audioName = "";

        if(playedFrom.equals(LIBRARY_PLAYER_ID))
        {

            var audioFiles = requestLibrary();
            //get size of library
            size = audioFiles.size();

            //finding index of current playing audio
            currentIndex = audioFiles.indexOf(currentAudio);

            //determining next audio index
            nextIndex = (currentIndex < size - 1) ? ++currentIndex : 0;

            //finding next audio name
            audioName = audioFiles.get(nextIndex);
        }
        else
        {
            var playlistTracks = playPlaylist(playedFrom);

            if(playlistTracks == null)
            {
                return null;
            }
            //get size of playlist
            size = playlistTracks.size();

            //finding index of current playing audio
            currentIndex = playlistTracks.indexOf(currentAudio);

            //determining next audio index
            nextIndex = (currentIndex < size - 1) ? ++currentIndex : 0;

            //finding next audio name
            audioName = playlistTracks.get(nextIndex);
        }
        return audioName;
    }

    public boolean authenticateUser(String command,String username, String password) throws IOException
    {
        var jsonRequest = new JSONObject();
        if(command.equals(LOGIN))
        {
            jsonRequest = new JSONObject();

            jsonRequest.put(COMMAND, LOGIN);

            jsonRequest.put("username", username.trim());

            jsonRequest.put("password", password.trim());

            request.println(jsonRequest);

            ClientApplication.logger.info("request send: " + jsonRequest);

            var response = reader.readLine();

            ClientApplication.logger.info("response from server: " + jsonRequest);

            JSONObject resJSON = new JSONObject(response);

            request.close();

            reader.close();

            if(resJSON.getString(STATUS_CODE).equals("success"))
            {
                System.out.println(resJSON.getString(MESSAGE));

                return true;
            }
            else
            {
                System.out.println(resJSON.getString(MESSAGE));

                return false;
            }
        }
        else if(command.equals(REGISTER))
        {
            jsonRequest.put(COMMAND, REGISTER);

            jsonRequest.put("username", username.trim());

            jsonRequest.put("password", password.trim());

            request.println(jsonRequest);

            ClientApplication.logger.info("request send: " + jsonRequest);

            var response = reader.readLine();

            ClientApplication.logger.info("response from server: " + jsonRequest);

            JSONObject resJSON = new JSONObject(response);

            request.close();

            reader.close();

            if(resJSON.getString(STATUS_CODE).equals("success"))
            {
                System.out.println(resJSON.getString(MESSAGE));

                return true;
            }
            else
            {
                System.out.println(resJSON.getString(MESSAGE));

                return false;
            }
        }

        request.close();

        reader.close();

        return false;
    }
    public void requestAudio(String audioName, String playedFrom) throws IOException
    {
        var jsonRequest = new JSONObject();

        //sending request type
        jsonRequest.put(COMMAND, REQUEST_AUDIO_STREAMING);

        //sending actual request
        jsonRequest.put(MESSAGE, audioName);

        request.println(jsonRequest);

        ClientApplication.logger.info("request send: " + jsonRequest);

        //start playing audio
        audioStreamer.receiveAndPlayAudio(terminalUI, audioName, playedFrom);

        request.close();

        reader.close();
    }

    public void uploadAudio(List<String> library) throws IOException
    {
        System.out.println("Enter audio file path: ");

        Scanner INPUT = new Scanner(System.in);

        var filePath = INPUT.next();

        //splitting file path in directories to get filename
        var directories = filePath.trim().split(PATH_SEPARATOR);

        if(directories.length < 2)
        {
            return;
        }
        //extracting file name from file path array
        var fileName = directories[directories.length - 1];

        //request library to check if audio you are trying is upload is already present or not
        if(library.contains(fileName))
        {
            System.out.println("File already exists");
            return;
        }

        // checking if file actually exists or not and whether it is executable
        if(Files.exists(Paths.get(filePath)) && fileName.contains(FILE_IDENTIFIER))
        {
            try
            {
                //MIME type output for .wav file  = audio/wav
                var fileType = Files.probeContentType(Path.of(filePath)).split(PATH_SEPARATOR, 2);

                if(!(fileType[0].equals("audio") && fileType[1].contains("wav")))
                {
                    System.out.println("only supported wav files you are trying to upload: " + fileType[1] + " " + fileType[0]);

                    return;
                }
            } catch(IOException e)
            {
                System.out.println("(ERROR) File with improper file format");
            }
        }
        else
        {
            System.out.println("file not found");
            return;
        }

        var jsonRequest = new JSONObject();

        jsonRequest.put(COMMAND,REQUEST_UPLOAD);

        jsonRequest.put(MESSAGE, fileName);

        //sending request type
        request.println(jsonRequest);

        ClientApplication.logger.info("request send: " + jsonRequest);

        audioStreamer.sendAudioToServer(filePath);

        System.out.println("Audio" + fileName + "send Successfully");
    }

    public void downloadAudio(String audioName) throws IOException
    {
        var jsonRequest = new JSONObject();

        jsonRequest.put(COMMAND,REQUEST_DOWNLOAD);

        jsonRequest.put(MESSAGE, audioName);
        //sending request type
        request.println(jsonRequest);

        ClientApplication.logger.info("request send: " + jsonRequest);

        //converting json object to audio file
        audioStreamer.downloadAudios(audioName);

        ClientApplication.logger.info("Download successful: " + audioName);

        request.close();

        reader.close();
    }

    public void createPlaylist(String playlistName, String username) throws IOException

    {
        var jsonRequest = new JSONObject();

        jsonRequest.put(COMMAND,REQUEST_CREATE_PLAYLIST);

        jsonRequest.put(MESSAGE, playlistName);

        jsonRequest.put("username", username);

        //sending request type
        request.println(jsonRequest);

        ClientApplication.logger.info("request send: " + jsonRequest);

        var response = reader.readLine();

        ClientApplication.logger.info("response from server: " + jsonRequest);

        JSONObject resJSON = new JSONObject(response);

        if(resJSON.getString(STATUS_CODE).equals("success"))
        {
            System.out.println(resJSON.getString(MESSAGE));
        }
        else
        {
            System.out.println(resJSON.getString(MESSAGE));
        }

        request.close();

        reader.close();

    }

    public List<String> requestPlaylist() throws IOException
    {
        var jsonRequest = new JSONObject();

        jsonRequest.put(COMMAND,REQUEST_PLAYLIST_NAMES);

        //sending request type
        request.println(jsonRequest);

        ClientApplication.logger.info("request send: " + jsonRequest);

        List<String> playlistNames = new ArrayList<>();
        try
        {
            var response = reader.readLine();

            ClientApplication.logger.info("response from server: " + jsonRequest);

            JSONObject responseJson = new JSONObject(response);
            //response from server
            var jsonArray = responseJson.getJSONArray(REQUEST_PLAYLIST_NAMES);

            for(int i = 0; i < jsonArray.length(); i++)
            {
                playlistNames.add(jsonArray.getString(i));
            }

        } catch(IOException e)
        {
            System.out.println("Server down");

            System.out.println("Retrying to establish connection");

            ClientApplication.logger.error("Server down" + NEWLINE + "Retrying to establish connection");

        } catch(JSONException e)
        {
            System.out.println("cannot read JSON file");

            ClientApplication.logger.error("cannot read JSON file");
        } finally
        {
            request.close();

            reader.close();

        }

        return playlistNames;
    }

    public boolean requestUpdatePlaylist(String requestType, String playlistName, String audioName , String username) throws IOException
    {
        var jsonRequest = new JSONObject();

        jsonRequest.put(COMMAND,requestType);

        jsonRequest.put(MESSAGE, playlistName + PATH_SEPARATOR + audioName);

        jsonRequest.put("username", username);

        //sending request type
        request.println(jsonRequest);

        ClientApplication.logger.info("request send: " + jsonRequest);

        try
        {
            var response = reader.readLine();

            ClientApplication.logger.info("response from server: " + jsonRequest);

            JSONObject resJSON = new JSONObject(response);

            if(resJSON.getString(STATUS_CODE).equals("success"))
            {
                System.out.println(resJSON.getString(MESSAGE));
            }
            else
            {
                System.out.println(resJSON.getString(MESSAGE));
            }
        } catch(IOException e)
        {
            System.out.println("Server down");

            System.out.println("Retrying to establish connection");

            ClientApplication.logger.error("Server down" + NEWLINE + "Retrying to establish connection");
        }
        finally
        {
            request.close();

            reader.close();
        }

        return true;
    }

    public boolean requestUpdatePlaylist(String playlistName, String username) throws IOException
    {
        var jsonRequest = new JSONObject();

        jsonRequest.put(COMMAND,REQUEST_REMOVE_PLAYLIST);

        jsonRequest.put(MESSAGE, playlistName);

        jsonRequest.put("username", username);

        request.println(jsonRequest);

        ClientApplication.logger.info("request send: " + jsonRequest);

        try
        {
            var response = reader.readLine();

            ClientApplication.logger.info("response from server: " + jsonRequest);

            JSONObject resJSON = new JSONObject(response);

            if(resJSON.getString(STATUS_CODE).equals("success"))
            {
                System.out.println(resJSON.getString(MESSAGE));
            }
            else
            {
                System.out.println(resJSON.getString(MESSAGE));
            }
        } catch(IOException e)
        {
            System.out.println("Server down");

            System.out.println("Retrying to establish connection");

            ClientApplication.logger.error("Server down" + NEWLINE + "Retrying to establish connection");

        }
        finally
        {
            request.close();

            reader.close();
        }

        return true;
    }

    public List<String> playPlaylist(String playlistName) throws IOException
    {

        var jsonRequest = new JSONObject();

        jsonRequest.put(COMMAND,REQUEST_PLAYLIST);

        jsonRequest.put(MESSAGE, playlistName);

        //sending request type
        request.println(jsonRequest);

        ClientApplication.logger.info("request send: " + jsonRequest);

        List<String> playlistTracks = new ArrayList<>();
        try
        {
            String response = reader.readLine();

            ClientApplication.logger.info("response from server: " + response);

            if(response == null)
            {
                return null;
            }

            JSONObject responseJson = new JSONObject(response);

            var jsonArray = responseJson.getJSONArray(REQUEST_PLAYLIST);

            for(int i = 0; i < jsonArray.length(); i++)
            {
                playlistTracks.add(jsonArray.getString(i));
            }

        } catch(IOException e)
        {
            System.out.println("(ERROR) Cannot read file: ");

            ClientApplication.logger.error("(ERROR) Cannot read file: ");
        }finally
        {
            request.close();

            reader.close();

        }

        return playlistTracks;
    }

    public void closeConnection()
    {

        // because closing the connection needs time and without it server application reader thread was showing exception(never stops programing)
        request.println(REQUEST_EXIT);

    }

}
