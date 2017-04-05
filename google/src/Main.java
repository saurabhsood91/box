import java.util.ArrayList;

public class Main {

    public static int fib(int no,int memo[])
    {
        if(no <= 0)
            return 0;
        if(no == 1)
            return  1;
        if(memo[no] == 0)
            memo[no] = fib(no - 1, memo) + fib(no - 2,memo);
        return memo[no];
    }

    public static ArrayList<String> permute(String s)
    {
        if(s == null)
            return null;
        ArrayList<String> permuted = new ArrayList<>();
        if(s.length() == 1)
        {
            permuted.add(s);
            return permuted;
        }

        for(int i = 0; i < s.length(); i++)
        {
            StringBuilder replica = new StringBuilder(s);
            replica.deleteCharAt(i);
            ArrayList<String> recursedPermutes = permute(replica.toString());
            for(String a : recursedPermutes)
            {
                if(!permuted.contains(s.charAt(i)+a))
                permuted.add(s.charAt(i)+a);
            }
        }
        return  permuted;

    }

    public static int getWays(int n, int den[], int index, int memo[])
    {

        if(n == 0 )
            return 1;
        if(index > den.length -1)
            return 0;
        int ways = 0;
        if(memo[n] > 0)
            return memo[n];
        for(int i = 0 ; i*den[index] <= n ;i++)
        {
            ways += getWays(n-i*den[index], den, index+1, memo);
        }
        memo[n] = ways;
        return ways;
    }
    public static void getParen(ArrayList<String> list, int l, int r, String prefix)
    {
        if(r < l) return  ;
        if(r==l && l ==0)
        {
            list.add(prefix);
            return;
        }
        if(l>0)
            getParen(list, l-1,r, prefix + "(");
        if(r>0)
            getParen(list,l,r-1,prefix+")");

    }
    public static void main(String[] args) {
        System.out.println("Hello World!");
        int tofind = 6;
        int memo[] = new int[tofind+1];

        System.out.println(fib(tofind,memo));

        String topermute = "tinn";
        ArrayList<String> permutes = permute(topermute);
        for(String s : permutes)
        {
            System.out.println(s);
        }

        int den[] = {25,10,5,1};
        int n = 5;
        for(n = 1; n <15 ;n++)
        {
            int nq[] = new int[n+1];
            nq[0] = 1;
            int ways = getWays(n, den, 0, nq);
            System.out.println(" n " + n + " ways = " + ways);
        }

        //generating all pairs of parentheses
        n = 3;
        ArrayList<String> l = new ArrayList<>();
        getParen(l,n,n,"");
        for (String s :
             l) {
            System.out.println(s);
        }
    }
}
