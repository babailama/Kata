import java.util.*;

/**
 * Created by ivanov-av
 * 06.01.2017 13:19.
 * <p>
 * {'t','u','p'},
 * {'w','h','i'},
 * {'t','s','u'},
 * {'a','t','s'},
 * {'h','a','p'},
 * {'t','i','s'},
 * {'w','h','s'}
 */
public class SecretDetective {
    private char[][] triplets;

    //https://www.codewars.com/kata/recover-a-secret-string-from-random-triplets/train/java
    public String recoverSecret(char[][] triplets) {
        this.triplets = triplets;
        StringBuffer secret = new StringBuffer();
        while (true) {
            Character c = getFirstUniqChar();
            if (c == '*') {
                break;
            }
            secret.append(c);
            shift(c);
        }
        return secret.toString();
    }

    private void shift(char c) {
        for (int i = 0; i < this.triplets.length; i++) {
            if (this.triplets[i][0] == c) {
                this.triplets[i][0] = this.triplets[i][1];
                this.triplets[i][1] = this.triplets[i][2];
                this.triplets[i][2] = '*';
            }
        }
    }

    private Character getFirstUniqChar() {
        Character character;
        TreeSet<Character> firstColItems = new TreeSet<>();
        TreeSet<Character> twoLastColItems = new TreeSet<>();
        for (int i = 0; i < this.triplets.length; i++) {
            firstColItems.add(this.triplets[i][0]);
            twoLastColItems.add(this.triplets[i][1]);
            twoLastColItems.add(this.triplets[i][2]);
        }
        firstColItems.removeAll(twoLastColItems);
        if (firstColItems.isEmpty()) {
            character = '*';
        } else {
            character = firstColItems.first();
        }
        return character;
    }


}
