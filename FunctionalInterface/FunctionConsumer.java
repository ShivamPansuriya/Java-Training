package FunctionalInterface;

import java.beans.PersistenceDelegate;
import java.util.function.*;

class Person
{
    private int height;

    public void setHeight(int value)
    {
        this.height = value;
    }

    public int getHeight(){
        return this.height;
    }
}

public class FunctionConsumer
{
    public static void main(String[] args)
    {
        Person p = new Person();

        Consumer<Person> setperson = s -> s.setHeight(30);

        setperson.accept(p);
        System.out.println(p.getHeight());
    }
}
