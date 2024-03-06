package PUB_SUB.Task;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Subscriber
{
    public static void main(String[] args)
    {
        try (ZContext context = new ZContext())
        {
            ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
            subscriber.connect("tcp://localhost:5550");
            subscriber.connect("tcp://localhost:5551");
            subscriber.connect("tcp://localhost:5552");
            subscriber.subscribe("".getBytes(ZMQ.CHARSET));

            ZMQ.Socket socket = context.createSocket(SocketType.PUSH);
            socket.bind("tcp://localhost:6660");
            socket.bind("tcp://localhost:6661");
            socket.bind("tcp://localhost:6662");
//            Thread.sleep(2000);
            while(!Thread.currentThread().isInterrupted())
            {
                // Receive a message
                byte[] message = subscriber.recv(0);
                byte[] port = subscriber.recv(0);
                System.out.println("Received message: " + new String(message, ZMQ.CHARSET) + new String(port, ZMQ.CHARSET));

                socket.send(("ACK from port: 666"+(char) port[3]).getBytes(), 0);
                                Thread.sleep(1000);
            }

        } catch(InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
}
