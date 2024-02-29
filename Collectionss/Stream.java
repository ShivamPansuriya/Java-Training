package Collectionss;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class Stream
{
    public static void main(String[] args)
    {
        ArrayList<ArrayList<String>> ar = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        name.add("Yash");
        name.add("Shivam");
        name.add("Dhyani");
        name.add("Raj");
        name.add("Vaibhav");
        ar.add(new ArrayList<>(Arrays.asList("heloo","yash","ko","nahi","ata hai","list in list")));
        ArrayList<String> surname = new ArrayList<>();
        surname.add("Prajapati");
        surname.add("Pansuriya");
        surname.add("Patel");
        surname.add("Patel");
        surname.add("Shah");



        ar.add(name);
        ar.add(surname);

        var result = ar.stream()
                .flatMap(Collection::stream )
                .sorted((l1,l2) -> l2.compareTo(l1))
                .skip(2)
                .limit(5)
                .reduce((a,b)-> a+" "+b);
//                .forEach(System.out::println);

        System.out.println(result.get());
    }
}
