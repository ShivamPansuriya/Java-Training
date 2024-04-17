package web.sockjs;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.bridge.BridgeEventType;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

public class EventBusBridgeEvent extends AbstractVerticle
{
    public static void main(String[] args)
    {
        Vertx.vertx().deployVerticle("web.sockjs.EventBusBridgeEvent").onSuccess(res-> System.out.println("verticle deployed"));
    }

    @Override
    public void start() throws Exception
    {
        var router = Router.router(vertx);

        var options = new SockJSBridgeOptions()
                .addInboundPermitted(new PermittedOptions().setAddress("someAddress"))
                //other permitted options::- setAddress() and setmatch()-> works only on jason message
                .addOutboundPermitted(new PermittedOptions().setAddressRegex(".*"));


        var sockjs = SockJSHandler.create(vertx);


        router.route("/eventbus/*").subRouter(sockjs
                .bridge(options, be ->
                {
                    System.out.println(be.type());
                    System.out.println(be.getRawMessage());

                    if(be.type() == BridgeEventType.PUBLISH || be.type() == BridgeEventType.SEND){
                        System.out.println("This event is not allowed");
                        be.complete(false);
                    }
                    be.complete(true);
                }));

        vertx.eventBus().consumer("someAddress", message -> {
            // Handle incoming message from client
            System.out.println("Received message from client: " + message.body());
        });

        vertx.setPeriodic(5000, timerId -> {
            vertx.eventBus().publish("someAddresstosend", "Hello from server!");
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
