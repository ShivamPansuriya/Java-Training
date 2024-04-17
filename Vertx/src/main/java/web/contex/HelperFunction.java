package web.contex;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;


public class HelperFunction extends AbstractVerticle
{
    public static void main(String[] args)
    {
        Vertx.vertx().deployVerticle("web.contex.HelperFunction");
    }

    @Override
    public void start() throws Exception
    {
        var router = Router.router(vertx);

        router.get("/home").handler(ctx->{
            vertx.fileSystem().readFile("/home/shivam/Downloads/gurupurnima.pdf",res->{
                if(res.succeeded())
                {
                    var file = res.result();
                    ctx.attachment("gurupurnima.pdf").response().putHeader("Content-Type", "application/pdf").end(file);
                }
                else
                {
                    ctx.response().end(res.cause().getMessage());
                }
            });
        });

        router.get("/test/:query").handler(ctx->{
            ctx.redirect(String.format("https://www.google.com/search?q=%s",ctx.pathParam("query")));
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
