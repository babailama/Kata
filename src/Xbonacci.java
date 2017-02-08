import java.util.Arrays;

/**
 * Created by ivanov-av
 * 05.01.2017 9:32.
 */
public class Xbonacci {
    public double[] xbonacci(double[] signature, int n) {
        // hackonacci me
        int x = signature.length;
        //System.out.println(" sig "+x+" n " +n);
        double[] ret = new double[x>n?x:n];
        double[] doubles = new double[n];
        System.arraycopy(signature,0,ret,0,x);
        for (int i = x; i < n; i++) {
            double[] tmp = Arrays.copyOfRange(ret, i - x, i);
            ret[i] = Arrays.stream(tmp).sum();
        }
        doubles = Arrays.copyOfRange(ret, 0, n);
        return doubles;
    }
}
