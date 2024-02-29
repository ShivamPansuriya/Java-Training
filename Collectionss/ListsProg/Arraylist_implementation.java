package Collectionss.ListsProg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Arraylist_implementation
{
    public static void main(String[] args)
    {
        List<Integer> arrayList =  new ArrayList<>();

        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);


        arrayList.set(2,10);// replace element at given index
//
        //after creating iterator you cannot create element
//        Iterator<Integer> it = arrayList.iterator();
//        while(it.hasNext()){
//            System.out.println(it.next());
//        }


        System.out.println(arrayList);

        var arrayList2 = new ArrayList<>(arrayList);

//        System.out.println(arrayList2);

        arrayList2.add(5);

        System.out.println(arrayList2);

        var arrayList3 = new ArrayList<>();

        arrayList3.add(5);
        arrayList3.add(6);
        arrayList3.add(7);
        arrayList3.addAll(arrayList);

        arrayList3.remove(Integer.valueOf(5));// remove integer 5 from array

        arrayList3.remove(5); // remove element from index 5

        System.out.println(arrayList3);


        //arr.sublist(arr1) create shallow copy so change in one array will be reflect in another array

        //converting arraylist to array
        Integer[] arr = arrayList.toArray(new Integer[0]);

        arrayList.sort((i1,i2)-> i2.compareTo(i1));
        System.out.println(arrayList);
    }


}
