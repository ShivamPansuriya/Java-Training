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

            while(true){
                for(int i=0; i<3; ++i){
                    String sendMessage = "message from publisher port :555"+i;

                    publishers[i].send(sendMessage.getBytes(ZMQ.CHARSET));

                    Thread.sleep(1000);
                }
            }
        } catch(InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
}
