package minesweeper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private final Set<Point> visitedCells = new HashSet<>();

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

    private boolean isInField(int row, int column) {
        return row >= 0 && row < height && column >= 0 && column < width;
    }

    private boolean isInField(Point point) {
        return isInField(point.row, point.column);
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
        visitCell(point);
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

    private void visitCell(Point point) {
        visitedCells.add(point);
        if (hasMineInCell(point)) {
            userField[point.row][point.column] = mineSymbol;
            return;
        }
        int mineNumber = countMinesInNearCells(point);
        if (mineNumber > 0) {
            userField[point.row][point.column] = String.valueOf(mineNumber).charAt(0);
        } else {
            userField[point.row][point.column] = '/';
            visitNearCells(point);
        }
    }

    private int countMinesInNearCells(Point point) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Point nextPoint = new Point(point.row + i, point.column + j);
                if (isInField(nextPoint) && hasMineInCell(nextPoint)) {
                    count++;
                }
            }
        }
        if (hasMineInCell(point)) {
            count--;
        }
        return count;
    }

    private boolean hasMineInCell(Point point) {
        return mineField[point.row][point.column] == mineSymbol;
    }

    private void visitNearCells(Point point) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Point nextPoint = new Point(point.row + i, point.column + j);
                if (isInField(nextPoint) && !visitedCells.contains(nextPoint)) {
                    visitCell(nextPoint);
                }
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
}
