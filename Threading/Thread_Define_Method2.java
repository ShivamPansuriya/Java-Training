package Threading;

class MyThread2 implements Runnable{

    //    public MyThread(){
    //        for(int i=0; i<10;++i){
    //            System.out.println("Hello");
    //        }
    //    }
    public void run(){
        for(int i=0; i<10;++i){
            System.out.println("Child thread");
        }
    }
}
public class Thread_Define_Method2
{
    //Start of main thread
    public static void main(String[] args)
    {
        //Class initiation
        MyThread2 r = new MyThread2();

        // creating new thread which has run() method of MyThread2 also called as target runnable
        Thread th = new Thread(r);

        //Starting of thread
        th.start();

        // Now there will be two thread main+ child(th)
        for(int i=0; i<10;++i){
            System.out.println("Main thread");
        }
    }
}
