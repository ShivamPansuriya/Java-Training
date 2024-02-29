package Collectionss.Maps;

import com.sun.jdi.IntegerValue;

import java.util.*;


public class HashMap_Vs_IdentityHashMap
{
    public static void main(String[] args)
    {
        IdentityHashMap hm = new IdentityHashMap();
        String i1 = new String("a");
        String i2 = new String("a");
        hm.put(i1,"shivam");
        hm.put(i2,"yash");

//        System.out.println(tp);
        System.out.println(hm);
    }
}
