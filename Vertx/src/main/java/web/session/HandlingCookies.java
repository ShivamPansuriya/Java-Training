package web.session;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;

public class HandlingCookies extends AbstractVerticle
{
    public static void main(String[] args)
    {
        Vertx.vertx().deployVerticle("web.session.HandlingCookies");
    }

    @Override
    public void start() throws Exception
    {
        var router = Router.router(vertx);

        router.get("/home").handler(ctx->{
            ctx.put("first",ctx.request().remoteAddress().toString());

            ctx.response().addCookie(Cookie.cookie("shivam","true"));

            ctx.response().end("hello");
        });

        router.get("/home/test").handler(ctx->{
//            var cookie = ctx.request().getCookie("shivam").getValue();

            var cookie  = ctx.request().getCookie("shivam");

            System.out.println(cookie);

            if(! (cookie==null))
            {
                cookie.setMaxAge(0);
            }
//            if(cookie.equals("true"))
//            {
//                ctx.response().removeCookie("shivam");
                ctx.response().end("welcome to test");
//            }
        });

        router.get("/get").handler(ctx->{
            var cookie  = ctx.request().getCookie("shivam");

            if(cookie!=null)
            {
                ctx.response().end("cookies found");
            }
            else {
                ctx.response().end("cookies not found");
            }

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

