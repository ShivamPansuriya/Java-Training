package core.json;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class JsonObjectReciever extends AbstractVerticle
{
    @Override
    public void start()
    {
        vertx.setPeriodic(2000, handler ->
        {
            vertx.eventBus().consumer("core/json", objectMessage ->
            {

                JsonObject obj = (JsonObject) objectMessage.body();

                System.out.println("Object Recieved from Sender");

                Person person = obj.mapTo(Person.class);

                System.out.println(person.getName() + " " + person.getAge());
            });
        });
    }

    ;

}
