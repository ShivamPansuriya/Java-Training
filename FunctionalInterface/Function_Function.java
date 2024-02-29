package FunctionalInterface;

import java.beans.PersistenceDelegate;
import java.util.function.*;

public class Function_Function
{
    public static void main(String[] args)
    {

        Function<Integer,String> function = a -> a*10 + ":This is new Number";

        System.out.println(function.apply(2));
    }
}
