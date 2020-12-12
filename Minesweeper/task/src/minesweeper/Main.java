package minesweeper;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("How many mines do you want on the field? > ");
        int mineNumber = Integer.parseInt(scanner.nextLine());
        final var field = new Minefield(9, 9, mineNumber);
        field.countMinesAroundCells();
        System.out.println(field);

        play(field);
    }

    static void play(Minefield minefield) {
        while (!minefield.allMinesMarkedOnly()) {
            System.out.print("Set/delete mine marks (x and y coordinates): > ");
            Point point = readUserPoint();
            markCellAndPrintField(minefield, point);
        }
        System.out.println("Congratulations! You found all the mines!");
    }

    private static Point readUserPoint() {
        final String[] line = scanner.nextLine().split("\\s+");
        return new Point(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
    }

    private static void markCellAndPrintField(Minefield minefield, Point point) {
        try {
            minefield.markCell(point);
            System.out.println(minefield);
        } catch (WrongCoordinatesException e) {
            System.out.println("There is a number here!");
        }
    }
}
