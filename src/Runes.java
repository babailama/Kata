import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ivanov-av
 * 10.01.2017 9:34.
 */
public class Runes {

    public static int solveExpression(final String expression) {
        int missingDigit = -1;

        //Write code to determine the missing digit or unknown rune
        //Expression will always be in the form
        //(number)[opperator](number)=(number)
        //Unknown digit will not be the same as any other digits used in expression

        for (int i = 0; i < 10; i++) {
            Character c = (char) (i + 48);
            if (expression.indexOf(c) == -1) {
                String exp = expression;
                exp = exp.replace('?', c);
                if (eval(exp)) {
                    missingDigit = i;
                    break;
                }
            }
        }
        return missingDigit;
    }

    private static boolean eval(String expression) {
        boolean evalResult = false;
        String rx = "(-?\\d+)([\\\\+-\\\\*])(-?\\d+)(=)(-?\\d+)";
        Pattern p = Pattern.compile(rx);
        Matcher m = p.matcher(expression);
        if (m.matches()) {
            String op1str = m.group(1);
            Integer operand1 = Integer.parseInt(op1str);
            String operator = m.group(2);
            String op2str = m.group(3);
            Integer operand2 = Integer.parseInt(op2str);
            String resStr = m.group(5);
            Integer result = Integer.parseInt(resStr);
            if ((!"00".equals(op1str)) && (!"00".equals(op2str)) && (!"00".equals(resStr))) {
                if ("+".equals(operator)) {
                    if (result == operand1 + operand2) {
                        evalResult = true;
                    }
                }
                if ("-".equals(operator)) {
                    if (result == operand1 - operand2) {
                        evalResult = true;
                    }
                }
                if ("*".equals(operator)) {
                    if (result == operand1 * operand2) {
                        evalResult = true;
                    }
                }
            }
        }
        return evalResult;
    }
}
