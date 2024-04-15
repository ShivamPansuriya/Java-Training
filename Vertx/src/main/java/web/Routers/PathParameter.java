package web.Routers;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

public class PathParameter
{
    public static void main(String[] args)
    {
        var vertx = Vertx.vertx();

        var router = Router.router(vertx);

        router.route(HttpMethod.GET, "/home/v1/:product/:ID").handler(ctx->{
           var product = ctx.pathParam("product");
           var id = ctx.pathParam("ID");

            ctx.response().end(product +"\t" + id);
        });

        vertx.createHttpServer().requestHandler(router).listen(8080);
    }
}
