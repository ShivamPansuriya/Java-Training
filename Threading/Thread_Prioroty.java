package Threading;

class mythread extends Thread
{
    public void run(){
        for(int i=0; i<10;++i){
            System.out.println("Child thread");
        }
    }
}

public class Thread_Prioroty
{
    public static void main(String[] args)
    {
        mythread th = new mythread();
        th.setPriority(5);
        th.start();

        for(int i=0; i<10;++i){
            System.out.println("main thread");
        }
    }
}
