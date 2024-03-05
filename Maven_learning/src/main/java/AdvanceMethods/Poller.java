package AdvanceMethods;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Poller {

    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            // Create a poller with capacity for 2 sockets
            ZMQ.Poller items = context.createPoller(2);

            // Create a PULL socket for receiving messages
            ZMQ.Socket receiver = context.createSocket(SocketType.PULL);
            receiver.connect("tcp://localhost:5557");
            // Register the PULL socket with the poller for POLLIN events
            items.register(receiver, ZMQ.Poller.POLLIN);

            // Create a SUB socket for subscribing to weather updates
            ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
            subscriber.connect("tcp://localhost:5556");
            subscriber.subscribe("10001 ".getBytes(ZMQ.CHARSET));
            // Register the SUB socket with the poller for POLLIN events
            items.register(subscriber, ZMQ.Poller.POLLIN);

            while (!Thread.currentThread().isInterrupted()) {
                // Poll for events with a 1-millisecond timeout
                items.poll(1);

                // Check if a message arrived on the PULL socket
                if (items.pollin(0)) {
                    byte[] message = receiver.recv(0);
                    System.out.println("Received message from PULL socket: " + new String(message, ZMQ.CHARSET));
                }

                // Check if a message arrived on the SUB socket
                if (items.pollin(1)) {
                    byte[] message = subscriber.recv(0);
                    System.out.println("Received weather update from SUB socket: " + new String(message, ZMQ.CHARSET));
                }
            }
        }
    }
}

