package Threading;

class Display1
{
    public synchronized void print(String s)
    {
        for(int i = 0; i < 10; ++i)
            for(int j = 0; j < 10; ++j)
                for(int k = 0; k < 10; ++k)
                    System.out.println("Before Block:"+ Thread.currentThread().getName());
        synchronized(this)
        {
            for(int i = 0; i < 10; ++i)
            {
                System.out.print(Thread.currentThread().getName());
            }
        }
        System.out.println("After Block");
    }
}

class Mythread1 extends Thread
{
    Display1 d;

    String name;

    Mythread1(Display1 d, String name)
    {
        this.d = d;
        this.name = name;
    }

    public void run()
    {
        d.print(name);
    }

}

public class Synchronize_block
{
    public static void main(String[] args)
    {
        Display1 d = new Display1();
        Mythread1 th = new Mythread1(d,"shivam");
        Mythread1 th2 = new Mythread1(d,"yash");
        th.start();
        th2.start();

    }
}
