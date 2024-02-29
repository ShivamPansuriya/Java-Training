package DSA;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Nodee
{
    Nodee left, right;

    int data;

    public Nodee(int data)
    {
        this.data = data;
    }
}

public class BinaryTree
{
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args)
    {
        Nodee root = createBinaryTree();


        System.out.println("Inorder Traversal: ");
        inOrder(root);
        System.out.println();

        System.out.println("Preorder Traversal: ");
        preOrder(root);
        System.out.println();

        System.out.println("Postorder Traversal: ");
        postOrder(root);

        System.out.println("Max Height: " + Height(root));
    }

    static Nodee createBinaryTree()
    {
        Nodee root = null;
        System.out.print("Enter data: ");
        int data = sc.nextInt();

        if(data == -1)
            return null;

        root = new Nodee(data);


        System.out.print("Enter value for left of " + data + " ");
        root.left = createBinaryTree();
        System.out.print("Enter value for right of " + data + " ");
        root.right = createBinaryTree();

        return root;
    }

    static void inOrder(Nodee root)
    {
        if(root == null)
            return;

        inOrder(root.left); // left
        System.out.print(root.data + " "); // root
        inOrder(root.right); // right
    }

    static void preOrder(Nodee root)
    {
        if(root == null)
            return;

        System.out.print(root.data + " "); // root
        preOrder(root.left); // left
        preOrder(root.right); // right
    }

    static void postOrder(Nodee root)
    {
        if(root == null)
            return;

        postOrder(root.left); // left
        postOrder(root.right); // right
        System.out.print(root.data + " "); // root
    }

    static int Height(Nodee root)
    {
        if(root == null)
        {
            return 0;
        }
        return (Math.max(Height(root.left), Height(root.right)) + 1);
    }

    static int SizeOfBinaryTree(Nodee root)
    {
        if(root == null)
            return 0;

        return SizeOfBinaryTree(root.left) + SizeOfBinaryTree(root.right) + 1;
    }

    static int maxvalue(Nodee root)
    {
        if(root == null)
            return Integer.MIN_VALUE;

        return Math.max(root.data, Math.max(maxvalue(root.left), maxvalue(root.right)));
    }

    static void LevelOrderTraverse(Nodee root)
    {
        Queue<Nodee> q = new LinkedList<>();
        q.add(root);

        while(!q.isEmpty())
        {
            Nodee node = q.poll();

            if(node.left != null)
                q.add(node.left);

            if(node.right != null)
                q.add(node.right);

            System.out.println(node.data + " ");
        }
    }

    static void PrintLeftUntil(Nodee root, ArrayList list, int level)
    {
        if(root == null)
            return;
        if(list.get(level) == null)
        {
            list.add(root);
        }

        PrintLeftUntil(root.left, list, level + 1);
        PrintLeftUntil(root.right, list, level + 1);
    }

    static void PrintLeftView(Nodee root)
    {
        ArrayList<Nodee> list = new ArrayList<>();

        PrintLeftUntil(root, list, 0);

        for(var node : list)
        {
            System.out.print(node.data + " ");
        }
        System.out.println();
    }

    static void PrintRightUntil(Nodee root, ArrayList list, int level)
    {
        if(root == null)
            return;
        if(list.get(level) == null)
        {
            list.add(root);
        }

        PrintLeftUntil(root.right, list, level + 1);
        PrintLeftUntil(root.left, list, level + 1);
    }

    static void PrintRightView(Nodee root)
    {
        ArrayList<Nodee> list = new ArrayList<>();

        PrintRightUntil(root, list, 0);

        for(var node : list)
        {
            System.out.print(node.data + " ");
        }
        System.out.println();
    }
}