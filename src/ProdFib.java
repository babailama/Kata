import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by babai-lama
 * 08.02.2017 7:51.
 */
public class ProdFib {
    private final double PHI = 1.6180339887498948482045868343656;
    private final double LOGPHI = Math.log(PHI);


    public static long[] productFib(long prod) {
        // your code
        return new ProdFib().getSolution(prod);
    }

    private long[] getSolution(long prod) {
        Fibo fibo = new Fibo();
        long n = Math.round(Math.log(Math.sqrt(5 * prod / PHI)) / LOGPHI);
        Long fiboNplus1 = fibo.getNthfibo(n + 1);
        Long fiboN = fibo.getNthfibo(n);
        while (fiboN * fiboNplus1 < prod) {
            n++;
            fiboN = fibo.getNthfibo(n);
            fiboNplus1 = fibo.getNthfibo(n + 1);
        }
        return new long[]{fiboN, fiboNplus1, fiboN * fiboNplus1 == prod ? 1 : 0};
    }


    public final class Fibo {

        private Fibo() {
        }

        public long getNthfibo(long n) {
            if (n < 0) {
                throw new IllegalArgumentException("The fibo value cannot be negative");
            }

            if (n <= 1) return n;

            long[][] result = {{1, 0}, {0, 1}}; // identity matrix.
            long[][] fiboM = {{1, 1}, {1, 0}};

            while (n > 0) {
                if (n % 2 == 1) {
                    multMatrix(result, fiboM);
                }
                n = n / 2;
                multMatrix(fiboM, fiboM);
            }

            return result[1][0];
        }

        private void multMatrix(long[][] m, long[][] n) {
            long a = m[0][0] * n[0][0] + m[0][1] * n[1][0];
            long b = m[0][0] * n[0][1] + m[0][1] * n[1][1];
            long c = m[1][0] * n[0][0] + m[1][1] * n[1][0];
            long d = m[1][0] * n[0][1] + m[1][1] * n[1][1];

            m[0][0] = a;
            m[0][1] = b;
            m[1][0] = c;
            m[1][1] = d;
        }
    }
}
