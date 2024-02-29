package Threading;

class threadone extends Thread
{
    public void run(){
        for(int i=0; i<10;++i){
            System.out.println(Thread.currentThread().getName()+" " + i);
            Thread.yield();
        }
    }
}

public class Thread_Yeild
{
    public static void main(String[] args)
    {
        threadone th = new threadone();
        th.setPriority(3);
        th.start();

        for(int i=0; i<10;++i){
            System.out.println(Thread.currentThread().getName()+" " + i);
        }
    }
}
