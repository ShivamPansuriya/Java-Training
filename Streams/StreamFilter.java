package Streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamFilter
{
    public static void main(String[] args)
    {
        ArrayList<Integer> l = new ArrayList<>();
        l.add(2);
        l.add(5);
        l.add(34);
        l.add(22);
        l.add(20);

        System.out.println(l);

        //filter will not reurn any type.
        List<Integer> l2 =  l.stream().filter(i->i%2==0).collect(Collectors.toList());

        System.out.println(l2);

        long evenNumberCount = l.stream().filter(i->i%2==0).count();

        System.out.println(evenNumberCount);
    }
}
