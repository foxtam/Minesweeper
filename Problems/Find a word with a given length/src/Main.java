import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = Integer.parseInt(scanner.nextLine());
        String line = scanner.nextLine();

        final Pattern pattern = Pattern.compile(String.format("\\b[a-zA-Z]{%d}\\b", size));
        final Matcher matcher = pattern.matcher(line);
        System.out.println(matcher.find() ? "YES" : "NO");
    }
}