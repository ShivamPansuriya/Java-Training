package Generics;
class Data<T>
{
    private T obj;

    public T getData()
    {

        return obj;
    }

    public void setData(T v)
    {

        this.obj = v;
    }
}

public class GenericDemo3
{
    public static void main(String[] args)
    {
        Data<Integer> d = new Data<>();

        d.setData(10);

        System.out.println(d.getData());
    }
}
