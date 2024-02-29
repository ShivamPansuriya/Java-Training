package Enums;

public class Enum_example2
{
    public static void main(String[] args)
    {
        for(var days : Daysenum.values())
        {
            System.out.println(days + " "+ days.fun);
        }
    }
}
