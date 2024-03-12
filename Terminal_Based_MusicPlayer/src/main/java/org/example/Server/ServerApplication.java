package org.example.Server;


public class ServerApplication
{
    static final MusicLibrary musicLibrary = new MusicLibrary();
    public static void main(String[] args)
    {
        //On start of server all audio files will be loaded first and then socket is created
        musicLibrary.loadAudioFile();

        int port = 6000;
        ServerSockets socket = new ServerSockets(port);
        socket.start();
    }
}
