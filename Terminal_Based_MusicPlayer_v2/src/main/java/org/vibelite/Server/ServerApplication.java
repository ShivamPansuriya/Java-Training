package org.vibelite.Server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.vibelite.Server.connection.ServerSockets;
import org.vibelite.Server.fileloader.MusicLibrary;
import static org.vibelite.Server.utils.Constants.*;

public class ServerApplication
{
    public static final Logger logger = LoggerFactory.getLogger(ServerApplication.class);

    public static void main(String[] args)
    {
        //On start of server all audio files will be loaded first and then socket is created
        //TODO - make program configurable : Done
        var socket = new ServerSockets();

        socket.start();
    }
}
