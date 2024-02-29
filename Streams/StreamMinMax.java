package Streams;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StreamMinMax
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
        Integer l2 =  l.stream().max((i1, i2)-> i1.compareTo(i2)).get();

        System.out.println(l2);

        Integer l3 =  l.stream().min((i1, i2)-> i1.compareTo(i2)).get();

        System.out.println(l3);


    }
}
