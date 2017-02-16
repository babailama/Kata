/**
 * Created by ivanov-av
 * 09.02.2017 10:52.
 */
public class ToSmallest {
    public static long[] smallest(long n) {
        // your code
        int[] digits = Long.toString(n).chars().map(c -> c-='0').toArray();
        return new long[]{n,0,0};
    }
}
