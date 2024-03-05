package PUB_SUB.Task;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Subscriber
{
    public static void main(String[] args)
    {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
            subscriber.connect("tcp://localhost:5550");
            subscriber.connect("tcp://localhost:5551");
            subscriber.connect("tcp://localhost:5552");

            subscriber.subscribe("".getBytes(ZMQ.CHARSET));

            while (!Thread.currentThread().isInterrupted()) {
                // Receive a message
                byte[] message = subscriber.recv();
                System.out.println("Received message: " + new String(message, ZMQ.CHARSET));
                Thread.sleep(1000);
            }
        } catch(InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
}
