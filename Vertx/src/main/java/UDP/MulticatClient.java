package UDP;

import io.vertx.core.Vertx;
import io.vertx.core.datagram.DatagramSocketOptions;

public class MulticatClient
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        var socket = vertx.createDatagramSocket(new DatagramSocketOptions());

        socket.handler(packet -> {
                    String message = packet.toString();
                    System.out.println("Received message: " + message);
                })
                .listen(1234, "0.0.0.0")
                .compose(v -> socket.listenMulticastGroup("localhost")) // join the multicast group
                .onComplete(asyncResult -> {
                    if (asyncResult.succeeded()) {
                        System.out.println("Listen succeeded");
                    } else {
                        System.out.println("Listen failed: " + asyncResult.cause().getMessage());
                    }
                });
    }

}

