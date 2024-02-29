package DSA;


public class MergeSort
{
    static int[] A = {1,3,63,7,5,9,4,6,22};
    static void merge(int l, int r, int mid)
    {
        int lsz = mid - l + 1;
        int[] Larr = new int[lsz+1];

        int rsz = r-mid;
        int[] Rarr = new int[rsz+1];

        for(int i = 0; i < lsz; i++)
        {
            Larr[i] = A[i + l];
        }

        for(int i = mid; i < rsz; i++)
        {
            Rarr[i] = A[i + mid + 1];
        }

        Larr[lsz] = Rarr[rsz] = Integer.MAX_VALUE;
        int L_index = 0;
        int R_index = 0;
        for(int i = l; i <= r; i++)
        {
            if(Larr[i] <= Rarr[i])
            {
                A[i] = Larr[L_index++];
            }
            else
            {
                A[i] = Larr[R_index++];
            }
        }
    }

    static void mergesort(int l, int r)
    {
        if(l==r) return;

        int mid = (r-l)/2;
        mergesort(l,mid);
        mergesort(mid+1,r);

        merge(l,r,mid);
    }

    public static void main(String[] args)
    {

        int r = A.length;

        MergeSort.mergesort(0,r-1);
    }

}


//
//package DSA;
//
//// Java program for Merge Sort
//import java.io.*;
//
//class MergeSort {
//
//    // Merges two subarrays of arr[].
//    // First subarray is arr[l..m]
//    // Second subarray is arr[m+1..r]
//    void merge(int arr[], int l, int m, int r)
//    {
//        // Find sizes of two subarrays to be merged
//        int n1 = m - l + 1;
//        int n2 = r - m;
//
//        // Create temp arrays
//        int L[] = new int[n1];
//        int R[] = new int[n2];
//
//        // Copy data to temp arrays
//        for (int i = 0; i < n1; ++i)
//            L[i] = arr[l + i];
//        for (int j = 0; j < n2; ++j)
//            R[j] = arr[m + 1 + j];
//
//        // Merge the temp arrays
//
//        // Initial indices of first and second subarrays
//        int i = 0, j = 0;
//
//        // Initial index of merged subarray array
//        int k = l;
//        while (i < n1 && j < n2) {
//            if (L[i] <= R[j]) {
//                arr[k] = L[i];
//                i++;
//            }
//            else {
//                arr[k] = R[j];
//                j++;
//            }
//            k++;
//        }
//
//        // Copy remaining elements of L[] if any
//        while (i < n1) {
//            arr[k] = L[i];
//            i++;
//            k++;
//        }
//
//        // Copy remaining elements of R[] if any
//        while (j < n2) {
//            arr[k] = R[j];
//            j++;
//            k++;
//        }
//    }
//
//    // Main function that sorts arr[l..r] using
//    // merge()
//    void sort(int arr[], int l, int r)
//    {
//        if (l < r) {
//
//            // Find the middle point
//            int m = l + (r - l) / 2;
//
//            // Sort first and second halves
//            sort(arr, l, m);
//            sort(arr, m + 1, r);
//
//            // Merge the sorted halves
//            merge(arr, l, m, r);
//        }
//    }
//
//    // A utility function to print array of size n
//    static void printArray(int arr[])
//    {
//        int n = arr.length;
//        for (int i = 0; i < n; ++i)
//            System.out.print(arr[i] + " ");
//        System.out.println();
//    }
//
//    // Driver code
//    public static void main(String args[])
//    {
//        int arr[] = { 12, 11, 13, 5, 6, 7 };
//
//        System.out.println("Given array is");
//        printArray(arr);
//
//        MergeSort ob = new MergeSort();
//        ob.sort(arr, 0, arr.length - 1);
//
//        System.out.println("\nSorted array is");
//        printArray(arr);
//    }
//}
///* This code is contributed by Rajat Mishra */

