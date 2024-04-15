package core.Verticles.asyncstart;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

/*
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class OtherVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception
    {

        System.out.println("In OtherVerticle.start (async)");

        vertx.setTimer(2000, tid ->
        {

            System.out.println("Startup tasks are now complete, OtherVerticle is now started!");

            startPromise.complete();
        });

    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {

        vertx.setTimer(2000, tid -> {

            System.out.println("Cleanup tasks are now complete, OtherVerticle is now stopped!");

            stopPromise.complete();

        });

    }
}