package org.example.Client;

import java.util.Scanner;

public class TerminalUI
{
    private final ClientSocket clientSocket;

    TerminalUI(ClientSocket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public void start()
    {
        clientSocket.connect();

        Scanner input = new Scanner(System.in);

        while(true){
            String commandOption = "Entre Command: \n1) Play music \n2) Pause music \n3) Exit from application";

            System.out.println(commandOption);

            String command = input.nextLine();

            switch(command)
            {
                case "1":
                    System.out.println("Play");

                    break;

                case "2":
                    System.out.println("Pause");

                    break;

                case "3":
                    System.out.println("Exit");

                    clientSocket.disconnect();

                    return;

                default:
                    System.out.println("entre correct option");

                    break;
            }
        }
    }
}
