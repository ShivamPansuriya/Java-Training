package Futures;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;

public class FutureComposition
{
    public static void main(String[] args)
    {
        // Create a Vertx instance
        Vertx vertx = Vertx.vertx();

        // Get the file system instance
        FileSystem fs = vertx.fileSystem();

        // Compose multiple asynchronous operations using Future composition
        Future<Void> future = fs
                .createFile("./foo")
                .compose(v -> {
                    // When the file is created (fut1), execute this:
                    return fs.writeFile("./foo", Buffer.buffer("Hello, Vert.x!"));
                })
                .compose(v -> {
                    // When the file is written (fut2), execute this:
                    return fs.move("./foo", "./bar");
                });

        // Handle the final result
        future.onComplete(ar -> {
            if (ar.succeeded()) {
                System.out.println("All operations completed successfully!");
            } else {
                System.err.println("Error occurred: " + ar.cause().getMessage());
            }

            // Close the Vertx instance
            vertx.close();
        });
    }
}
