import java.util.Arrays;

/**
 * Created by babai-lama
 * 07.02.2017 13:56.
 */
public class Dinglemouse {
    public static String histogram(final int results[]) {
        String res = "";
        int max = getMax(results);
        if (max == 0) return "-----------\n1 2 3 4 5 6\n";
        String[] temp = new String[max+3];
        StringBuffer sb = new StringBuffer();
        for (int curr = 0; curr <=max; curr++) {
            char[] tmp = new char[]{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '};
            for (int i = 0; i < results.length; i++) {
                if (results[i] - curr > 0) {
                    tmp[i*2] = '#';
                }
                if (results[i] - curr  == 0 && results[i] != 0) {
                    if (results[i]>9){
                        tmp[i*2] = Character.forDigit(results[i]/10, 10);
                        tmp[i*2 + 1] =  Character.forDigit(results[i] % 10, 10);
                    }else {
                        tmp[i * 2] = Character.forDigit(results[i], 10);
                    }
                }
                if (results[i] - curr < 0) {
                    tmp[i*2] = ' ';
                }
            }
            temp[max - curr] = new String(tmp).replaceAll("\\s+$","");
            temp[max - curr] += "\n";
        }
        temp[max+1] = "-----------\n";
        temp[max+2] = "1 2 3 4 5 6\n";
        Arrays.stream(temp).forEach(l->sb.append(l));
        return sb.toString();
    }


    public static int getMax(int[] inputArray) {
        int maxValue = inputArray[0];
        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[i] > maxValue) {
                maxValue = inputArray[i];
            }
        }
        return maxValue;
    }
}


//expected:<        1313[]        # #        ...>
// but was:<        1313[]        # #        ...>
//expected:<[]-----------1 2 3 4 ...>
// but was:<[        ]-----------        1 2 3 4 ...>