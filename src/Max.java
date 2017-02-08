import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by ivanov-av
 * 05.01.2017 11:36.
 */
public class Max {
    public static int sequence(int[] array) {
        int max = 0;
        if (array.length == 0) {
            return max;
        }
        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                int test = 0;
                test = Arrays.stream(Arrays.copyOfRange(array, j, j + i)).reduce((x, y) -> x + y).getAsInt();
                if (test > max) {
                    max = test;
                }
            }
        }
        return max;
    }
}
