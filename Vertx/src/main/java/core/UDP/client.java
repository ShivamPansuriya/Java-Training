package core.UDP;

import io.vertx.core.Vertx;
import io.vertx.core.datagram.DatagramSocketOptions;

public class client
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        var datagramsocket = vertx.createDatagramSocket(new DatagramSocketOptions());

        datagramsocket.handler(datagramPacket ->
        {
            System.out.println(datagramPacket.data() + "\n" + datagramPacket.sender());
        }).listen(1234,"localhost")
                .onComplete(res->
                {
                    if(res.succeeded())
                    {
                        System.out.println("message received");
                    }
                    else
                    {
                        System.out.println("server down");
                    }
                });
    }
}
