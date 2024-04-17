package web.contex;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class BodyHandlerlimit
{
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        // Create a BodyHandler and set a body limit
        BodyHandler bodyHandler = BodyHandler.create().setBodyLimit(1024*1024*1); // Limit set to 1KB

        // Add the BodyHandler to handle incoming request bodies
        router.route().handler(bodyHandler);

        // Define a route that consumes the body
        router.post("/process").handler(ctx -> {
            // Handling the incoming POST request with body
            System.out.println(ctx.body().toString());
            ctx.response().setStatusCode(200).end("Received body: " + ctx.getBodyAsString());
        });

        // Start the server
        server.requestHandler(router).listen(8080);
    }
}
