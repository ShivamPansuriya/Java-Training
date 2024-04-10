package Verticles.Contex;

import Verticles.worker.ExecuteBlocking;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContexExample extends AbstractVerticle
{
    private static final Logger logger = LoggerFactory.getLogger(ContexExample.class);

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        Context ctx = vertx.getOrCreateContext();
        ctx.put("foo", "bar");
        ctx.exceptionHandler(t -> {
            if ("Tada".equals(t.getMessage())) {
                logger.info("Got a _Tada_ exception");
            } else {
                logger.error("Woops", t);
            }
        });

        ctx.runOnContext(v -> {
            throw new RuntimeException("Tada");
        });
        ctx.runOnContext(v -> {
            logger.info("foo = {}", (String) ctx.get("foo"));
        });
    }
}
