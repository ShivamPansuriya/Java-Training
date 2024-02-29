package SocketProgramming;

import java.io.*;
import java.net.*;

public class Proxy {
    private Socket clientSocket;
    private Socket serverSocket;

    public Proxy(Socket clientSocket, Socket serverSocket) {
        this.clientSocket = clientSocket;
        this.serverSocket = serverSocket;
    }

    public void startProxy() {
        try {
            BufferedReader clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter clientOut = new PrintWriter(clientSocket.getOutputStream(), true);

            BufferedReader serverIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            PrintWriter serverOut = new PrintWriter(serverSocket.getOutputStream(), true);

            String clientMessage;
            while ((clientMessage = clientIn.readLine()) != null) {
                // Intercept and modify client message here if needed
                System.out.println("Proxy: Client sent: " + clientMessage);

                // Forward client message to server
                serverOut.println(clientMessage);

                // Receive response from server
                String serverResponse = serverIn.readLine();
                System.out.println("Proxy: Server responded: " + serverResponse);

                // Forward server response to client
                clientOut.println(serverResponse);
            }

            clientIn.close();
            clientOut.close();
            serverIn.close();
            serverOut.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket proxyServerSocket = new ServerSocket(12346);
            System.out.println("Proxy listening on port 12346...");

            while (true) {
                Socket clientSocket = proxyServerSocket.accept();

                // Connect the proxy to the server
                Socket serverSocket = new Socket("localhost", 12345);

                Proxy echoProxy = new Proxy(clientSocket, serverSocket);
                new Thread(() -> echoProxy.startProxy()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

