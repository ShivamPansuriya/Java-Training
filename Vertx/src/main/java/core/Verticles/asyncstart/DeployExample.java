package core.Verticles.asyncstart;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;

public class DeployExample extends AbstractVerticle
{

    public static void main(String[] args)
    {
        Launcher.executeCommand("run", DeployExample.class.getName());
    }

    @Override
    public void start() throws Exception
    {

        System.out.println("Main verticle has started, let's deploy some others...");

        vertx.deployVerticle("Verticles.asyncstart.OtherVerticle", res -> {
            if(res.succeeded())
            {

                String deploymentID = res.result();

                System.out.println("Other verticle deployed ok, deploymentID = " + deploymentID);

                vertx.undeploy(deploymentID, res2 -> {
                    if(res2.succeeded())
                    {
                        System.out.println("Undeployed ok!");
                    }
                    else
                    {
                        res2.cause().printStackTrace();
                    }
                });
            }
            else
            {
                res.cause().printStackTrace();
            }
        });

    }
}