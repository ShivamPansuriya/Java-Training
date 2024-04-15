package core.Futures;

import io.vertx.core.Future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class FutrreFromCompleteStage
{
    public static void main(String[] args)
    {
        CompletionStage<String> completionStage = CompletableFuture.supplyAsync(() ->
        {
            return "Hello, Vert.x!";
        });

        // Convert the CompletionStage to a Future
        Future<String> future = Future.fromCompletionStage(completionStage);

        // Handle the Future
        future.onSuccess(result ->
        {
            System.out.println("Result: " + result);
        }).onFailure(err ->
        {
            System.err.println("Error: " + err.getMessage());
        });
    }
}