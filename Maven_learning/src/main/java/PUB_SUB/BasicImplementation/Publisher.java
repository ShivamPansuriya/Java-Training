package PUB_SUB.BasicImplementation;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Publisher {

    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            // Create a PUB socket
            ZMQ.Socket publisher = context.createSocket(SocketType.PUB);
//            publisher.setHWM(10);
            // Bind the socket to a port
            publisher.bind("tcp://*:5556");

            while (!Thread.currentThread().isInterrupted()) {
                // Send a message
                String message = "New message from publisher!";
                publisher.send(message.getBytes(ZMQ.CHARSET), 0);

                // Wait for a while before sending the next message
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

