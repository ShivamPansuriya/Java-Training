package InterLanguage;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Puller
{

    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            // Create a PULL socket
            ZMQ.Socket puller = context.createSocket(SocketType.PULL);
            // Connect to the pusher (optional)
             puller.connect("tcp://localhost:5558"); // Uncomment if pusher runs on different machine
            // Bind the socket to a port (useful if puller runs on different machine)
            // puller.bind("tcp://*:5558");  // Uncomment if pusher binds

            while (!Thread.currentThread().isInterrupted()) {
                // Receive messages
                byte[] message = puller.recv(0);
                System.out.println("(Main - 5558)Received message: " + new String(message, ZMQ.CHARSET));
            }
        }



    }
}

