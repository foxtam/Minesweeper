package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? > ");
        int mineNumber = scanner.nextInt();
        final var field = new Minefield(9, 9, mineNumber);
        field.countMinesAroundCells();
        System.out.println(field);
    }
}
