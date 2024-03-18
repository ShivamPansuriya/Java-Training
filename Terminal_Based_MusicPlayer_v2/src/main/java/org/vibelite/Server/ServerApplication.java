package org.vibelite.Server;


import org.vibelite.Server.connection.ServerSockets;
import org.vibelite.Server.fileloader.MusicLibrary;
import static org.vibelite.Server.utils.Constants.*;

public class ServerApplication
{
    public static final MusicLibrary musicLibrary = new MusicLibrary();

    public static void main(String[] args)
    {
        //On start of server all audio files will be loaded first and then socket is created
        musicLibrary.loadAudioFile();

        var socket = new ServerSockets(PORT);

        socket.start();
    }
}
