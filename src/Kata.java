import java.util.Arrays;

/**
 * Created by ivanov-av
 * 04.01.2017 16:51.
 */
public class Kata {
    public static int findEvenIndex(int[] arr) {
        // your code
        int ret = -1;
        if (arr.length < 3) {
            return ret;
        }
        for (int i = 1; i < arr.length; i++) {
            //int[] array1 = new int[i];
            //int[] array2 = new int[arr.length - 1];
            //System.arraycopy(arr,0,array1,array1.length);
            int[] array1 = Arrays.copyOfRange(arr,0,i);
            int[] array2 = Arrays.copyOfRange(arr,i+1,arr.length);
            if (Arrays.stream(array1).sum() == Arrays.stream(array2).sum()) {
                ret = i;
                break;
            }
        }

        return ret;
    }

    public static void main(String[] args) {
        System.out.println(findEvenIndex(new int[] {1,2,3,4,3,2,1}));
        System.out.println(findEvenIndex(new int[] {1,100,50,-51,1,1}));
        System.out.println(findEvenIndex(new int[] {1,2,3,4,5,6}));
        System.out.println(findEvenIndex(new int[] {20,10,30,10,10,15,35}));
        System.out.println(findEvenIndex(new int[] {-8505, -5130, 1926, -9026}));
        System.out.println(findEvenIndex(new int[] {2824, 1774, -1490, -9084, -9696, 23094}));
        System.out.println(findEvenIndex(new int[] {4, 5, 6, 7, 8, 9, 10, 9, 8, 7, 6, 5, 4}));
    }
}
