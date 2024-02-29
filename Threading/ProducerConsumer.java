package Threading;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

class Product
{
    int capicaty = 8;

    private LinkedList<Integer> l = new LinkedList<>();

    public void producer() throws InterruptedException
    {
        int value = 0;
        while(true)
        {
            synchronized(this)
            {
                while(l.size() == capicaty)
                {
                    wait();
                }

                System.out.println("Producer produce: " + value);
                l.add(value++);
                notify();

                TimeUnit.MILLISECONDS.sleep(100);
            }
        }
    }

    public synchronized void consumer() throws InterruptedException
    {
        while(true)

        {
            synchronized(this)
            {
                while(l.isEmpty())
                {
                    wait();
                }

                System.out.println("Consumer consume: " + l.removeFirst());

                notify();

                TimeUnit.SECONDS.sleep(1);
            }
        }
    }

}

public class ProducerConsumer
{
    public static void main(String[] args)
    {
        Product p = new Product();

        Thread th1 = new Thread(() -> {
            try
            {
                p.producer();
            } catch(InterruptedException e)
            {
                throw new RuntimeException(e);
            }

        });

        Thread th2 = new Thread(() -> {
            try
            {
                p.consumer();
            } catch(InterruptedException e)
            {
                throw new RuntimeException(e);
            }

        });

        th1.start();
        th2.start();
    }
}
