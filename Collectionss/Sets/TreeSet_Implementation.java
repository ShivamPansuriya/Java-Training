package Collectionss.Sets;

import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class TreeSet_Implementation
{
    public static void main(String[] args)
    {
        NavigableSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(3);
        treeSet.add(87);
        treeSet.add(45);
        treeSet.add(2);
        treeSet.add(878);
        treeSet.remove(4);

        System.out.println(treeSet);

        System.out.println(treeSet.higher(45)); // return >45;
        System.out.println(treeSet.lower(45)); // return <45;
        System.out.println(treeSet.ceiling(45)); // return >=45;
        System.out.println(treeSet.floor(45)); // return <=45;

    }
}
