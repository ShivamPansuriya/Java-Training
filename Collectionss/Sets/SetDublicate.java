package Collectionss.Sets;

import java.util.HashSet;

class Data{
    String name;

   Data(String name){
       this.name = name;
   }

    @Override
    public boolean equals(Object obj)
    {
        return false;
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}

public class SetDublicate
{
    public static void main(String[] args)
    {
        HashSet<Data> s = new HashSet();
        Data s1 = new Data("shivam");
        Data s2 = new Data("shivam");
        s.add(s1);
        s.add(s2);

        System.out.println(s);
    }
}
