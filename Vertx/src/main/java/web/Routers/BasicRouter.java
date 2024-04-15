package web.Routers;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class BasicRouter
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        var server = vertx.createHttpServer();

        var router = Router.router(vertx);

        router.route().handler(rctx->{
            HttpServerResponse response = rctx.response();
            response.putHeader("content-type", "text/plain")
                    .setChunked(true); //if not setchunked then we can set only one response i.e.(only write response.end() ) response.write will not work

            response.write("hello from Vertx-Web");
            response.end();
        });

        server.requestHandler(router).listen(8080);
    }
}
