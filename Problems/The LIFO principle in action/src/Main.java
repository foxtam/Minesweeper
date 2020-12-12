import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final Deque<String> stack = new ArrayDeque<>();
        int size = scanner.nextInt();
        for (int i = 0; i < size; i++) {
            stack.addLast(scanner.next());
        }
        for (int i = 0; i < size; i++) {
            System.out.println(stack.removeLast());
        }
    }
}