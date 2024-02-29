package Lambda;

@FunctionalInterface
interface Mylambda
{
    public void print();
}

@FunctionalInterface
interface MyLambdaParameter
{
    public int add(int a);
}

public class Lambda_test
{
    public static void main(String[] args)
    {

        Mylambda obj = new Mylambda() {
            @Override
            public void print()
            {
                System.out.println("Hello!! this is inner class");
            }
        };

        Mylambda obj1 = ()->
        {
            System.out.println("Hello!! this is lambda function");
        };

        MyLambdaParameter obj2 = (a)-> {
            return a+1;
        };

        obj.print();

        obj1.print();


        System.out.println(obj2.add(5));
    }
}
