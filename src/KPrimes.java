import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Created by ivanov-av
 * 30.01.2017 14:47.
 */
public class KPrimes {
    public static long[] countKprimes(int k, long start, long end) {

        // your code
        TreeSet<Long> treeSet = new TreeSet<>();
        TreeSet<Long> tmpSet = new TreeSet<>();
        long[] lArray = primeSequence(end).toArray();
        long[] tmpArray = lArray;
        Arrays.stream(lArray).forEach(i->treeSet.add((Long) i));
        for (int i = 0; i <k; i++) {
            for (int j=0;j<lArray.length;j++){
                tmpArray[j]*=lArray[j];
            }
            Arrays.stream(tmpArray).forEach(a->treeSet.add((Long) a));
        }
        return lArray;
    }

    public static int puzzle(int s) {
        // your code
        return 0;
    }

    public static boolean isPrime(long x) {
        return LongStream.rangeClosed(2, (long) (Math.sqrt(x)))
                .allMatch(n -> x % n != 0);
    }

    public static LongStream primeSequence(long max) {
        return LongStream.iterate(2, i -> i + 1)
                .filter(x -> isPrime(x))
                .limit(max);
    }

}
