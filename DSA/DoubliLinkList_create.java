package DSA;

class Node_Double
{
    int data;

    Node_Double next;

    Node_Double prev;

    Node_Double(int val)
    {
        this.data = val;

        this.next = null;

        this.prev = null;
    }

}

class DoubliLink
{
    Node_Double head;

    DoubliLink()
    {
        this.head = null;
    }

    public void insert(int val)
    {
        Node_Double newnode = new Node_Double(val);

        if(head == null)
        {
            head = newnode;
        }
        else
        {
            Node_Double current = head;

            while(current.next != null)
            {
                current = current.next;
            }

            current.next = newnode;

            newnode.prev = current;
        }
    }

    public void insertAt(int val, int counter)
    {
        Node_Double newnode = new Node_Double(val);

        if(head == null)
        {
            head = newnode;
        }
        else
        {
            Node_Double current = head;


            while(current.data != counter)
            {
                if(current == null)
                {
                    System.out.println("not possible");
                    return;
                }

                current = current.next;


            }
            Node_Double prev = current.prev;

            prev.next = newnode;

            newnode.next = current;

            newnode.prev = prev;

            current.prev = newnode;
        }
    }

    public void displayForward()
    {
        Node_Double current = head;

        while(current != null)
        {
            System.out.print(current.data + " ");

            current = current.next;
        }

        System.out.println();
    }
}


public class DoubliLinkList_create
{
    public static void main(String[] args)
    {
        DoubliLink dl = new DoubliLink();

        dl.insert(1);
        dl.insert(2);
        dl.insert(3);

        dl.insertAt(5,2);

        dl.displayForward();
    }
}
