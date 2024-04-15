package core.EventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;

import java.util.Random;
import java.util.UUID;

public class HeatSensor extends AbstractVerticle
{
    private final Random random = new Random();
    private final String sensorId = UUID.randomUUID().toString();
    private double temperature = 21.0;

    @Override
    public void start() throws Exception
    {
        scheduleNextUpdate();
    }

    public void scheduleNextUpdate()
    {
        vertx.setTimer(random.nextInt(4000) + 1000 , id->
        {
            temperature = temperature + (delta() / 10);

            JsonObject payload = new JsonObject()
                    .put("id", sensorId)
                    .put("temp", temperature);

            vertx.eventBus().publish("sensor.updates", payload);

            scheduleNextUpdate();
        });
    }

    private double delta() {
        if (random.nextInt() > 0) {
            return random.nextGaussian();
        } else {
            return -random.nextGaussian();
        }
    }
}
