package ThreadPool;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

record Order(long orderId, String item, int qty)
{};

public class Main
{

    private static final Random random = new Random();

    public static void main(String[] args)
    {

        ShoeWarehouse warehouse = new ShoeWarehouse();

        ExecutorService produceres = Executors.newSingleThreadExecutor();
        produceres.execute(()->{
            for(int j = 0; j < 10; j++)
            {
                warehouse.receiveOrder(new Order(random.nextLong(1000000, 9999999), ShoeWarehouse.PRODUCT_LIST[random.nextInt(0, 5)], random.nextInt(1, 4)));
            }
        });

        ExecutorService consumees = Executors.newFixedThreadPool(2);
        for(int i = 0; i < 2; i++)
        {
            consumees.execute(() -> {
                for(int j = 0; j < 5; j++)
                {
                    Order item = warehouse.fulfillOrder();
                }
            });
        }
        consumees.shutdown();
        produceres.shutdown();
    }
}