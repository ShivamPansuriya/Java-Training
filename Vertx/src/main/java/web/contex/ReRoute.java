package web.contex;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;


public class ReRoute extends AbstractVerticle
{
    public static void main(String[] args)
    {
        Vertx.vertx().deployVerticle("web.contex.ReRoute");
    }

    @Override
    public void start() throws Exception
    {
        var router = Router.router(vertx);

        router.get("/some/path").handler(ctx -> {

            ctx.put("foo", "bar");
            ctx.next();

        });

        router
                .get("/some/path/B")
                .handler(ctx -> {
                    System.out.println("ji");
                    ctx.next();
                });

        router
                .get("/some/path")
                .handler(ctx -> ctx.reroute("/some/path/B"));

        router
                .get("/some/path/B")
                .handler(ctx -> {
                    System.out.println("hii");
                    ctx.end("second");
                });


        vertx.createHttpServer(new HttpServerOptions().setHost("10.20.40.239").setPort(8080)).requestHandler(router).listen()
                .onComplete(httpServerAsyncResult -> {
                    if(httpServerAsyncResult.succeeded())
                    {
                        System.out.println("http server started on 8080");
                    }
                });
    }
}
