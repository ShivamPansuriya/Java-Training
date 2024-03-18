package org.vibelite.client.eventdriven;

import org.vibelite.client.eventdriven.ui.TerminalUI;

import java.io.IOException;

public class ClientApplication
{
    public static void main(String[] args)
    {
        // creating main ui object and redirecting client to ui
        var terminalUI = new TerminalUI();

        try
        {
            new TerminalUI().start();//terminalUI.start();
        }
        catch(IOException e)
        {
            System.out.println("(ERROR) cannot close the connection");
        }
        catch(NullPointerException e)
        {
            System.out.println("server is down");
            org.vibelite.client.eventdriven.ClientApplication.main(null);
        }
    }
}
