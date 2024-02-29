package Generics;
class shiv{
    public void add(){}
}
@SuppressWarnings("Unchecked")
public class GenericsBasic <T>
{

    T arr[] = (T[]) new Object[3];

    public static void main(String[] args)
    {
        GenericsBasic<String> gb = new GenericsBasic<>();

        gb.arr[0] = "Hi";

        gb.arr[1] = "2";

        gb.arr[2] = "1";

        for(int i=0; i<3; ++i)
        {
            System.out.println(gb.arr[i]);
        }
    }
}
