package org.example.Client;

public class ClientApplication
{
    public static void main(String[] args)
    {
        int port = 6000;
        String serverIP = "localhost";

        ClientSocket clientSocket = new ClientSocket(serverIP,port);

        TerminalUI terminalUI = new TerminalUI(clientSocket);
        terminalUI.start();

    }
}
