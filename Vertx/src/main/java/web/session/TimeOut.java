package web.session;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.TimeoutHandler;

public class TimeOut extends AbstractVerticle
{
    public static void main(String[] args)
    {
        Vertx.vertx().deployVerticle("web.session.TimeOut");
    }

    @Override
    public void start() throws Exception
    {
        var router = Router.router(vertx);

        router.get("/home").handler(TimeoutHandler.create(3000)).handler(ctx->{

            vertx.setTimer(4000,id->{
                if(! ctx.request().isEnded())
                {
                    ctx.put("first", ctx.request().remoteAddress().toString());

                    ctx.response().addCookie(Cookie.cookie("shivam", "true"));

                    ctx.response().end("hello");
                }
            });
        });

        router.route("/*").failureHandler(ctx->{
           ctx.response().end("Time out");
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

