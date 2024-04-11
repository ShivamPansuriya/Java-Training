package EventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpServer extends AbstractVerticle
{

    private final Logger logger = LoggerFactory.getLogger(HttpServer.class);
    @Override
    public void start() throws Exception
    {
        vertx.createHttpServer().requestHandler(httpServerRequest ->
        {
            if("/".equals(httpServerRequest.path()))
            {
                httpServerRequest.response().sendFile("/home/shivam/Java/Vertx/src/main/java/EventBus/index.html");
            }
            else if("/ss".equals(httpServerRequest.path()))
            {
                ss(httpServerRequest);
            }

        }).listen(config().getInteger("port",8080));
    }

    private void ss(HttpServerRequest httpServerRequest)
    {
        HttpServerResponse response = httpServerRequest.response();

        response
                .putHeader("Content-Type", "text/event-stream")
                .putHeader("Cache-Control", "no-cache")
                .setChunked(true);

        vertx.eventBus().<JsonObject>consumer("sensor.updates", msg->
        {
            logger.info("Data: {} reports a temperature", msg.body());
            response.write("event: update\n");
            response.write("data: " + msg.body() + "\n\n");
        });

        vertx.periodicStream(1000)
        .handler(id -> {
            vertx.eventBus().<JsonObject>request("sensor.average", "",new DeliveryOptions().setSendTimeout(5000), reply -> {
                if (reply.succeeded()) {
                    response.write("event: average\n");
                    response.write("data: " + reply.result().body() + "\n\n");
                }
                else {
                    response.write("event: error\n");
                    response.write("data: operation fail\n\n");
                }
            });
        });

    }
}
