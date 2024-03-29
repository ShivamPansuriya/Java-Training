package org.vibelite.server.connection;

import org.vibelite.server.ServerApplication;
import org.vibelite.server.handler.ClientHandler;
import static org.vibelite.server.utils.Config.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerSockets
{
    private final int port;

    private final ThreadPoolExecutor executorService;

    public ServerSockets(int port)
    {
        this.port = port;

        this.executorService = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TIME_UNIT, new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY));
    }

    public void start()
    {
        try(var serverSocket = new ServerSocket(port))
        {

            ServerApplication.logger.info("server started on port: {}" , port);

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
