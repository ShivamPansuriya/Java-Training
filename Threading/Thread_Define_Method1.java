package Threading;

class MyThread extends Thread{

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
public class Thread_Define_Method1
{
    //Start of main thread
    public static void main(String[] args)
    {
        //thread initiation
        MyThread th = new MyThread();
        //Starting of thread
        th.start();

        // Now there will be two thread main+ child(th)
        for(int i=0; i<10;++i){
            System.out.println("Main thread");
        }
    }
}
