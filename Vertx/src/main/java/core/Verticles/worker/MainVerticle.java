package core.Verticles.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Launcher;
import io.vertx.core.ThreadingModel;

public class MainVerticle extends AbstractVerticle {

    public static void main(String[] args)
    {
        Launcher.executeCommand("run", MainVerticle.class.getName());
    }

    @Override
    public void start() throws Exception
    {
        System.out.println("[Main] Running in " + Thread.currentThread().getName());
        vertx
                .deployVerticle("Verticles.worker.WorkerVerticle",
                        new DeploymentOptions().setThreadingModel(ThreadingModel.WORKER));

        vertx.eventBus().request(
                "sample.data",
                "hello vert.x",
                r -> {
                    System.out.println("[Main] Receiving reply ' " + r.result().body()
                            + "' in " + Thread.currentThread().getName());
                }
        );
    }
}