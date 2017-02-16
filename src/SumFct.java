import java.math.BigInteger;

/**
 * Created by babai-lama
 * 09.02.2017 9:57.
 */
public class SumFct {
    public static BigInteger perimeter(BigInteger n) {
        // your code
        n = n.add(BigInteger.ONE);
        BigInteger res  = BigInteger.ZERO;
        BigInteger prev = BigInteger.ONE;
        BigInteger curr = BigInteger.ONE;
        BigInteger next = BigInteger.ZERO;
        while (n.compareTo(BigInteger.ZERO) >= 0) {
            prev = curr;
            curr = next;
            next = prev.add(curr);
            res = res.add(curr);
            System.out.println(n.toString()+" "+curr.toString());
            n = n.subtract(BigInteger.ONE);
        }
        return res.multiply(BigInteger.valueOf(4));
    }
}
