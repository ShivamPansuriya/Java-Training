package web.sockjs;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions;

public class ServerEcho extends AbstractVerticle
{
    public static void main(String[] args)
    {
        Vertx.vertx().deployVerticle("web.sockjs.ServerEcho").onSuccess(res-> System.out.println("verticle deployed"));
    }

    @Override
    public void start() throws Exception
    {
        var router = Router.router(vertx);

        var options = new SockJSHandlerOptions().setHeartbeatInterval(2000);

        var sockjs = SockJSHandler.create(vertx,options);


        router.route("/sockjs/*").subRouter(sockjs.socketHandler(socket -> {
            socket.handler(buffer -> {

                //read and modify
                var newmessage = buffer.toString() + "[client]";

                System.out.println("Received message from client: " + buffer.toString());

                //send message
                socket.write(newmessage);
            });
        })
        );

        vertx.createHttpServer(new HttpServerOptions().setHost("10.20.40.239").setPort(8080)).requestHandler(router).listen()
                .onComplete(httpServerAsyncResult -> {
                    if(httpServerAsyncResult.succeeded())
                    {
                        System.out.println("http server started on 8080");
                    }
                });
    }
}
