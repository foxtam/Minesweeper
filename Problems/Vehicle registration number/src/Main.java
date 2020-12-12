import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String regNum = scanner.nextLine(); // a valid or invalid registration number

        String letter = "[ABEKMHOPCTYX]";
        String pattern = letter + "[0-9]{3}" + letter + "{2}";
        System.out.println(regNum.matches(pattern));
    }
}