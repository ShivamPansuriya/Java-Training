package Threading;

public class Thread_getSet_name
{
    public static void main(String[] args)
    {
        System.out.println(Thread.currentThread().getName());
        Thread th = new Thread();
        System.out.println(th.getName());

        //set name
        Thread.currentThread().setName("shivam");
        th.setName("Raj");

        //get name
        System.out.println(Thread.currentThread().getName());
        System.out.println(th.getName());

    }
}
