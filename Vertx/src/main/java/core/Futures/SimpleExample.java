package core.Futures;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class SimpleExample extends AbstractVerticle
{
    public static void main(String[] args)
    {
        add(2,3).onComplete(res->
        {
            if(res.succeeded())
            {
                System.out.println(res.result());
            }
            else{
                System.out.println("fail");
            }
        });
    }

    static Future<Integer> add (int a, int b)
    {
        Promise<Integer> promise = Promise.promise();

        Vertx.vertx().setTimer(4000, id->
        {
            promise.complete(a+b);
        });
        return promise.future();
    }
}
