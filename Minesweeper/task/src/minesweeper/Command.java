package minesweeper;

public class Command {
    public final Point point;
    public final String action;

    public Command(Point point, String action) {
        this.point = point;
        this.action = action;
    }
}
