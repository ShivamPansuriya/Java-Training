package AdvanceMethods;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class AchieveDynamicPoller
{

    public static void main(String[] args)
    {
        try(ZContext context = new ZContext())
        {
            // Create a poller with initial capacity
            ZMQ.Poller items = context.createPoller(2);

            // Initial socket (assuming a PULL socket)
            ZMQ.Socket receiver = context.createSocket(SocketType.PULL);
            receiver.connect("tcp://localhost:5557");
            items.register(receiver, ZMQ.Poller.POLLIN);

            while(!Thread.currentThread().isInterrupted())
            {
                // Poll for events
                items.poll(1);

                // Check message on existing socket
                if(items.pollin(0))
                {
                    byte[] message = receiver.recv(0);
                    System.out.println("Received message from existing socket: " + new String(message, ZMQ.CHARSET));
                }

                // Logic to dynamically add or remove sockets
                // ... (replace with your specific logic)

                // Update poller with any changes
                updatePoller(items);
            }
        }
    }

    private static void updatePoller(ZMQ.Poller items)
    {
        // Implement your logic to check for new sockets or removal requests
        // This could involve checking databases, user input, or other sources
        try(ZContext context = new ZContext())
        {
            // Example: Add a new PULL socket if a specific condition is met
            ZMQ.Socket newSocket = context.createSocket(SocketType.PULL);
            if(true)
            {                          // condition to add new socket

                newSocket.connect("tcp://localhost:5558"); // Replace with new socket address
                items.register(newSocket, ZMQ.Poller.POLLIN);
            }

            // Example: Remove a socket if a specific condition is met
            if(true)
            {                                       // condition to remove socket
                int indexToRemove = 0;// determine index of socket to remove
                items.unregister(newSocket);
            }
        }
    }
}


