import  java.util.*;
class Node
{
String data;
ArrayList<Node> adjacent = new ArrayList<>();
Node(String s)
{data = s;}
    void DFS(HashSet<Node> s)
    {
        System.out.println(data);

        if(!adjacent.isEmpty())
        {
            for (Node n:
                 adjacent) {
                if(!s.contains(n))
                {
                    s.add(n);
                    n.DFS(s);
                }
            }
        }
    }
}
class Graph
{
ArrayList<Node> nodes = new ArrayList<>();

}


public class Main {

    public static int ways(int coins[],int total, int index)
    {
        //if(total == 0)
          //  return  1;
        if(index >= coins.length-1)
            return 1;

        int amt = coins[index];
        int way = 0;
        for(int i  = 0 ; total - i*amt >=0;i++)
        {
            way = way + ways(coins,total - i*amt ,index+1);
        }
        return way;
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
        n4.adjacent.add(n2);


        Graph g = new Graph();
        g.nodes.addAll(Arrays.asList(n1,n2,n3,n4,n5));

        n1.DFS(new HashSet<>());

        int n[] = {25,10,5,1};
        int total = 15;
        System.out.println(ways(n,total,0));

    }
}
