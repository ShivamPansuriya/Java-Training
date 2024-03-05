package REQ_REP.BasicImplementation;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Client
{
    public static void main(String[] args)
    {
        try(ZContext contex = new ZContext()){

            ZMQ.Socket clientSocket = contex.createSocket(SocketType.REQ);

            clientSocket.connect("tcp://localhost:5555");

            String sendMessage = "Hello server from main";
            clientSocket.send(sendMessage.getBytes());

            byte[] recevedMessage = clientSocket.recv(0);
            System.out.println("(main)Message from server: "+ new String(recevedMessage));

        }

        new Thread(()->{
            try(ZContext contex = new ZContext()){

                ZMQ.Socket clientSocket = contex.createSocket(SocketType.REQ);

                clientSocket.connect("tcp://localhost:5555");

                String sendMessage = "Hello server from thread";
                clientSocket.send(sendMessage.getBytes());

                byte[] recevedMessage = clientSocket.recv(0);
                System.out.println("(Thread)Message from server: "+ new String(recevedMessage));

            }
        }).start();
    }
}
