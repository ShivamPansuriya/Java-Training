package Futures;
import io.vertx.core.Vertx;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.net.NetServer;
import io.vertx.core.CompositeFuture;

public class FutureCoordinates {
    public static void main(String[] args) {
        // Create a Vertx instance
        Vertx vertx = Vertx.vertx();

        // Create an HTTP server
        HttpServer httpServer = vertx.createHttpServer();
        // Configure the HTTP server, e.g.:
        httpServer.requestHandler(request -> {
            request.response().end("Hello from HTTP server!");
        });

        // Create a NET server
        NetServer netServer = vertx.createNetServer();
        // Configure the NET server, e.g.:
        netServer.connectHandler(socket -> {
            socket.write("Hello from NET server!");
            socket.close();
        });

        // Start the HTTP and NET servers asynchronously
        Future<HttpServer> httpServerFuture = httpServer.listen();
        Future<NetServer> netServerFuture = netServer.listen();

        // Combine the two Futures using Future.all()
        Future<CompositeFuture> compositeFuture = Future.all(httpServerFuture, netServerFuture);

        // Handle the combined result
        compositeFuture.onComplete(ar -> {
            if (ar.succeeded()) {
                // Both servers started successfully
                CompositeFuture result = ar.result();

                // Access the results of individual Futures
                HttpServer httpServerInstance = result.resultAt(0);
                NetServer netServerInstance = result.resultAt(1);

                System.out.println("HTTP server started on port: " + httpServerInstance.actualPort());
                System.out.println("NET server started on port: " + netServerInstance.actualPort());

                // Perform additional operations or start accepting connections
            } else {
                // At least one server failed to start
                Throwable cause = ar.cause();
                System.err.println("Failed to start servers: " + cause.getMessage());
            }

            // Close the Vertx instance
            vertx.close();
        });
    }
}