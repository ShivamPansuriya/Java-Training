package org.example.Server;


public class ServerApplication
{
    static final MusicLibrary musicLibrary = new MusicLibrary();
    public static void main(String[] args)
    {
        musicLibrary.loadAudioFile();

        int port = 6000;
        ServerSockets socket = new ServerSockets(port);
        socket.start();
    }
}
