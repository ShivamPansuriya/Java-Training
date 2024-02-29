package FunctionalInterface;

import java.util.function.Predicate;

public class FunctionPredicate
{
    public static void main(String[] args)
    {
        Predicate<String> LenCheck = s -> s.length()>6;

        System.out.println(LenCheck.test("shivam123"));
    }
}
