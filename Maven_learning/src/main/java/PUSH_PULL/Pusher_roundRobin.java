package PUSH_PULL;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Pusher_roundRobin
{
    public static void main(String[] args)
    {
        try (ZContext context = new ZContext()) {
            // Create a PUSH socket
            ZMQ.Socket pusher = context.createSocket(SocketType.PUSH);
            pusher.bind("tcp://*:5558");
            pusher.bind("tcp://*:5559");
            pusher.bind("tcp://*:5557");

            while (!Thread.currentThread().isInterrupted()) {
                for (int i = 0; i < 5; i++) {
                    String message = "Message " + (i + 1);
                    pusher.send(message.getBytes(ZMQ.CHARSET), 0);
                    System.out.println("Sent message: " + message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
