package Http;


import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class HttpServers
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        HttpServer server = vertx.createHttpServer();

        server.requestHandler(req->
        {
            req.response()
                    .end("<h1>Hello there</h1>");

            System.out.println(req.path() + "\n" + req.uri());
        });

        server.listen(5555, "localhost").onComplete(res -> {
            if(res.succeeded())
            {
                System.out.println("Server is now listening!");
            }
            else
            {
                System.out.println("Failed to bind!");
            }
        });
    }
}
