package web.Routers;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class RouterOrder
{
    public static void main(String[] args)
    {
        var vertx = Vertx.vertx();

        var router = Router.router(vertx);

        router.route("/home").order(1).handler(ctx->{
            ctx.response().write("router1");
            ctx.next();
        });

        router.route("/home").order(0).handler(ctx->{

            ctx.response().setChunked(true);

            ctx.response().write("router2");

            ctx.next();
        });

        router.route("/home").order(2).handler(ctx->{

            ctx.response().end("router3");
        });

        vertx.createHttpServer().requestHandler(router).listen(8080);
    }
}
