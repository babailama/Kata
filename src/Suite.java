import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Created by babai-lama
 * 08.02.2017 15:32.
 */
public class Suite {
    public static double going(int n) {
        return factorial(n).setScale(6).doubleValue();
    }

    public static BigDecimal factorial(int n)
    {
        BigDecimal ret = BigDecimal.ONE;
        BigDecimal ret2 = BigDecimal.ZERO;
        for (int i = 1; i <= n; ++i) {
            ret = ret.multiply(BigDecimal.valueOf(i));
            ret2 = ret2.add(ret);
        }
        return ret2.divide(ret,6, RoundingMode.FLOOR);
    }

}
