package org.example.Server;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerRequestHandler
{
    private final Socket clientSocket;

    private ObjectOutputStream response;

    private final AudioStreamer audioStreamer;

    ServerRequestHandler(Socket socket)
    {
        this.clientSocket = socket;

        audioStreamer = new AudioStreamer(clientSocket);

        try
        {
            this.response = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch(IOException e)
        {
            throw new RuntimeException(e);
        }

    }

    public void Hello()
    {

        new Thread(() -> {
            try(BufferedReader receive = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));)
            {
                while(true)
                {
                    String request = receive.readLine();

                    if(request.equals("exit"))break;

                    switch(request)
                    {
                        case "library":
                            sendLibrary();
                            break;

                        case "audioFile":
                            request = receive.readLine();
                            sendAudioFile(request);
                            break;
                    }
                }
            } catch(IOException e)
            {
                throw new RuntimeException(e);
            }
        }).start();

    }

    public void sendLibrary()
    {
        var files = ServerApplication.musicLibrary.getAudioFilename();

        try
        {
            response.writeObject(files);
        } catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void sendAudioFile(String audioName){
        audioStreamer.sendAudioToClient(audioName);
    }


}
