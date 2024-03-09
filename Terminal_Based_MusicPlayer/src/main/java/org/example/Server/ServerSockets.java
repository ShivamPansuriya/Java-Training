package org.example.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.ServerSocket;

public class ServerSockets
{
    private final int port;

    private final ExecutorService executorService;

    ServerSockets(int port){
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
            throw new RuntimeException(e);
        }
    }

    public void stop()
    {
        executorService.shutdown();
    }
}
