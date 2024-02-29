package DSA;

import java.io.IOException;

class parent
{
    public void print()
    {
        System.out.println("parent block");
    }
}

class child extends parent
{

}

public class Task
{
    public static void main(String[] args)
    {
        child c = new child();

        try
        {

            System.out.println("hello");
        }catch(Exception e){

        }

    }
}
