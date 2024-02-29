package Collectionss.Queue_Stack;

import java.util.Stack;

public class Stack_Impement
{
    public static void main(String[] args)
    {
        Stack<Integer> stack = new Stack<>();

        stack.push(1);
        stack.push(2);
        stack.push(2);


        System.out.println(stack);

//        while(!stack.isEmpty())
//            System.out.println(stack.pop());

        System.out.println(stack.contains(1)); // check if element is present or not

        stack.clear();// empty the stack
        System.out.println(stack);

    }
}
