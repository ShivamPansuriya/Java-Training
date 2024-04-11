package Futures;

import io.vertx.core.Vertx;
import io.vertx.core.Future;
import io.vertx.core.dns.DnsClient;
import java.util.concurrent.CompletionStage;

public class CompletionStageInteroperability {
    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        DnsClient dnsClient = vertx.createDnsClient();

        thenApply(vertx);

        Future<String> future = dnsClient.lookup("vertx.io");
        future.toCompletionStage().whenComplete((ip, err) -> {
            if (err != null) {
                System.err.println("Could not resolve vertx.io");
                err.printStackTrace();
            } else {
                System.out.println("vertx.io => " + ip);
            }

            vertx.close();
        });
    }

    static void thenApply(Vertx vertx)
    {
        Future<String> future = vertx.createDnsClient().lookup("vertx.io");

        CompletionStage<String> completionStage = future.toCompletionStage();

        CompletionStage<Integer> lengthStage = completionStage.thenApply(ip -> ip.length());

        lengthStage.whenComplete((length, err) -> {
            if (err != null) {
                System.err.println("Error: " + err.getMessage());
            } else {
                System.out.println("The resolved IP address has " + length + " characters.");
            }
        });
    }


}
