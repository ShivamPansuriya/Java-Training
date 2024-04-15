package core.json;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class JsonObjectSender extends AbstractVerticle
{
    @Override
    public void start()
    {
        vertx.setPeriodic(2000, handler ->
        {

            Person person = new Person("Raj", 10);

            JsonObject object = JsonObject.mapFrom(person);

            vertx.eventBus().publish("core/json", object);
            System.out.println("Sender : Data Published");
        });
    }
}
