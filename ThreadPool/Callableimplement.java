package ThreadPool;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Callableimplement
{
    public static int sum(int a, int b){
        System.out.println(a+b+ " : " +Thread.currentThread().getName());

        return a+b;
    }

    public static void main(String[] args)
    {

        var ex = Executors.newCachedThreadPool();

        Callable c = ()->Callableimplement.sum(2,3);

        var future = ex.submit(c);

        System.out.println(future);

        ex.shutdown();
    }
}
