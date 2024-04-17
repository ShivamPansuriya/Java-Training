package web.Routers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;


public class SubRoute extends AbstractVerticle
{
    public static void main(String[] args)
    {
        Vertx.vertx().deployVerticle("web.Routers.SubRoute");
    }

    @Override
    public void start() throws Exception
    {
        var router = Router.router(vertx);

        var router2 = Router.router(vertx);

        router.route("/API/*").subRouter(router2);

        router.get("/some/path").handler(ctx -> {

            ctx.response().end("main router");

        });

        router2.get("/some/path/c").handler(ctx -> {
            System.out.println("ji");
            ctx.response().end("first");
        });

        router2.get("/some/paths").handler(ctx -> ctx.reroute("/API/some/path/B"));

        router2.get("/some/path/B").handler(ctx -> {
            System.out.println("hii");
            ctx.response().end("second");
        });


        vertx.createHttpServer(new HttpServerOptions().setHost("10.20.40.239").setPort(8080)).requestHandler(router).listen().onComplete(httpServerAsyncResult -> {
            if(httpServerAsyncResult.succeeded())
            {
                System.out.println("http server started on 8080");
            }
        });
    }
}
