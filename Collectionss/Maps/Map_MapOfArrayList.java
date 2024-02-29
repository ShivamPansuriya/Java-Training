package Collectionss.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Map_MapOfArrayList
{
    public static void main(String[] args)
    {
        var map1 = new HashMap<>();
        var map2 = new HashMap<>();

        var arrayList1 = new ArrayList<>();
        arrayList1.add(1);
        arrayList1.add(2);
        arrayList1.add(3);
        arrayList1.add(4);

        var arrayList2 = new ArrayList<>();
        arrayList2.add(1);
        arrayList2.add(2);
        arrayList2.add(3);
        arrayList2.add(4);

        map2.put("arrayList1",arrayList1);
        map2.put("arrayList2",arrayList2);

        map1.put("map2", map2);

        map1.values().forEach(System.out::println);
//        map1.entrySet().stream().
//                flatMap(entry->((HashMap<String,ArrayList<Integer>>)entry.getValue()).entrySet().stream())
//                .forEach((entry)-> {
//            System.out.println(entry.getKey() + entry.getValue());
//        });
    }
}
