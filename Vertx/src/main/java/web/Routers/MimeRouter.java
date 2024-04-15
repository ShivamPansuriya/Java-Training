package web.Routers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class MimeRouter extends AbstractVerticle
{
    public static void main(String[] args)
    {
        Vertx.vertx().deployVerticle("web.Routers.MimeRouter");
    }

    public void start (Promise<Void> promise) {

        Router router = Router.router(vertx);
        Route route = router.route().consumes("text/html").method(HttpMethod.POST);

        route.handler(ctx -> {

            //                ctx.request().bodyHandler(buffer -> {
            //                    String body = buffer.toString();
            //                    System.out.println(body);
            //                    ctx.response().end(body);
            //                });

            Future<Buffer> bodyFuture = ctx.request().body();
            bodyFuture.onSuccess(buffer ->
            {
                String body = buffer.toString();
                System.out.println(body);
                ctx.response().end(body);
            }).onFailure(err ->
            {
                ctx.fail(err);
            });
        });

        vertx.createHttpServer().requestHandler(router).listen(8080, "localhost").onComplete(res -> {
            if(res.succeeded())
            {
                System.out.println("Server is now listening");
                promise.complete();
            }
            else
            {
                System.out.println("Failed to bind!");
                promise.fail(res.cause());
            }
        });

    }
}
