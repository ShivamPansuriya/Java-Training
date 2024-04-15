package web.Routers;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class BlockingHandler_Post
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

        router.post("/").handler(ctx -> {
            ctx.request().setExpectMultipart(true);

            ctx.next();
        }).blockingHandler(ctx -> {
            System.out.println("Performing a blocking operation...");
            try
            {
                Thread.sleep(5000);
            } catch(InterruptedException e)
            {
                throw new RuntimeException(e);
            }

            // Set the response
            ctx.response().setStatusCode(200).end("Blocking operation completed");
        });

        vertx.createHttpServer().requestHandler(router).listen(8080).onComplete(ar -> {
            if(ar.succeeded())
            {
                System.out.println("Server started on port 8080");
            }
            else
            {
                System.out.println("Failed to start server: " + ar.cause().getMessage());
            }
        });
    }
}
