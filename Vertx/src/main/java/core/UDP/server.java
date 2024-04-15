package core.UDP;

import io.vertx.core.Vertx;
import io.vertx.core.datagram.DatagramSocketOptions;

public class server
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        var socket = vertx.createDatagramSocket(new DatagramSocketOptions());

        vertx.setPeriodic(2000, id->
        {
            socket.send("[server]: hello client", 1234,"localhost")
                    .onComplete(res->
                    {
                        if(res.succeeded())
                        {
                            System.out.println("message send");
                        }
                        else
                        {
                            System.out.println("cannot send message");
                        }
                    });
        });
    }
}
