package Collectionss.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Map_streamPractice
{
    public static void main(String[] args)
    {
        var map1 = new HashMap<>();

        var list = new ArrayList<>();
        for(int i = 1; i < 11; i++)
        {
            list.add(i);
        }

        for(int i = 1; i < 11; i++)
        {
            map1.put(i,list);
        }

        var result = map1.entrySet().stream().filter(e-> ((Integer)e.getKey()%2) ==0)
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> ((ArrayList<Integer>)entry.getValue()).stream()
                        .filter(val -> val%2 ==1)
                        .collect(Collectors.toList())
                ));

        System.out.println(result);
    }
}
