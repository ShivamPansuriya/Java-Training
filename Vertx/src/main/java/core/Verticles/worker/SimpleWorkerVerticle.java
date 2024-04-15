package core.Verticles.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.ThreadingModel;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleWorkerVerticle extends AbstractVerticle
{

    private final Logger logger = LoggerFactory.getLogger(SimpleWorkerVerticle.class);

    @Override
    public void start()
    {
        vertx.setPeriodic(10_000, id -> {
            try
            {
                logger.info("Zzz...");

                Thread.sleep(8000);

                logger.info("Up!");

            }
            catch(InterruptedException e)
            {
                logger.error("Woops", e);
            }
        });
    }

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        DeploymentOptions opts = new DeploymentOptions()
                .setInstances(2)
                .setThreadingModel(ThreadingModel.WORKER);

        vertx.deployVerticle("chapter2.worker.WorkerVerticle", opts);
    }
}