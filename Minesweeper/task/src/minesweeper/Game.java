package minesweeper;

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
                minefield.markCell(command.point);
                break;
            case "free":
                minefield.claimCell(command.point);
                break;
            default:
                throw new IllegalArgumentException();
        }
        System.out.println(minefield);
    }

    private Command readUserInput() {
        String[] line = scanner.nextLine().split("\\s+");
        Point point = new Point(Integer.parseInt(line[1]) - 1, Integer.parseInt(line[0]) - 1);
        return new Command(point, line[2]);
    }
}
