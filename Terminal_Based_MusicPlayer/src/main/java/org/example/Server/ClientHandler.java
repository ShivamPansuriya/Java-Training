package org.example.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable
{
    private final Socket clientSocket;
    ClientHandler(Socket socket){
        this.clientSocket = socket;
    }

    @Override
    public void run()
    {
        try(PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(),true);){
            printWriter.println("Hello Client");

        }catch(IOException e){
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                clientSocket.close();
            } catch(IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}
