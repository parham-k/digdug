package digdug.drawables.monsters;

import digdug.GameManager;
import digdug.drawables.ScreenObject;
import digdug.values.Direction;
import digdug.values.Values;

public class DragonFire extends ScreenObject implements Runnable {

    private boolean hasHit;
    private int dx, moveSgn;

    public DragonFire(GameManager game, Direction dir, int i, int j) {
        super(game, game.getImages().getDragonFire(dir), i, j);
        isMoving = true;
        moveSgn = (dir == Direction.LEFT ? -1 : 1);
        dx = moveSgn * (game.screenWidth() / Values.columnCount) / 10;
    }

    public void run() {
        while (isRunning && !hasHit && x > 0 && x + w < game.screenWidth() && !game.screen().hasGround(i, j + moveSgn)) {
            x += dx;
            j = x / (game.screenWidth() / Values.columnCount);
            try {
                Thread.currentThread().sleep(Values.movementSleepTime / 4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        game.screen().removeObject(this);
    }

    public void hit() {
        hasHit = true;
    }

}
