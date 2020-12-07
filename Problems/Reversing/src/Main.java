import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String numberAsString = scanner.next();
        String reversed = "" + numberAsString.charAt(2) + numberAsString.charAt(1) + numberAsString.charAt(0);
        System.out.println(Integer.valueOf(reversed));
    }
}