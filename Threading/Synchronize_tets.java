package Threading;

class sequence{
    int value =0;
    public void th1() throws Exception{

        while(true){
            synchronized(this)
            {
                if(value==0)System.out.println(Thread.currentThread().getName() + " : " + value++);
                wait();//
                    System.out.println(Thread.currentThread().getName() + " : " + value++);
                notify();
//                Thread.sleep(1000);
            }
        }
    }

    public void th2() throws Exception{
        int val =0;
        while(true){
            synchronized(this)
            {

                System.out.println(Thread.currentThread().getName() + " : " + val++);
                if(val%2 ==0)
                {
                    notify();
                    wait(); // /
                }


//                Thread.sleep(1000);
            }
        }
    }
}
public class Synchronize_tets
{
    public static void main(String[] args) throws Exception
    {
        sequence sq = new sequence();

        Thread th = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    sq.th1();
                }
                catch(Exception e){

                }

            }
        });

        Thread th3 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    sq.th2();
                }
                catch(Exception e){

                }

            }
        });

        th.start();
        th3.start();

        th.join();
        th3.join();
    }
}
