//package generics;

public class GenericDemo1
{
    public static void main(String[] args)
    {
        Object obj = new String("shivam");

        // String str = obj; // not allowed

        String str = (String) obj; // allowed

        Object[] arr = new Object[3];

        arr[0] = str;
        arr[1] = 22;
        arr[2] = 3.46f;

        for(int i=0; i<arr.length; i++)
        {
            System.out.println(arr[i]+"\t==>\t"+arr[i].getClass());
        }
    }

}