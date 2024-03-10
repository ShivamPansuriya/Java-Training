package org.example.Server;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

public class ClientSession
{
    private Queue<File> playbackQueue;

    ClientSession(){
        this.playbackQueue = new LinkedList<>();
    }



}
