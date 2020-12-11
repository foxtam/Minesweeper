package minesweeper;

import java.util.Arrays;

public class Minefield {
    private static final char emptyCellSymbol = '.';
    private static final char mineSymbol = 'X';

    private final int width;
    private final int height;
    private final int mineNumber;
    private final char[][] field;

    public Minefield(int width, int height, int mineNumber) {
        this.width = width;
        this.height = height;
        this.mineNumber = mineNumber;
        this.field = getInitMineField();
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

    public void countMinesAroundCells() {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (field[row][column] == mineSymbol) continue;
                int minesNumber = countMinesAroundCurrentCell(row, column);
                if (minesNumber > 0) {
                    field[row][column] = String.valueOf(minesNumber).charAt(0);
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
        return field[row][column] == mineSymbol;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (char[] chars : field) {
            builder.append(chars).append('\n');
        }
        return builder.toString();
    }
}
