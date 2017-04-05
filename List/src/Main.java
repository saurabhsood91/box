import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;
class List{
    int data;
    List next = null;
    List(){data = Integer.MIN_VALUE;}
    List(int i){data = i;}

    void insert(int i)
    {
        if(this.data == Integer.MIN_VALUE)
        {
            data = i;
            return;
        }
        List temp = this;
        while(temp.next!=null)
        {
            temp = temp.next;
        }
        temp.next = new List(i);

    }
    void read()
    {
        List temp = this;
        while(temp!=null)
        {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();
    }
    List delete(int i)
    {
        List head = this;
        if(head.data == i)
            return head.next;
        else
        {
            List prev = this;
            while(prev.next != null)
            {
                if(prev.next.data == i)
                {
                    prev.next = prev.next.next;
                    return head;
                }
                prev = prev.next;
            }
        }
        return head;
    }
    List kToLast(int k)
    {
        List head = this;
        if(head == null)
            return null;
        List kL = head;
        while(kL!= null && k!= 0)
        {
            kL = kL.next;
            k--;
        }
        if(k!=0)
            return  null;
        while(kL!=null)
        {
            kL = kL.next;
            head = head.next;
        }
        return  head;
    }

    /*void reverse(List head, List prev)
    {
        if(next == null)
        {
            head = this;
            head.next = prev;
            return ;//head;
        }
        else
             next.reverse(head,this);
    }*/
    static List reverse(List node) //null , l
    {
        if(node==null || node.next == null)
            return  node;
        List remaining = reverse(node.next);
        node.next.next = node;
        node.next = null;
        return  remaining;

    }
    static List cloneAndReverse(List node)
    {
        if(node.next == null)
            return node;
        List head = null;

        while(node!=null)
        {
            List n = new List(node.data);
            n.next = head;
            head = n;
            node = node.next;
        }
        return head;

    }
    boolean Palin()
    {
        System.out.println("previous = ");
        this.read();
        List l = List.cloneAndReverse(this);
        System.out.println("next = ");
        l.read();
        List temp = this;
        while(l!=null && temp!=null)
        {
            if(l.data != temp.data)
                return  false;
            l = l.next;
            temp = temp.next;
        }
        return  l==null && temp==null;
    }

}

public class Main {

    public static  void sort(Stack a)
    {
        Stack b = new Stack();
        if(a==null)
            return;
        int temp = (int)a.pop();
        b.push(temp);
        while(!a.isEmpty())
        {
            temp = (int)a.pop();
            while(!b.empty() && temp<(int)b.peek())
            {
                a.push(b.pop());
            }
            b.push(temp);
        }
        while(!b.isEmpty())
        {
            a.push(b.pop());
        }
    }
    public  static void read(Stack a)
    {
        Stack b = new Stack();
        while(!a.isEmpty())
        {
            System.out.print((int)a.peek());
            b.push(a.pop());
        }
        while(!b.empty())
            a.push(b.pop());
    }
    public static void main(String[] args) {
        int m[] = {1,2,3,4,4,3};
        List l = new List();
        for(int i:m)
        {
            l.insert(i);
        }
        l.read();
        //l = l.delete(5);
        l.read();
        System.out.println(l.kToLast(5).data);
        //List ll = new List();
        l = List.reverse(l);
        l.read();
        System.out.println(l.Palin());

        int m2[] = {5,1,7,6,2,3,4};
        Stack s = new Stack();
        LinkedList ll = new LinkedList();
        Queue<Integer> l2 = new LinkedList<Integer>();

        for (int i:
             m2) {
            s.push(i);
            ll.add(i);
            l2.offer(i);
        }
       // while(!s.empty() && !ll.isEmpty() && !l2.isEmpty())
        {

          //  System.out.println("reading .. " +s.pop() + " " + ll.removeFirst() + " " +l2.poll());
        }
        System.out.println("before");
        read(s);
        sort(s);
        System.out.println("after ");
        read(s);
    }
}
