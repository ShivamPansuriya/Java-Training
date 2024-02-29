package DSA;

class BinarySearchTree
{

    // Function to create a new BST node
    static node newNode(int item)
    {
        node temp = new node();
        temp.key = item;
        temp.left = temp.right = null;
        return temp;
    }

    static node insert(node node, int key)
    {
        if(node == null)
            return newNode(key);

        if(key < node.key)
        {
            node.left = insert(node.left, key);
        }
        else if(key > node.key)
        {
            node.right = insert(node.right, key);
        }

        return node;
    }

    static void inorder(node root)
    {
        if(root != null)
        {
            inorder(root.left);
            System.out.print(" " + root.key);
            inorder(root.right);
        }
    }

    // Driver Code
    public static void main(String[] args)
    {

        node root = null;

        root = insert(root, 50);

        insert(root, 30);

        insert(root, 20);

        insert(root, 40);

        insert(root, 70);

        insert(root, 60);

        insert(root, 80);

        inorder(root);
    }

    static class node
    {
        int key;

        node left, right;
    }
}
