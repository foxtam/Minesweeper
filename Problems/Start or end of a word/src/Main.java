import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String part = scanner.nextLine();
        String line = scanner.nextLine();

        final Pattern pattern = Pattern.compile(part + "\\b|\\b" + part, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(line);
        System.out.println(matcher.find() ? "YES" : "NO");
    }
}