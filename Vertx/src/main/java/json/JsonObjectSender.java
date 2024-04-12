package org.vertx.core.json;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
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

            vertx.eventBus().publish("json", object);
            System.out.println("Sender : Data Published");
        });
    }
}
