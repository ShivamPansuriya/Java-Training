package Collectionss.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Nested_Map_Map
{
    public static void main(String[] args)
    {
        var map1 = new HashMap<>();
        var map2 = new HashMap<>();

        map2.put(1, 10);
        map2.put(2, 20);
        map2.put(3, 30);
        map2.put(4, 40);
        map2.put(5, 50);

        map1.put("map2", map2);
        map1.put("map3", map2);

        map1.entrySet().stream()
                .flatMap(entry -> ((HashMap<Integer, Integer>) entry.getValue()).entrySet().stream())
                .forEach(entry -> {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        });

        var filterMap = map1.entrySet()
                .stream()
                .filter(entry -> entry.getKey() == "map2")
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        System.out.println(filterMap);
    }
}

