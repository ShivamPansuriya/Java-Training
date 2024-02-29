package Collectionss.ListsProg;

import java.util.Collections;
import java.util.LinkedList;

public class LinkList_Implementation
{
    public static void main(String[] args)
    {
        var linkList = new LinkedList<Integer>();

        linkList.add(1);
        linkList.add(2);
        linkList.add(3);

        // ways to print in LinkList
        //method-1
        System.out.println(linkList);

        //method-2
        for(var val: linkList)
            System.out.println(val);

        //method-3
        var iterator = linkList.listIterator();
        System.out.println(iterator.next());// it will print element and then go to next pointer
        System.out.println(iterator.next());// it will print element and then go to next pointer
        System.out.println(iterator.previous());// it will go to next pointer and then it will print that is the reason why we are getting 2 as output

        linkList.set(1,10);
        System.out.println(linkList);

        linkList.sort((i1,i2)-> i2.compareTo(i1));
        System.out.println(linkList);


    }
}
