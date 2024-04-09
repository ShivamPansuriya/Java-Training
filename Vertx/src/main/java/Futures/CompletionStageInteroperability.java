package Futures;

import io.vertx.core.Vertx;
import io.vertx.core.Future;
import io.vertx.core.dns.DnsClient;

import java.util.Objects;
import java.util.concurrent.CompletionStage;

public class CompletionStageInteroperability {
    public static void main(String[] args) {
        // Create a Vertx instance
        Vertx vertx = Vertx.vertx();

        // Create a DNS client
        DnsClient dnsClient = vertx.createDnsClient();

        thenApply(vertx);

        // Perform a DNS lookup and convert the Future to a CompletionStage
        Future<String> future = dnsClient.lookup("vertx.io");
        future.toCompletionStage().whenComplete((ip, err) -> {
            if (err != null) {
                System.err.println("Could not resolve vertx.io");
                err.printStackTrace();
            } else {
                System.out.println("vertx.io => " + ip);
            }

            // Close the Vertx instance
            vertx.close();
        });
    }

    static void thenApply(Vertx vertx)
    {
        // Assuming you have a Future<String> representing the result of an asynchronous operation
        Future<String> future = vertx.createDnsClient().lookup("vertx.io");

        // Convert the Future to a CompletionStage
        CompletionStage<String> completionStage = future.toCompletionStage();

        // Use thenApply() to transform the result
        CompletionStage<Integer> lengthStage = completionStage.thenApply(ip -> ip.length());

        // Handle the transformed result
        lengthStage.whenComplete((length, err) -> {
            if (err != null) {
                System.err.println("Error: " + err.getMessage());
            } else {
                System.out.println("The resolved IP address has " + length + " characters.");
            }
        });
    }


}
