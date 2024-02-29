package Collectionss.Queue_Stack;

import java.util.LinkedList;
import java.util.Queue;

public class Queue_Implementation
{
    public static void main(String[] args)
    {
        //Always remember to implement queue use linklist
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(1);
        queue.offer(2);


        // to see first element use peek() if queue is empty it will return null not an error
        System.out.println(queue.peek());

        // to remove element use poll(), if queue is empty it will return null not an error
        System.out.println(queue.poll());

        System.out.println(queue.peek());

        System.out.println(queue.isEmpty());
    }
}
