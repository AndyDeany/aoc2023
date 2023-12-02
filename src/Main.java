import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.List;


public class Main
{
    private static final int DAYS = 2;

    public static void main(String[] args)
    {
        for (int day = 1; day <= DAYS; day++)
        {
            SolveDaysPuzzles(day);
        }
    }

    private static void SolveDaysPuzzles(int dayNumber)
    {
        Aoc aoc = getAocClassInstance(dayNumber);
        Method Puzzle1 = getPuzzleMethod(1);
        Method Puzzle2 = getPuzzleMethod(2);
        List<String> puzzleInput = ReadFile("aoc" + dayNumber + ".txt");

        System.out.println("\nDay " + dayNumber + ":");
        System.out.println(CallPuzzleMethod(aoc, Puzzle1, puzzleInput));
        System.out.println(CallPuzzleMethod(aoc, Puzzle2, puzzleInput));
    }

    public static List<String> ReadFile(String path)
    {
        try
        {
            return Files.readAllLines(FileSystems.getDefault().getPath("data", path));
        }
        catch (IOException e)
        {
            System.out.println(MessageFormat.format("No such file ''{0}''", path));
            throw new RuntimeException(e);
        }
    }

    private static Aoc getAocClassInstance(int number)
    {
        String className = "Aoc" + number;
        Class<?> AocClass;
        try
        {
            AocClass = Class.forName(className);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println(MessageFormat.format("No such class ''{0}''", className));
            throw new RuntimeException(e);
        }

        try
        {
            return (Aoc) AocClass.getDeclaredConstructor().newInstance();
        }
        catch (NoSuchMethodException e)
        {
            System.out.println(MessageFormat.format("Class ''{0}'' has no declared constructor", className));
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e)
        {
            System.out.println(MessageFormat.format("The constructor of class ''{0}'' is not public", className));
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e)
        {
            System.out.println(MessageFormat.format("The constructor of class ''{0}'' threw an exception", className));
            throw new RuntimeException(e);
        }
        catch (InstantiationException e)
        {
            System.out.println(MessageFormat.format("Class ''{0}'' is abstract", className));
            throw new RuntimeException(e);
        }
    }

    private static Method getPuzzleMethod(int puzzleMethodNumber)
    {
        String methodName = "Puzzle" + puzzleMethodNumber;
        try
        {
            return Aoc.class.getMethod(methodName, List.class);
        }
        catch (NoSuchMethodException e)
        {
            System.out.println(MessageFormat.format("Class has no method ''{0}''", methodName));
            throw new RuntimeException(e);
        }
    }

    private static int CallPuzzleMethod(Aoc aoc, Method PuzzleMethod, List<String> puzzleInput)
    {
        try
        {
            return (int) PuzzleMethod.invoke(aoc, puzzleInput);
        }
        catch (IllegalAccessException e)
        {
            System.out.println(MessageFormat.format("The constructor of class ''{0}'' is not public", PuzzleMethod.getName()));
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e)
        {
            System.out.println(MessageFormat.format("The ''{0}.{1}'' method is not public", aoc.getClass().getName(), PuzzleMethod.getName()));
            throw new RuntimeException(e);
        }
    }
}
