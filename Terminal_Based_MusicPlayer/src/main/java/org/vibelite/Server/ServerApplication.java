package org.vibelite.Server;


import org.vibelite.Server.connection.ServerSockets;
import org.vibelite.Server.fileloader.MusicLibrary;

public class ServerApplication
{
    public static final MusicLibrary musicLibrary = new MusicLibrary();
    public static void main(String[] args)
    {
        //On start of server all audio files will be loaded first and then socket is created
        musicLibrary.loadAudioFile();

        var port = 6000;
        var socket = new ServerSockets(port);
        socket.start();
    }
}
