package EventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.stream.Collectors;

public class SensorData extends AbstractVerticle {

    private final HashMap<String, Double> lastValues = new HashMap<>();

    @Override
    public void start() {
        EventBus bus = vertx.eventBus();

        bus.<JsonObject>consumer("sensor.updates", msg ->{
            JsonObject json = msg.body();
            lastValues.put(json.getString("id"), json.getDouble("temp"));
        });

        bus.<JsonObject>consumer("sensor.average", msg->{
            double avg = lastValues.values().stream()
                    .collect(Collectors.averagingDouble(Double::doubleValue));
            JsonObject json = new JsonObject().put("average", avg);
            vertx.setTimer(4000,r->msg.reply(json));
        });
    }
}