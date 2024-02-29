package Collectionss.Maps;

import java.util.ArrayList;
import java.util.HashMap;

public class Nested_Map
{
    public static void main(String[] args)
    {
        var nestedMap = new HashMap<>();
        var list1 = new ArrayList<>();
        var list2 = new ArrayList<>();

        list1.add(10);
        list1.add(20);
        list1.add(30);

        list2.add(40);
        list2.add(50);
        list2.add(60);

        nestedMap.put("shivam", list1);
        nestedMap.put("Yash", list2);

        nestedMap.forEach((k, v) -> {
            var l = (ArrayList<Integer>) v;
            if(k == "shivam")
            {
                l.add(90);
            }
        });

        System.out.println(nestedMap);
        //        for(var entry : nestedMap.entrySet())
        //        {
        //            System.out.println(entry.getKey() + " = " + entry.getValue());
        //
        //            List<Integer> l = (ArrayList<Integer>) entry.getValue();
        //
        //            l.add(90);
        //
        //            for(var arrayValue : l)
        //            {
        //                System.out.println(arrayValue);
        //            }
        //        }
    }
}
