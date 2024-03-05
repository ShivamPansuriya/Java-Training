package PUSH_PULL;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Pusher {

    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            // Create a PUSH socket
            ZMQ.Socket pusher = context.createSocket(SocketType.PUSH);
            // Connect to the puller (optional)
             pusher.connect("tcp://localhost:5558"); // Uncomment if puller runs on different machine
            // Bind the socket to a port (useful if puller connects)
            pusher.bind("tcp://*:5558");

            while (!Thread.currentThread().isInterrupted()) {
                // Send messages
                for (int i = 0; i < 5; i++) {
                    String message = "Message " + (i + 1);
                    pusher.send(message.getBytes(ZMQ.CHARSET), 0);
                    System.out.println("Sent message: " + message);
                    try {
                        Thread.sleep(1000); // Simulate processing time
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

