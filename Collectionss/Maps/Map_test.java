package Collectionss.Maps;

import java.util.*;

public class Map_test
{
    public static void main(String[] args) throws InterruptedException
    {
        Map<Integer, String> hashMap = new HashMap<>();
        hashMap.put(1, "shivam");
        hashMap.put(2, "shivam");
        hashMap.put(3, "shivam");


//        hashMap.remove(2);

//        var it = hashMap.entrySet().iterator();
//        while(it.hasNext()){
//            var entry = it.next();
//            if(entry.getKey() == 2){
//                it.remove();   //base64
//            }
//        }
//        System.out.println(hashMap);

        var it = hashMap.keySet().iterator();
                while(it.hasNext()){
                    if(it.next() == 2){
                        it.remove();
                    }
                }
                System.out.println(hashMap);

    }
}
