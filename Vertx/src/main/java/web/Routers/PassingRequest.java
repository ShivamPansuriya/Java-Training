package web.Routers;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class PassingRequest
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        var server = vertx.createHttpServer();

        var router = Router.router(vertx);

        var route = router.route("/");

        route.handler(ctx -> {

            var response = ctx.response();

            response.putHeader("Content-Type", "text/event-stream")
                    .putHeader("Cache-Control", "no-cache")
                    .setChunked(true);

            response.write("route1\n");

            ctx.vertx().setTimer(5000, tid -> ctx.next());
        });

        route.handler(ctx -> {

            var response = ctx.response();

            response.write("route2\n");

            ctx.vertx().setTimer(5000, tid -> ctx.next());
        });

        route.handler(ctx -> {

            var response = ctx.response();
            response.write("route3");

            ctx.response().end();
        });

        server.requestHandler(router).listen(8080)
                .onComplete(httpServerAsyncResult -> {
                    if(httpServerAsyncResult.succeeded())
                    {
                        System.out.println("server listening at port "+ server.actualPort());
                    }
                    else
                    {
                        System.out.println("error");
                    }
                });

    }
}
