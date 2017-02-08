import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by ivanov-av
 * 05.01.2017 11:09.
 */
public class FindOdd {
    public static int findIt(int[] A) {
        int odd = -1;
        SortedSet<Integer> set = new TreeSet<Integer>();
        for (int i = 0; i < A.length; i++) {
            int times = 0;
            if (!set.contains(A[i])) {
                for (int j = 0; j < A.length; j++) {
                    if (A[i] == A[j]) {
                        times++;
                    }
                }
                set.add(A[i]);
                if (times % 2 == 1) {
                    return A[i];
                }
            }
        }
        return odd;
    }
}

/*
Given an array, find the int that appears an odd number of times.

There will always be only one integer that appears an odd number of times.


* import static java.util.Arrays.stream;

public class FindOdd {
  public static int findIt(int[] arr) {
    return stream(arr).reduce(0, (x, y) -> x ^ y);
  }
}


        System.out.println(FindOdd.findIt(new int[]{20,1,-1,2,-2,3,3,5,5,1,2,4,20,4,-1,-2,5}));
        System.out.println(FindOdd.findIt(new int[]{1,1,2,-2,5,2,4,4,-1,-2,5}));
        System.out.println(FindOdd.findIt(new int[]{20,1,1,2,2,3,3,5,5,4,20,4,5}));
        System.out.println(FindOdd.findIt(new int[]{10}));
        System.out.println(FindOdd.findIt(new int[]{1,1,1,1,1,1,10,1,1,1,1}));
        System.out.println(FindOdd.findIt(new int[]{5,4,3,2,1,5,4,3,2,10,10}));
* */