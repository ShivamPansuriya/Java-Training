package Collectionss.Sets;

import java.util.HashSet;
import java.util.Set;

public class HashSet_Implementation
{
    public static void main(String[] args)
    {
        Set<Integer> hashSet = new HashSet<>();
        hashSet.add(30);
        hashSet.add(40);
        hashSet.add(10);
        hashSet.add(20);

        System.out.println(hashSet);


        Set<Integer> hashSet2 = new HashSet<>();
        hashSet2.add(20);
        hashSet2.add(60);
        hashSet2.add(80);
        hashSet2.add(10);

        System.out.println(hashSet2);

//        hashSet2.retainAll(hashSet); // hashSet2 will only contain elements that are common in hashSet and hashSet2
//        System.out.println(hashSet2);

//        hashSet2.removeAll(hashSet); // will remove the element that are common in hashset and hashset2
//        System.out.println(hashSet2);

          hashSet2.addAll(hashSet);
        System.out.println(hashSet2); // will give union of hashSet and hashSet2
    }
}
