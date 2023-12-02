import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Aoc1 extends Aoc
{
    private final Pattern pattern1 = Pattern.compile("\\d");
    private final Pattern pattern2 = Pattern.compile("(?=(\\d|one|two|three|four|five|six|seven|eight|nine))");
    private static final HashMap<String, Character> digitMap = new HashMap<>();

    public Aoc1()
    {
        digitMap.put("one", '1');
        digitMap.put("two", '2');
        digitMap.put("three", '3');
        digitMap.put("four", '4');
        digitMap.put("five", '5');
        digitMap.put("six", '6');
        digitMap.put("seven", '7');
        digitMap.put("eight", '8');
        digitMap.put("nine", '9');
    }

    public int Puzzle1(List<String> calibrationDocument)
    {
        int sum = 0;
        for (String line : calibrationDocument)
        {
            sum += CalibrationValue1(line);
        }

        return sum;
    }

    private int CalibrationValue1(String line)
    {
        Character first_digit = null;
        Character last_digit = null;
        for (Character c : line.toCharArray())
        {
            if (!isDigit(c)) { continue; }
            if (first_digit == null)
            {
                first_digit = c;
            }
            last_digit = c;
        }

        if (first_digit == null) { throw new IllegalArgumentException("No digit in line"); }

        return Integer.parseInt("" + first_digit + last_digit);
    }

    private boolean isDigit(char c)
    {
        return Character.isDigit(c);
    }

    public int Puzzle2(List<String> calibrationDocument)
    {
        int sum = 0;
        for (String line : calibrationDocument)
        {
            sum += CalibrationValue2(line);
        }

        return sum;
    }

    private int CalibrationValue2(String line)
    {
        return CalibrationValue(line, pattern2);
    }

    private int CalibrationValue(String line, Pattern pattern)
    {
        Matcher matcher = pattern.matcher(line);

        Character first_digit = null;
        Character last_digit = null;
        while (matcher.find())
        {
            char c = StringDigitToChar(matcher.group(1));
            if (first_digit == null)
            {
                first_digit = c;
            }
            last_digit = c;
        }

        if (first_digit == null) { throw new IllegalArgumentException("No digit in line: " + line); }

        return Integer.parseInt("" + first_digit + last_digit);
    }

    private char StringDigitToChar(String digit)
    {
        if (digit.length() == 1) { return digit.charAt(0); }
        return digitMap.get(digit);
    }
}
