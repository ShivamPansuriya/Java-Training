package web.session;

import io.vertx.core.Vertx;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
public class Cookies
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.get("/set-cookie").handler(context -> {
            context.response().addCookie(Cookie.cookie("my-cookie","cookie-name")).end("Cookies set");
        });
        router.get("/get-cookie").handler(context -> {
            Cookie cookie = context.request().getCookie("my-cookie");
            if(cookie!=null)
            {
                String cookieValue = cookie.getValue();
                context.json(new JsonObject().put("cookies", new JsonObject().put(cookie.getName(), cookieValue)));
            }
            else
            {
                context.response().end("Cookie not found");
            }
        });
        router.get("/delete-cookie").handler(ctx->{
            Cookie cookie = ctx.request().getCookie("my-cookie");
            if(cookie!=null)
            {
                cookie.setMaxAge(0);
            }

            ctx.response().end("Deletion completed");
        });
        server.requestHandler(router).listen(8000);
    }
}
