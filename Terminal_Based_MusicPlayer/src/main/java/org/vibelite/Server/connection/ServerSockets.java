package org.vibelite.Server.connection;

import org.vibelite.Server.handler.ClientHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.ServerSocket;

public class ServerSockets
{
    private final int port;

    private final ExecutorService executorService;

    public ServerSockets(int port)
    {
        this.port = port;

        this.executorService = Executors.newCachedThreadPool();
    }

    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(port);){

            System.out.println("server started on port: " + port);

            while(true)
            {
                Socket clientSocket = serverSocket.accept();

                ClientHandler clientHandler = new ClientHandler(clientSocket);

                executorService.execute(clientHandler);
            }
        } catch(IOException e)
        {
            System.out.println("(ERROR) Socket is already in use cannot create same socket: " + e.getMessage());
        }
    }

    public void stop()
    {
        executorService.shutdown();
    }
}
