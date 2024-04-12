package org.vertx.core.json;

import io.vertx.core.Vertx;

public class Main
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        Vertx vertx1 = Vertx.vertx();

        vertx.deployVerticle(new JsonObjectSender());

        vertx1.deployVerticle(new JsonObjectReciever());
    }
}
