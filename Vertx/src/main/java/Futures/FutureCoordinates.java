package Futures;
import io.vertx.core.Vertx;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.net.NetServer;
import io.vertx.core.CompositeFuture;

public class FutureCoordinates {
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        HttpServer httpServer = vertx.createHttpServer();

        httpServer.requestHandler(request ->
        {
            request.response().end("Hello from HTTP server!");
        });

        NetServer netServer = vertx.createNetServer();

        netServer.connectHandler(socket ->
        {
            socket.write("Hello from NET server!");
            socket.close();
        });

        Future<HttpServer> httpServerFuture = httpServer.listen();

        Future<NetServer> netServerFuture = netServer.listen();

        Future<CompositeFuture> compositeFuture = Future.all(httpServerFuture, netServerFuture);

        compositeFuture.onComplete(ar -> {
            if (ar.succeeded()) {

                CompositeFuture result = ar.result();

                HttpServer httpServerInstance = result.resultAt(0);

                NetServer netServerInstance = result.resultAt(1);

                System.out.println("HTTP server started on port: " + httpServerInstance.actualPort());

                System.out.println("NET server started on port: " + netServerInstance.actualPort());

            } else
            {
                Throwable cause = ar.cause();

                System.err.println("Failed to start servers: " + cause.getMessage());
            }

            vertx.close();
        });
    }
}