package Threading;

class Display
{
    public synchronized void print(String s)
    {
        for(int i = 0; i < 10; ++i)
        {
            System.out.print("Hello");
            try
            {
                Thread.sleep(2000);
            }
            catch(Exception e){

            }
            System.out.println(s);
        }
    }
    public void print2(String s)
    {
        for(int i = 0; i < 10; ++i)
        {
            System.out.print("Hello");
            try
            {
                Thread.sleep(2000);
            }
            catch(Exception e){

            }
            System.out.println(s);
        }
    }
}

class Mythread extends Thread
{
    Display d;

    String name;

    Mythread(Display d, String name)
    {
        this.d = d;
        this.name = name;
    }

    public void run()
    {
        d.print(name);
    }
}

class Mythread2 extends Thread
{
    Display d;

    String name;

    Mythread2(Display d, String name)
    {
        this.d = d;
        this.name = name;
    }

    public void run()
    {
        d.print2(name);
    }

}

public class Synchrenize_class
{
    public static void main(String[] args)
    {
        Display d = new Display();
        Mythread th = new Mythread(d,"shivam");
        Mythread2 th2 = new Mythread2(d,"yash");
        th.start();
        th2.start();

    }
}
