package minesweeper;

import java.util.Arrays;

public class Minefield {
    private static char emptyCell = '.';
    private static char mine = 'X';

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
            if (field[row][column] == emptyCell) {
                field[row][column] = mine;
                i++;
            }
        }
        return field;
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
