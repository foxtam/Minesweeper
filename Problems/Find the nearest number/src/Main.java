import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final String numbersAsString = scanner.nextLine();
        final int N = scanner.nextInt();

        final ArrayList<Integer> numbers = new ArrayList<>();
        for (String n : numbersAsString.split("\\s+")) {
            numbers.add(Integer.valueOf(n));
        }

        ArrayList<Integer> closest = new ArrayList<>();
        int diff = Integer.MAX_VALUE;
        for (Integer x : numbers) {
            final int currentDiff = Math.abs(N - x);
            if (currentDiff < diff) {
                closest.clear();
                closest.add(x);
                diff = currentDiff;
            } else if (currentDiff == diff) {
                closest.add(x);
            }
        }
        Collections.sort(closest);

        for (Integer x : closest) {
            System.out.print(x + " ");
        }
    }
}