import java.util.*;

class Tree{
    public  Tree left;
    public  Tree right;
    public int data;
    public Tree(){};
    public Tree(int i){data = i;}
    public static Tree insertSortedArray(int arr[],int start, int end, Tree root)
    {
        if( end < start )
            return null;
        int mid = start + (end - start)/2;
        root = new Tree(arr[mid]);
        System.out.print("inserted "+ root.data+ " ");
        //left = new Tree();
        root.left = Tree.insertSortedArray(arr, start,mid-1,root.left);
        //right = new Tree();
        root.right = Tree.insertSortedArray(arr,mid+1,end, root.right);
        return root;
    }
    public void inOrder()
    {
        if(this!=null)
        {
            if(left!=null)
            left.inOrder();
            System.out.print(data + " ");
            if(right!=null)
            right.inOrder();
        }
    }
    public void insert(int i)
    {
        if(data >= i)
        {
            if(left == null)
                left = new Tree(i);
            else
                left.insert(i);
        }
        else
        {
            if(right == null)
                right = new Tree(i);
            else
                right.insert(i);
        }
    }
    static int height(Tree root)
    {
        if(root == null)
            return -1;
        else
        {
            return Math.max(height(root.left), height(root.right))+1;
        }
    }
    int getHeight()
    {
        int l = 0, r = 0;
        if(left != null)
            l = 1 + left.getHeight();
        if(right!= null)
            r = 1 + right.getHeight();
        return l>r ? l : r;
    }
    int getMaxHeight()
    {
        int l = 0, r = 0;
        if(left != null)
            l = 1 + left.getHeight();
        if(right!= null)
            r = 1 + right.getHeight();
        return l>r ? l : r;
    }
    int getMinHeight()
    {
        int l = 0, r = 0;
        if(left != null)
            l = 1 + left.getHeight();
        if(right!= null)
            r = 1 + right.getHeight();
        return l<r ? l : r;
    }
    boolean balance()
    {
        if(getMaxHeight() - getMinHeight() > 1)
            return false;
        boolean l = true,  r = true;
        if(left!=null)
            l = left.balance();
        if(right!=null)
            r = right.balance();
        return l&&r;
    }
    static Tree LCA (Tree root, int one, int two)
    {
        if(root == null)
            return null;
        if(root.data == one || root.data == two)
            return root;
        Tree x = LCA(root.left, one, two);
        Tree y = LCA(root.right, one, two);

        if(x!=null && y != null)
            return root;

        else return (x==null)? y: x;
    }
    static boolean sumPath(Tree root, int sum)
    {
        if(root == null)
            return false;
        if(sum == root.data && root.left == null && root.right == null)
            return true;
        return sumPath(root.left, sum - root.data) || sumPath(root.right, sum - root.data);
    }
}

class Node{
public String name;
public ArrayList<Node> adjacent = new ArrayList<>();
Node(String n)
{name= n;}
}
class Graph{
public ArrayList<Node> nodes = new ArrayList<>();
boolean isConnected(Node one, Node two)
{
    Node root = one ;// nodes.get(0);
    Queue<Node> q = new LinkedList<>();
    q.add(root);
    HashSet<Node> visited = new HashSet<>();
    visited.add(root);

    while(!q.isEmpty())
    {
        Node removed = q.remove();
        for (Node n :
             removed.adjacent) {
            if(!visited.contains(n))
            {
                visited.add(n);
                q.add(n);
                if(n == two)
                    return true;
            }
        }
    }

    return false;
}
}


public class Main {
    static void binTraverse(int m[],int start,int end) //0 , 6
    {
        if(end >= start) {
            int mid = (start + end) / 2; //3 ... // 1  .. // 0
            System.out.println(m[mid]); //4 ...//2 ...// 1
            binTraverse(m, start, mid - 1); // 0 , 2 ...//0 , 0 .. //0 , -1
            binTraverse(m, mid + 1, end); // 4, 6
        }
    }
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Node n1 = new Node( "tinu");
        Node n2 = new Node( "babloo");
        Node n3 = new Node( "yash");
        Node n4 = new Node( "parth");
        Node n5 = new Node( "ashu");

        n1.adjacent.add(n2);
        n1.adjacent.add(n3);
        n3.adjacent.add(n4);
        n4.adjacent.add(n3);

        Graph g = new Graph();
        g.nodes.addAll(Arrays.asList(n1,n2,n3,n4,n5));

        System.out.print(g.isConnected(n4,n3));

        int m[] = {1,2,3,4,5,6,7};
        Tree t = new Tree(0);
        t = t.insertSortedArray(m,0,m.length-1,t);
        //t.left = new Tree(2);
        //t.right = new Tree(3);
        t.inOrder();
        //binTraverse(m,0,m.length-1);

        int m2[] = {1,2,5};

        Tree tt = new Tree(4);
        for (int i:
             m2) {
            tt.insert(i);
        }

        System.out.println("\n height is = " + Tree.height(tt));

        System.out.println("\n height is = " + tt.getHeight());
        System.out.println("\n balance is = " + tt.balance());
        System.out.println("FCA is = " + Tree.LCA(t, 1,3 ).data);
        System.out.println("sumpath is = " + Tree.sumPath(t, 17 ));
    }
}
