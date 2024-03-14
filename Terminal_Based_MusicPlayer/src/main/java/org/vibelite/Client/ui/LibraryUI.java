package org.vibelite.Client.ui;

import org.vibelite.Client.handler.ClientRequestHandler;
import static org.vibelite.Client.utils.Constants.*;

public class LibraryUI
{
    private final ClientRequestHandler clientRequestHandler;

    LibraryUI(ClientRequestHandler clientRequestHandler)
    {
        this.clientRequestHandler = clientRequestHandler;
    }

    public void library()
    {
        while(true)
        {
            // list all files that are available in library;
            var audioFiles = clientRequestHandler.requestLibrary();

            for(var audioFile : audioFiles)
            {
                System.out.println(audioFile.substring(0,audioFile.length()-4));
            }

//            Scanner input = new Scanner(System.in);
            System.out.println("-------------------------------");
            System.out.println(TAB + TAB +TAB +"LIBRARY");
            System.out.println("-------------------------------");

            System.out.println("0) EXIT library");

            System.out.print("Enter the music name or 0 to exit: ");

            String audioName = INPUT.nextLine();

            // exit from library
            if(audioName.equals("0"))
            {
                break;
            }
            // validate audio name
            while(!audioFiles.contains(audioName + ".wav"))
            {
                System.out.print("Please Enter valid audio file name or 0 to exit: ");

                audioName = INPUT.nextLine();

                if(audioName.equals("0"))
                    break;
            }

            //request audio file from server
            if(!audioName.equals("0"))
                clientRequestHandler.requestAudio(audioName + ".wav", "library");
        }
    }
}
