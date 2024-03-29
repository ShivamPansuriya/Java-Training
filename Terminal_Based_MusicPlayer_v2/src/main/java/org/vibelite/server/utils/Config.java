package org.vibelite.server.utils;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Config
{

    private Config()
    {}

    public static final int PORT ;

    public static final int CORE_POOL_SIZE; // Number of core threads

    public static final int MAXIMUM_POOL_SIZE; // Maximum number of threads

    public static final long KEEP_ALIVE_TIME; // Time (in seconds) for idle threads to wait before termination

    public static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    public static final int BLOCKING_QUEUE_CAPACITY;

//    public static final BlockingQueue<Runnable> WORKERS = new ArrayBlockingQueue<>(100); // Queue capacity

    static
    {
        var config = loadConfig();

        PORT = config.getInt("PORT");

        CORE_POOL_SIZE = config.getInt("CORE_POOL_SIZE");

        MAXIMUM_POOL_SIZE = config.getInt("MAXIMUM_POOL_SIZE");

        KEEP_ALIVE_TIME = config.getInt("KEEP_ALIVE_TIME");

        BLOCKING_QUEUE_CAPACITY = config.getInt("BLOCKING_QUEUE_CAPACITY");
    }

    private static JSONObject loadConfig()
    {
        try(var inputStream = new FileInputStream("config.json"))
        {
            var buffer = inputStream.readAllBytes();

            var jsonText = new String(buffer);

            return new JSONObject(jsonText);

        }
        catch(IOException e)
        {
            System.out.println("Error reading configuration file: ");

            return new JSONObject();
        }
    }

}
