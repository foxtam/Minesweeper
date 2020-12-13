package minesweeper;

public class Command {
    public final Game.Point point;
    public final String action;

    public Command(Game.Point point, String action) {
        this.point = point;
        this.action = action;
    }
}
