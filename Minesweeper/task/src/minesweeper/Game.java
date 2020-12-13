package minesweeper;

import java.util.Objects;
import java.util.Scanner;

public class Game {
    static Scanner scanner = new Scanner(System.in);

    private final Minefield minefield;
    private GameStatus gameStatus = GameStatus.CONTINUE;

    public Game() {
        System.out.print("How many mines do you want on the field? > ");
        int mineNumber = Integer.parseInt(scanner.nextLine());
        this.minefield = new Minefield(9, 9, mineNumber);
    }

    public void play() {
        System.out.println(minefield);
        while (gameStatus == GameStatus.CONTINUE) {
            userLoop();
            setGameStatus();
        }
        if (gameStatus == GameStatus.WIN) {
            System.out.println("Congratulations! You found all the mines!");
        } else {
            System.out.println("You stepped on a mine and failed!");
        }
    }

    private void userLoop() {
        try {
            tryUserAttempt();
        } catch (WrongCoordinatesException e) {
            System.out.println("There is a number here!");
        }
    }

    private void setGameStatus() {
        if (minefield.areAllMinesMarkedOnly()) {
            gameStatus = GameStatus.WIN;
        } else if (minefield.isMineClaimed()) {
            gameStatus = GameStatus.LOSE;
        }
    }

    private void tryUserAttempt() throws WrongCoordinatesException {
        System.out.print("Set/unset mines marks or claim a cell as free: > ");
        Command command = readUserInput();
        switch (command.action) {
            case "mine":
                minefield.markCell(minefield.new Point(command.point));
                break;
            case "free":
                minefield.claimCell(minefield.new Point(command.point));
                break;
            default:
                throw new IllegalArgumentException();
        }
        System.out.println(minefield);
    }

    private Command readUserInput() {
        String[] line = scanner.nextLine().split("\\s+");
        Point point = new Point(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
        return new Command(point, line[2]);
    }

    public static class Point {
        public final int row;
        public final int column;

        public Point(Minefield.Point point) {
            this(point.column + 1, point.row + 1);
        }

        public Point(int column, int row) {
            if (Math.min(row, column) < 1) {
                throw new IllegalArgumentException();
            }
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return row == point.row && column == point.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }
}
