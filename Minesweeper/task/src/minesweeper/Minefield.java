package minesweeper;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Minefield {
    private static final char emptyCellSymbol = '.';
    private static final char mineSymbol = 'X';
    private static final char markedCellSymbol = '*';

    private final int width;
    private final int height;
    private final int mineNumber;
    private char[][] userField;
    private char[][] mineField;
    private boolean noOneClaimCell = true;

    public Minefield(int width, int height, int mineNumber) {
        this.width = width;
        this.height = height;
        this.mineNumber = mineNumber;
        initUserField();
    }

    private void initUserField() {
        userField = new char[height][width];
        for (char[] chars : userField) {
            Arrays.fill(chars, '.');
        }
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

    public boolean areAllMinesMarkedOnly() {
        if (mineField == null) return false;

        List<Character> significant = List.of(markedCellSymbol, emptyCellSymbol);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boolean equalsAny = significant.contains(userField[i][j]);
                if (equalsAny && mineField[i][j] != mineSymbol
                        || !equalsAny && mineField[i][j] == mineSymbol) return false;
            }
        }
        return true;
    }

    public void markCell(Point point) throws WrongCoordinatesException {
        point = new Point(point.row - 1, point.column - 1);
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

    public void claimCell(Point point) {
        if (noOneClaimCell) {
            noOneClaimCell = false;
            initMineFieldWith(point);
        }

    }

    private void initMineFieldWith(Point freePoint) {
        mineField = new char[height][width];
        for (char[] chars : mineField) {
            Arrays.fill(chars, '.');
        }
        for (int i = 0; i < mineNumber; ) {
            int row = (int) (Math.random() * height);
            int column = (int) (Math.random() * width);
            if (mineField[row][column] == emptyCellSymbol && !new Point(row, column).equals(freePoint)) {
                mineField[row][column] = mineSymbol;
                i++;
            }
        }
    }

    public boolean isMineClaimed() {
        for (char[] row : userField) {
            for (char ch : row) {
                if (ch == mineSymbol) {
                    return true;
                }
            }
        }
        return false;
    }

    public class Point {
        public final int row;
        public final int column;

        public Point(Game.Point point) {
            this(point.row - 1, point.column - 1);
        }

        public Point(int row, int column) {
            if (Math.min(row, column) < 0 || row >= height || column >= width) {
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
