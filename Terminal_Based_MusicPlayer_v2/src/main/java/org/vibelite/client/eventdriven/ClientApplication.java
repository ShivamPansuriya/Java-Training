package org.vibelite.client.eventdriven;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.vibelite.Server.ServerApplication;
import org.vibelite.client.eventdriven.ui.TerminalUI;

import java.io.IOException;

public class ClientApplication
{
    public static final Logger logger = LoggerFactory.getLogger(ClientApplication.class);

    public static final Marker fatal = MarkerFactory.getMarker("FATAL");

    public static void main(String[] args)
    {
        // creating main ui object and redirecting client to ui
        var terminalUI = new TerminalUI();

        try
        {
            terminalUI.start();//terminalUI.start();
        } catch(NullPointerException e)
        {
            System.out.println("server is down");

            ClientApplication.logger.error("server is down");

            org.vibelite.client.eventdriven.ClientApplication.main(null);
        }
    }
}
