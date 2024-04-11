package Verticles.deploy;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Launcher;
import io.vertx.core.ThreadingModel;
import io.vertx.core.json.JsonObject;


public class DeployExample extends AbstractVerticle {

    public static void main(String[] args) {
        Launcher.executeCommand("run", DeployExample.class.getName());
    }

    @Override
    public void start() throws Exception {

        System.out.println("Main verticle has started, let's deploy some others...");


        vertx.deployVerticle("Verticles.deploy.OtherVerticle");


        vertx.deployVerticle("Verticles.deploy.OtherVerticle", res -> {
            if (res.succeeded()) {

                String deploymentID = res.result();

                System.out.println("Other verticle deployed ok, deploymentID = " + deploymentID);


                vertx.undeploy(deploymentID, res2 -> {
                    if (res2.succeeded()) {
                        System.out.println("Undeployed ok!");
                    } else {
                        res2.cause().printStackTrace();
                    }
                });

            } else {
                res.cause().printStackTrace();
            }
        });

        // Deploy with config
        JsonObject config = new JsonObject().put("foo", "bar");
        vertx.deployVerticle("Verticles.deploy.OtherVerticle", new DeploymentOptions().setConfig(config));

        // Deploy 10 instances
        vertx.deployVerticle("Verticles.deploy.OtherVerticle", new DeploymentOptions().setInstances(10));

        // Deploy it as a worker verticle
        vertx.deployVerticle("Verticles.deploy.OtherVerticle", new DeploymentOptions().setThreadingModel(ThreadingModel.WORKER));


    }
}
