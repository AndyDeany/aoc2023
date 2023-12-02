import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aoc2 extends Aoc
{
    private static final int RED_LIMIT = 12;
    private static final int GREEN_LIMIT = 13;
    private static final int BLUE_LIMIT = 14;

    public int Puzzle1(List<String> puzzleInput)
    {
        int sum = 0;
        for (String gameString : puzzleInput)
        {
            Game game = new Game(gameString);
            if (IsValidGame(game)) { sum += game.id; }
        }
        return sum;
    }

    private boolean IsValidGame(Game game)
    {
        for (CubeSet set : game.sets)
        {
            if (set.red > RED_LIMIT) { return false; }
            if (set.green > GREEN_LIMIT) { return false; }
            if (set.blue > BLUE_LIMIT) { return false; }
        }
        return true;
    }

    public int Puzzle2(List<String> puzzleInput)
    {
        int sum = 0;
        for (String gameString : puzzleInput)
        {
            Game game = new Game(gameString);
            sum += game.power;
        }
        return sum;
    }
}


class Game
{
    private final static Pattern idPattern = Pattern.compile("Game (?<id>\\d+)");
    private final static Pattern setPattern = Pattern.compile("(?<set>(?:\\d+ (?:red|green|blue)(?:, )?)+)(?:;|$)");

    public int id;
    public List<CubeSet> sets = new ArrayList<>();
    public int power;

    public Game(String gameString)
    {
        Matcher idMatcher = idPattern.matcher(gameString);
        idMatcher.find();
        id = Integer.parseInt(idMatcher.group("id"));
        Matcher matcher = setPattern.matcher(gameString);
        while (matcher.find())
        {
            sets.add(new CubeSet(matcher.group("set")));
        }

        CalculatePower();
    }

    private void CalculatePower()
    {
        int red = 0;
        int green = 0;
        int blue = 0;

        for (CubeSet set : sets)
        {
            if (set.red > red) { red = set.red; }
            if (set.green > green) { green = set.green; }
            if (set.blue > blue) { blue = set.blue; }
        }

        power = red * green * blue;
    }
}


class CubeSet
{
    private final static Pattern pattern = Pattern.compile("(?:(?:(?<red>\\d+) red|(?<green>\\d+) green|(?<blue>\\d+) blue)(?:, )?)+$");

    private final Matcher matcher;

    public int red;
    public int green;
    public int blue;

    public CubeSet(String setString)
    {
        matcher = pattern.matcher(setString);
        matcher.find();
        red = getCubeAmount("red");
        green = getCubeAmount("green");
        blue = getCubeAmount("blue");
    }

    private Integer getCubeAmount(String cubeColor)
    {
        String amount = matcher.group(cubeColor);
        if (amount == null) { return 0; }
        return Integer.parseInt(amount);
    }
}
