package Collectionss.Queue_Stack;

import java.util.PriorityQueue;

class StudentMark implements Comparable<StudentMark>
{
    private int mathMark;

    private int phyMark;

    StudentMark(int math, int phy)
    {
        this.mathMark = math;
        this.phyMark = phy;
    }

    public int getMathMark()
    {
        return mathMark;
    }

    public int getPhyMark()
    {
        return phyMark;
    }

    @Override
    public String toString()
    {
        return "Maths marks: "+ mathMark + " Phy Marks: "+ phyMark ;
    }

    @Override
    public int compareTo(StudentMark studentMark)
    {
        return this.mathMark - studentMark.mathMark;
    }
}

public class PriorityQueue_Implement
{
    public static void main(String[] args)
    {
        //        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        //        priorityQueue.offer(1);
        //        priorityQueue.offer(10);
        //        priorityQueue.offer(3);
        //        priorityQueue.offer(20);
        //
        //        System.out.println(priorityQueue);
        //
        //
        //        while(!priorityQueue.isEmpty())
        //            System.out.print(priorityQueue.poll()+" ");

        PriorityQueue<StudentMark> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(new StudentMark(50, 77));
        priorityQueue.offer(new StudentMark(60, 78));
        priorityQueue.offer(new StudentMark(30, 57));
        priorityQueue.offer(new StudentMark(50, 97));
        priorityQueue.offer(new StudentMark(20, 37));
        priorityQueue.offer(new StudentMark(90, 87));

        System.out.println(priorityQueue);
        while(!priorityQueue.isEmpty())
        {
            System.out.println(priorityQueue.poll());
        }
    }
}
