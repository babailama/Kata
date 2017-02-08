import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.LongStream;

/**
 * Created by ivanov-av
 * 05.01.2017 7:58.
 */
public class Divisors {
    public static String listSquared(long m, long n) {
        // your code
        ArrayList<Long[]> arr = new ArrayList<>();
        for (long i = m; i <= n; i++) {
            final long test = i;
            Long sum = LongStream.rangeClosed(1, i).filter(item -> test % item == 0).map(div -> div * div).sum();
            Long sqrt = ((Double) Math.sqrt(sum)).longValue();
            if (sum == sqrt * sqrt) {
                arr.add(new Long[]{i, sum});
            }
        }
        return Arrays.deepToString(arr.toArray());
    }

    public static void main(String[] args) {
        System.out.println(listSquared(1, 500));
        /*System.out.println(findEvenIndex(new int[] {1,100,50,-51,1,1}));
        System.out.println(findEvenIndex(new int[] {1,2,3,4,5,6}));
        System.out.println(findEvenIndex(new int[] {20,10,30,10,10,15,35}));
        System.out.println(findEvenIndex(new int[] {-8505, -5130, 1926, -9026}));
        System.out.println(findEvenIndex(new int[] {2824, 1774, -1490, -9084, -9696, 23094}));
        System.out.println(findEvenIndex(new int[] {4, 5, 6, 7, 8, 9, 10, 9, 8, 7, 6, 5, 4}));*/
    }
}
