package web.sockjs;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions;

public class SockjsWithEventBus extends AbstractVerticle
{
    public static void main(String[] args)
    {
        Vertx.vertx().deployVerticle("web.sockjs.SockjsWithEventBus").onSuccess(res-> System.out.println("verticle deployed"));
    }

    @Override
    public void start() throws Exception
    {
        var router = Router.router(vertx);

        var options = new SockJSHandlerOptions().setHeartbeatInterval(2000).setRegisterWriteHandler(true);

        var sockjs = SockJSHandler.create(vertx,options);


        router.route("/sockjs/*").subRouter(sockjs.socketHandler(socket -> {
                    socket.handler(buffer -> {
                        vertx.eventBus().send(socket.writeHandlerID(), buffer);
                        System.out.println("published message: " + buffer.toString());
                    });
                })
        );

        vertx.eventBus().consumer("test",res->{
            System.out.println("Event bus: " + res.body().toString());
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
