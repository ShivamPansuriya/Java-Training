package DSA;

class Node{
    int data;
    Node next;

    Node(int value){
        this.data = value;
        this.next = null;
    }

    public void del(int val, Node head){
        Node prev = null;
        while(head.data != val){
            if(head.next == null){
                System.out.println("No such element");
                return;
            }
            prev = head;
            head = head.next;

        }
        prev.next = head.next.next;
    }

}
public class Linklist_create
{
    public static void main(String[] args)
    {
        Node head = new Node(0);
        Node prev = head;
        for(int i = 1; i < 10; i++)
        {
            Node n = new Node(i);
//            System.out.println("insert "+i);
            prev.next = n;
//            System.out.println(n.data);
            prev = prev.next;
//            System.out.println(prev.data);
        }

        head.del(5,head);

        prev = head;
        while(prev != null){
            System.out.println(prev.data);
            prev = prev.next;
        }
    }
}
