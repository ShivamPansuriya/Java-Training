package DSA;

public class QuickShot
{
    static void swap(int i, int j, int[] arr)
    {
        int temp = arr[i];

        arr[i] = arr[j];

        arr[j] = temp;
    }

    static int Partation(int l, int r, int[] arr)
    {
        int pivot = r - 1;

        int i_index = l;

        for(int i = l; i < r; i++)
        {
            if(arr[i] > pivot)
            {
                swap(i_index,i,arr);

                i_index++;
            }
        }
        swap(i_index,r,arr);

        return (i_index+1);
    }

    static void quicksort(int l, int h , int[] arr)
    {
        if(l<h) return;
        int pi =Partation(l,h,arr);

        quicksort(l, pi-1, arr);
        quicksort(pi+1, h, arr);
    }

    static void display(int[] arr)
    {
        for(var val: arr)
        {
            System.out.print(val+" ");
        }
        System.out.println();
    }

    public static void main(String[] args)
    {
        int[] A = {2,3,4,9,6,7,54,67,22,34,45};

        int len = A.length;

        quicksort(0,len,A);

        display(A);
    }


}
