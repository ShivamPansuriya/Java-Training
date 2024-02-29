package Streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamMap
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

        //Map will  return object type and is generally use to update valur for given object
        List<Integer> l2 =  l.stream().map(i->(i%2==0)?i+2:i+1).collect(Collectors.toList());

        System.out.println(l2);


    }
}
