package org.vibelite.client.eventdriven.connection;

import org.json.JSONObject;
import org.vibelite.client.eventdriven.ClientApplication;
import org.vibelite.client.eventdriven.handler.ClientRequestHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;


public class ClientSocket
{
    private final int serverPort;

    private final String serverIP;

    private Socket socket;

    public ClientSocket()
    {
        var config = loadConfig();

        serverIP = config.getString("IP");

        serverPort = config.getInt("PORT");
    }

    private JSONObject loadConfig()
    {
        try(var inputStream = new FileInputStream("config.json"))
        {
            var buffer = inputStream.readAllBytes();

            var jsonText = new String(buffer);

            ClientApplication.logger.info("read config file: " + jsonText);

            return new JSONObject(jsonText);

        }
        catch(IOException e)
        {
            System.out.println("Error reading configuration file: ");

            ClientApplication.logger.error("Error reading configuration file: " + e.getMessage());

            return new JSONObject();
        }
    }


    public ClientRequestHandler connect()
    {
        ClientRequestHandler requestHandler = null;

        try
        {
            //establish connection with server
            socket = new Socket(serverIP, serverPort);

            // create single clientRequestHandler object
            requestHandler = new ClientRequestHandler(socket);

        } catch(IOException e)
        {
            System.out.println("Server you are trying to connect is not available");

            try
            {
                Thread.sleep(5000);
            } catch(InterruptedException ex)
            {
                System.out.println("(FATAL ERROR)");
                ClientApplication.logger.error("reconnect sleep interrupted");
            }
        }
        return requestHandler;
    }

    public void disconnect()
    {
        try
        {
            socket.close();
        } catch(IOException e)
        {
            System.out.println("Cannot disconnect socket connection");
            ClientApplication.logger.error("Cannot disconnect socket connection");
        }
    }
}
