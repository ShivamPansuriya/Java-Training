package web.contex;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;

public class ContexData extends AbstractVerticle
{
    public static void main(String[] args)
    {
        Vertx.vertx().deployVerticle("web.contex.ContexData");
    }

    @Override
    public void start() throws Exception
    {
        var router = Router.router(vertx);

        router.get("/home/*").handler(ctx->{
           ctx.put("first",ctx.request().remoteAddress().toString());
           System.out.println("hi");
           ctx.next();
        });

        router.get("/home/table").handler(ctx->{
            String res = ctx.get("first");
            System.out.println(res);
            ctx.response().end(res);
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
