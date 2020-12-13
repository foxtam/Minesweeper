import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final String input = scanner.nextLine();
        System.out.println(isCorrectIP(input) ? "YES" : "NO");
    }

    private static boolean isCorrectIP(String input) {
        final String ipNumber = "(2\\d[0-5]|1\\d{2}|[1-9]\\d|\\d)";
        final Pattern pattern = Pattern.compile(ipNumber + "(\\." + ipNumber + "){3}");
        return pattern.matcher(input).matches();
    }
}