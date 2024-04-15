package web.Routers;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class SimpleResponse
{
    public static void main(String[] args)
    {
        var vertx = Vertx.vertx();

        var router = Router.router(vertx);

        router.get("/")
                .handler(ctx ->{
                    ctx.response().end("<h1>hello from home </h1>");
                });

        router.get("/library")
                .handler(ctx ->{
                    ctx.response()
                            .end("<div><h2>hello from library </h2></div>");
                });

        vertx.createHttpServer().requestHandler(router).listen(8080);
    }
}
