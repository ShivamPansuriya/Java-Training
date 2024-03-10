package org.example.Client;

import java.util.Scanner;

public class TerminalUI
{
    private final ClientSocket clientSocket;

    private ClientRequestHandler clientRequestHandler;

    private final Scanner input;

    TerminalUI(ClientSocket clientSocket, ClientRequestHandler clientRequestHandler)
    {
        this.clientSocket = clientSocket;

        this.clientRequestHandler = clientRequestHandler;

        this.input = new Scanner(System.in);
    }

    public void start()
    {
        while(true)
        {
            String commandOption = "Entre Command: \n1) Playlist \n2) Library \n3) Exit from application";

            System.out.println(commandOption);

            String command = input.nextLine();

            switch(command)
            {
                case "1":
                    System.out.println("Playlist");
                    break;

                case "2":
                    System.out.println("Library");

                    library();

                    break;

                case "3":
                    System.out.println("Exit");

                    clientRequestHandler.closeConnection();

                    clientSocket.disconnect();

                    return;

                default:
                    System.out.println("entre correct option");

                    break;
            }
        }
    }

    public void library()
    {
        clientRequestHandler.requestLibrary();

        while(true)
        {
            String audioName = input.nextLine();

            boolean isValid = clientRequestHandler.requestAudio(audioName);
            while(!isValid){
                System.out.print("Please entre valid audio file name: ");

                audioName = input.nextLine();

                isValid = clientRequestHandler.requestAudio(audioName);
            }

        }
    }
}
