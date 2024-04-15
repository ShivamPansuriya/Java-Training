package core.Verticles.deploy;

import io.vertx.core.AbstractVerticle;

public class OtherVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {

        System.out.println("In OtherVerticle.start");

        System.out.println("Config is " + config());
    }

    @Override
    public void stop() throws Exception {

        System.out.println("In OtherVerticle.stop");

    }
}
