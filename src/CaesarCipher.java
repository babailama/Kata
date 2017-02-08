
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ivanov-av
 * 05.01.2017 14:33.
 */
public class CaesarCipher {

    public static List<String> movingShift(String s, int shift) {
        List<String> ret = new ArrayList<>();
        s = s.substring(0,2)+s;
        char[] charArray = s.toCharArray();
        charArray[0] = Character.toLowerCase(charArray[0]);
        charArray[1] = (char)(charArray[0]+1);
        for (int i = 2; i < charArray.length; i++) {
            int sh = 0;
            if (((65 <= (int) charArray[i]) && (90 >= (int) charArray[i]))) {
                sh = 65;
            }
            if (((97 <= (int) charArray[i]) && (122 >= (int) charArray[i]))) {
                sh = 97;
            }
            if (sh != 0) {
                charArray[i] = (char) (sh + ((int) charArray[i] - sh  + shift) % 26);
            }
            //if (charArray[i]>122) charArray[i] = (char)(charArray[i]-32);
        }
        String result = new String(charArray);
        int cs = chunkSize(result.length());
        ret = Stream.of(result)
                .map(w -> w.split("(?<=\\G.{" + cs + "})")).flatMap(Arrays::stream)
                .collect(Collectors.toList());
        /*while(ret.size()<5){
            ret.add("");
        }*/
        return ret;
    }


    private static int chunkSize(int size) {
        int ret = 0;
        if (size < 5) {
            ret = 1;
        } else {
            if (size % 5 == 0) {
                ret = size / 5;
            } else {
                ret = (int) Math.ceil((double) size / 5);
            }
        }
        return ret;
    }

    public static String demovingShift(List<String> s) {
        String str = String.join("", s);
        char first = str.charAt(0);
        char firstCoded = (char)(str.charAt(2));
        //str.replace(str.substring(0,2),"");
        if (firstCoded <= 90) {
            first = Character.toUpperCase(first);
        }
        int shift = firstCoded - first > 0 ? firstCoded - first: 26+(firstCoded - first);
        char[] charArray = new char[str.length()-2];
        for (int i =2;i<str.length();i++) charArray[i-2] = str.charAt(i);
        for (int i = 0; i < charArray.length; i++) {
            int sh = 0;
            if (((65 <= (int) charArray[i]) && (90 >= (int) charArray[i]))) {
                sh = 65;
            }
            if (((97 <= (int) charArray[i]) && (122 >= (int) charArray[i]))) {
                sh = 97;
            }
            if (sh != 0) {
                int code = (int) charArray[i] - sh - shift;
                while (code < 0) {
                    code += 26;
                }
                charArray[i] = (char) (code + sh);

            }

        }
        str = new String(charArray);
        return str;
    }
}
