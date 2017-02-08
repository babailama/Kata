/**
 * Created by ivanov-av
 * 05.01.2017 13:52.
 */
public class Magnets {
    public static double doubles(int maxk, int maxn) {
        double ret = 0;
        for (int k = 1; k <= maxk; k++) {
            for (int n = 1; n <= maxn; n++) {
                ret += v(k, n);
            }
        }
        return ret;
    }

    private static double v(int k, int n) {
        return 1 / (k * Math.pow(n + 1, 2 * k));
    }
}
