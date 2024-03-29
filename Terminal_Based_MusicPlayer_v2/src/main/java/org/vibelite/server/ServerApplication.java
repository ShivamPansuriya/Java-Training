package org.vibelite.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.vibelite.server.connection.ServerSockets;

import static org.vibelite.server.utils.Config.*;

public class ServerApplication
{
    public static final Logger logger = LoggerFactory.getLogger(ServerApplication.class);

    public static final Marker fatal = MarkerFactory.getMarker("FATAL");

    public static void main(String[] args)
    {
        //On start of server all audio files will be loaded first and then socket is created
        //TODO - make program configurable

        var socket = new ServerSockets(PORT);

        socket.start();
    }
}
