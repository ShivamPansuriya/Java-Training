package web.Routers;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class BlockingHandler
{
    public static void main(String[] args)
    {
        var vertx = Vertx.vertx();

        var router = Router.router(vertx);

        router.route().blockingHandler(routingContext -> {
            try
            {
                Thread.sleep(5000);
            } catch (Exception ignore)
            {
            }

            routingContext.next();
        }, false);

        router.route().handler(routingContext ->
        {
            routingContext.response().putHeader("content-type", "text/html").end("Hello World!");
        });

        vertx.createHttpServer().requestHandler(router).listen(8080);
    }
}
