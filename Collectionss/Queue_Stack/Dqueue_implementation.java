package Collectionss.Queue_Stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.concurrent.DelayQueue;
import java.util.stream.Collectors;

public class Dqueue_implementation
{
    public static void main(String[] args)
    {
        Deque<Integer> deque = new ArrayDeque<>();

        deque.offerFirst(1);
        deque.offerFirst(2);
        deque.offerFirst(3);

        deque.offerLast(4);
        deque.offerLast(5);
        deque.offerLast(6);
        
         System.out.println(deque);

        System.out.println(deque.peekFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.peekLast());
        System.out.println(deque.pollLast());


        // sorting in deque. it is possible using stream api
        deque.stream().sorted((i1,i2)-> i2.compareTo(i1))
                .forEach(System.out::print);

        System.out.println();
        System.out.println(deque);


    }
}
