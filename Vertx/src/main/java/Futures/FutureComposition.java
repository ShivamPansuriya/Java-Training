package Futures;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;

public class FutureComposition
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        FileSystem fs = vertx.fileSystem();

        Future<Void> future = fs
                .createFile("./foo")
                .compose(v ->
                {
                    return fs.writeFile("./foo", Buffer.buffer("Hello, Vert.x!"));
                })
                .compose(v ->
                {
                    return fs.move("./foo", "./bar");
                });

        future.onComplete(ar ->
        {
            if (ar.succeeded()) {
                System.out.println("All operations completed successfully!");
            } else {
                System.err.println("Error occurred: " + ar.cause().getMessage());
            }

            vertx.close();
        });
    }
}
