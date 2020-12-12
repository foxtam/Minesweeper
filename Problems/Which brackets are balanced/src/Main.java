import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final String brackets = scanner.next();
        System.out.println(isBalancedBrackets(brackets));
    }

    static boolean isBalancedBrackets(String brackets) {
        final Deque<Character> stack = new ArrayDeque<>();
        for (Character bracket : brackets.toCharArray()) {
            if ("([{".indexOf(bracket) != -1) {
                stack.addLast(bracket);
            } else {
                Character peekBracket = stack.pollLast();
                if (peekBracket == null) return false;
                if (peekBracket == '(' && bracket != ')'
                        || peekBracket == '[' && bracket != ']'
                        || peekBracket == '{' && bracket != '}') return false;
            }
        }
        return stack.isEmpty();
    }
}