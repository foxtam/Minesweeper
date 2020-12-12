package minesweeper;

import java.util.Arrays;

public class Minefield {
    private static final char emptyCellSymbol = '.';
    private static final char mineSymbol = 'X';
    private static final char markedCellSymbol = '*';

    private final int width;
    private final int height;
    private final int mineNumber;
    private final char[][] userField;
    private final char[][] mineField;

    public Minefield(int width, int height, int mineNumber) {
        this.width = width;
        this.height = height;
        this.mineNumber = mineNumber;
        this.mineField = getInitMineField();
        this.userField = getInitUserField();
    }

    private char[][] getInitMineField() {
        char[][] field = new char[height][width];
        for (char[] chars : field) {
            Arrays.fill(chars, '.');
        }
        for (int i = 0; i < mineNumber; ) {
            int row = (int) (Math.random() * height);
            int column = (int) (Math.random() * width);
            if (field[row][column] == emptyCellSymbol) {
                field[row][column] = mineSymbol;
                i++;
            }
        }
        return field;
    }

    private char[][] getInitUserField() {
        char[][] field = new char[height][width];
        for (char[] chars : field) {
            Arrays.fill(chars, '.');
        }
        return field;
    }

    public void countMinesAroundCells() {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (mineField[row][column] == mineSymbol) continue;
                int minesNumber = countMinesAroundCurrentCell(row, column);
                if (minesNumber > 0) {
                    userField[row][column] = String.valueOf(minesNumber).charAt(0);
                }
            }
        }
    }

    private int countMinesAroundCurrentCell(int row, int column) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                if (isInField(row + i, column + j) && hasMineAt(row + i, column + j)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isInField(int row, int column) {
        return row >= 0 && row < height && column >= 0 && column < width;
    }

    private boolean hasMineAt(int row, int column) {
        return mineField[row][column] == mineSymbol;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(" │123456789│\n—│—————————│\n");
        for (int i = 0; i < userField.length; i++) {
            builder.append(i + 1).append("|").append(userField[i]).append("|\n");
        }
        builder.append("—│—————————│\n");
        return builder.toString();
    }

    public boolean allMinesMarkedOnly() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (userField[i][j] == markedCellSymbol && mineField[i][j] != mineSymbol
                        || userField[i][j] != markedCellSymbol && mineField[i][j] == mineSymbol) return false;
            }
        }
        return true;
    }

    public void markCell(Point point) throws WrongCoordinatesException {
        point = new Point(point.column - 1, point.row - 1);
        switch (userField[point.row][point.column]) {
            case emptyCellSymbol:
                userField[point.row][point.column] = markedCellSymbol;
                break;
            case markedCellSymbol:
                userField[point.row][point.column] = emptyCellSymbol;
                break;
            default:
                throw new WrongCoordinatesException();
        }
    }
}
