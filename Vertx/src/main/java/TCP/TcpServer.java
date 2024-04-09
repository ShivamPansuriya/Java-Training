package TCP;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;

import java.nio.Buffer;

public class TcpServer
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        NetServer netServer = vertx.createNetServer(new NetServerOptions().setPort(5555));

        netServer.connectHandler(socket -> {
            socket.handler(buffer -> {

                System.out.println("I received some bytes: " + buffer.length());
                socket.write("hello client \n");
            });
        });

        netServer.listen(res -> {
            if(res.succeeded())
            {
                System.out.println("server created and listening to port: " + res.result().actualPort());
            }
            else
            {
                System.out.println("Server creation fails");
            }
        });

    }
}
