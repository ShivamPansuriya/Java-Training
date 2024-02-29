package Generics;
// public class GenericMethod
// {
//     public <E > void show(E... list)
//     {
//         for(E x: list)
//         {
//             System.out.println(x);
//         }
//     }
//     public static void main(String[] args)
//     {
//         GenericMethod obj = new GenericMethod();
//         obj.show("hello","world");
//         obj.show(1,2,3,4,5);
//     }
// }

class MyArray<T>
{

}
public class GenericMethod
{
    public void show(MyArray<? extends Number> obj)
    {

    }
    public static void main(String[] args)
    {
        MyArray<String> obj1 = new MyArray();

        MyArray<Integer> obj2 = new MyArray();

    }
}