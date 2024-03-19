package org.vibelite.Server.connection;

import org.json.JSONObject;
import org.vibelite.Server.handler.ClientHandler;
import org.vibelite.client.eventdriven.ClientApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServerSockets
{
    private final int port;

    private final ThreadPoolExecutor executorService;

    public ServerSockets()
    {
        var config = loadConfig();

        this.port = config.getInt("PORT");

        this.executorService = new ThreadPoolExecutor(config.getInt("CORE_POOL_SIZE"), config.getInt("MAXIMUM_POOL_SIZE"), config.getInt("KEEP_ALIVE_TIME"), TimeUnit.SECONDS, new ArrayBlockingQueue<>(config.getInt("BLOCKING_QUEUE_CAPACITY")));
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

    public void start()
    {
        try(var serverSocket = new ServerSocket(port))
        {

            System.out.println("server started on port: " + port);

            while(true)
            {
                var clientSocket = serverSocket.accept();

                var clientHandler = new ClientHandler(clientSocket);

                executorService.execute(clientHandler);
            }
        }
        catch(IOException e)
        {
            System.out.println("(ERROR) Socket is already in use cannot create same socket: " + e.getMessage());
        }
    }

    // TODO :- either use it or remove it... : Done (removed)
}
