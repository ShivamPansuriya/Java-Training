package Records;

public class Record_example1
{
    public static void main(String[] args)
    {
        person_record pr = new person_record("shivam",19,"be always cool!!");

        System.out.println(pr.name());
        System.out.println(pr.age());
        System.out.println(pr.quote());
        System.out.println(pr.hashCode());
        System.out.println(pr);
        System.out.println(pr.ToUpper());

    }
}
