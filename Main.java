import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.*;

class parent
{
    int x = 10;

    public void print(int i)
    {
        System.out.println("parent: " + this.x);
    }

    public static void print2(int i)
    {
        System.out.println("parent: " + i);
    }
}

class child extends parent
{
    public static void print2(int i){
        System.out.println("this is second static in child");
    }

    public void print(int i)
    {
        System.out.println("child");
    }
}

//class child2 extends child{
//    public void print(){
//        System.out.println("displaying");
//    }
//}
class Yash{}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main
{
    //    String s = new String("hi");
    int x;

    public static void main(String[] args)
    {

        child c = new child();
        child.print2(2);
        String[] s = new String[-100];

        parent p = new parent();

//        Map<String, Integer> map = new HashMap<>();
//
//        // Increment the value associated with the key "shivam"
//        map.merge("shivam", 1, Integer::sum);
//        map.merge("shivam", 1, Integer::sum);
//
//        // Display the updated map
//        System.out.println("Updated map: " + map);
//        child c1 = new child();
//        c1.print(10);
//        System.out.println(c1.x);
//
//        Thread t = new Thread();
//        t.stop();
//        parent c2 = new child();
//        c2.print(10);
//        System.out.println(c2.x);
//
//        parent c3 = new parent();
//        c3.print(10);



        //        System.out.println(m.x);
        //        System.out.println(x);
    }


}