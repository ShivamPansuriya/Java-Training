package Collectionss.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMap_Implementation
{
    public static void main(String[] args)
    {
        Map<String, Integer> hashMap = new HashMap<>();

        hashMap.put("Shivam", 1);
        hashMap.put("Yash", 2);
        hashMap.put("Raj", 3);
        hashMap.put("Dhyani", 4);
        hashMap.put("Vaibhav", 5);

        System.out.println(hashMap.get("Shivam"));
        System.out.println(hashMap.containsKey("Shivam"));
        System.out.println(hashMap.containsValue(4));

        for(var mapEntry : hashMap.entrySet())
        {
            System.out.println(mapEntry.getKey()+" , "+ mapEntry.getValue());
        }

        for(var key: hashMap.keySet()){
            System.out.println(key+" , "+ hashMap.get(key));
        }

        Map<Integer, List<Integer>> adj = new HashMap<>();
        //this will check if 1 is present in key or not
        //if not present than it will run the argument function i.e.create arraylist and then it will get that value.
        adj.computeIfAbsent(1, f-> new ArrayList<>()).add(2);


    }
}
