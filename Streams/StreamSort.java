package Streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamSort
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

        //Sort the array in default manner
        List<Integer> l2 =  l.stream().sorted().collect(Collectors.toList());

        System.out.println(l2);

        //Sort array using comparator arguments
        List<Integer> l3 =  l.stream().sorted((i1,i2)->i2.compareTo(i1)).collect(Collectors.toList());

        System.out.println(l3);
    }
}
