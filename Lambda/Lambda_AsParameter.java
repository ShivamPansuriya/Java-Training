package Lambda;

@FunctionalInterface
interface Lambda{
    public void print(String s);
}
public class Lambda_AsParameter
{
    Lambda_AsParameter(String s)
    {
        StringBuffer str = new StringBuffer(s);

        str.reverse();

        System.out.println(str);
    }

    public static void main(String[] args)
    {
//         Lambda lb = Lambda_AsParameter::new;   //for constructor

        Lambda lb = System.out::print;

         lb.print("shivam");
    }
}
