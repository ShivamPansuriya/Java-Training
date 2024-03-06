package PUB_SUB.Task;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Publisher
{
    public static void main(String[] args)
    {
        try(ZContext context = new ZContext()){
            ZMQ.Socket[] publishers = new ZMQ.Socket[3];

            for(int i = 0; i < 3; i++)
            {
                publishers[i] = context.createSocket(SocketType.PUB);

                publishers[i].bind("tcp://*:555"+i);
            }

            ZMQ.Socket puller = context.createSocket(SocketType.PULL);
            puller.connect("tcp://localhost:6660");
            puller.connect("tcp://localhost:6661");
            puller.connect("tcp://localhost:6662");
            Thread.sleep(2000);
            while(true){
                for(int i=0; i<3; ++i){
                    String sendMessage = "message from publisher port :";

                    publishers[i].sendMore(sendMessage.getBytes(ZMQ.CHARSET));
                    publishers[i].send(("555"+i).getBytes(ZMQ.CHARSET));
                    System.out.println("hi");

                    byte[] Ack = puller.recv(0);
                    System.out.println("555"+i + new String(Ack,ZMQ.CHARSET));

                    Thread.sleep(1000);
                }
            }
        } catch(InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
}
