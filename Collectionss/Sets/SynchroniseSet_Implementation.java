package Collectionss.Sets;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SynchroniseSet_Implementation
{
    public static void main(String[] args)
    {
        Set<Integer> set = new HashSet<>();
        var synset = Collections.synchronizedSet(set);
        synset.add(1);
        synset.add(2);

        for(int i = 0; i < 1000; i++)
        {
            set.add(i);
        }
        Thread th = new Thread(()->{
            for(var val:set){
                System.out.println(val+ Thread.currentThread().getName());
            }
        });
        th.start();
        for(var val:set){
            System.out.println(val+ Thread.currentThread().getName());
        }
    }
}
