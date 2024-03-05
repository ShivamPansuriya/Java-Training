package REQ_REP.BasicImplementation;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Server
{
    public static void main(String[] args)
    {
        try(ZContext contex = new ZContext()){
            ZMQ.Socket server = contex.createSocket(SocketType.REP);

            server.bind("tcp://*:5555");

            while(!Thread.currentThread().isInterrupted()){
                byte[] receve = server.recv();
                System.out.println("message from client: "+ new String(receve));

                String sendMessage = "hello client";
                server.send(sendMessage.getBytes());
            }
        }
    }
}
