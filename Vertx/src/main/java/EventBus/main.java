package EventBus;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class main
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle("EventBus.HeatSensor", new DeploymentOptions().setInstances(4));

        vertx.deployVerticle("EventBus.HttpServer");

        vertx.deployVerticle("EventBus.Listener");

        vertx.deployVerticle("EventBus.SensorData");
    }
}
