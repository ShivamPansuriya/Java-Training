package DSA;

import java.util.Collections;

public class BubbleShort
{
    static void bubblesort(int[] arr)
    {
        int n = arr.length;
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n-i-1; j++)
            {
                if(arr[j]>arr[j+1])
                {
                   int temp = arr[j];
                   arr[j] = arr[j+1];
                   arr[j+1] = temp;
                }
            }
        }

    }

    public static void main(String[] args)
    {
        int[] A = {1,2,5,7,8,4,32};
        BubbleShort.bubblesort(A);

        for(int i = 0; i < 7; i++)
        {
            System.out.println(A[i]);
        }
    }

}
