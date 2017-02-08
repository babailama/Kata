/**
 * Created by ivanov-av
 * 02.02.2017 16:40.
 */
public class Solution {
    public static int zeros(int n) {
        // your beatiful code here
        int res = 0;
        double tmp = n;
        while (tmp>=1){
            tmp = tmp / 5;
            res += (int)Math.floor(tmp);
        }
        return res;
    }
}
