package Generics;

public class GenericDemo2<T>
{
    T data[] = (T[]) new Object[3]; // generic array
    public static void main(String[] args)
    {
        GenericDemo2<String> gd = new GenericDemo2<>();

        gd.data[0] = "yash";
        gd.data[1] = "prajapati";
        // gd.data[2] = 10;

    }

}