package REQ_REP.HelloWorld;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class hwserver
{
    public static void main(String[] args) throws Exception
    {
        try(ZContext context = new ZContext())
        {
            // Socket to talk to clients
            ZMQ.Socket socket = context.createSocket(SocketType.REP);
            socket.bind("tcp://127.0.0.1:5555");

            while(!Thread.currentThread().isInterrupted())
            {
                byte[] reply = socket.recv(0);
                System.out.println("Received " + ": [" + new String(reply, ZMQ.CHARSET) + "]");

                Thread.sleep(1000); //  Do some 'work'

                String response = "world";
                socket.send(response.getBytes(ZMQ.CHARSET), 0);
            }
        }
    }
}