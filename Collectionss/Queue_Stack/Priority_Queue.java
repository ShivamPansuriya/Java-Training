package Collectionss.Queue_Stack;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.PriorityBlockingQueue;

public class Priority_Queue
{
    public static void main(String[] args)
    {
        PriorityQueue pq = new PriorityQueue(2);
        PriorityBlockingQueue pbq = new PriorityBlockingQueue(2);

        pq.add(1);
        pq.add(3);
        pq.add(4);

        System.out.println(pq);



    }
}
