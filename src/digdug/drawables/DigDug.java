package digdug.drawables;

import digdug.GameManager;
import digdug.values.Direction;
import digdug.values.Values;

public class DigDug extends ScreenObject implements Runnable {

    public Direction movingDirection, facing;
    public boolean isAlive, isPumping;
    private Pump pump;

    public DigDug(GameManager game, int i, int j) {
        super(game, game.getImages().getDigdugStandRight(), i, j);
        movingDirection = Direction.NONE;
        facing = Direction.RIGHT;
        isAlive = true;
    }

    public void run() {
        while (isAlive && isRunning) {
            if (movingDirection == Direction.UP && !isMoving) up();
            if (movingDirection == Direction.DOWN && !isMoving) down();
            if (movingDirection == Direction.LEFT && !isMoving) left();
            if (movingDirection == Direction.RIGHT && !isMoving) right();
            else if (movingDirection == Direction.NONE) {
                if(facing == Direction.UP) img = game.getImages().getDigdugStandUp();
                if(facing == Direction.DOWN) img = game.getImages().getDigdugStandDown();
                if(facing == Direction.LEFT) img = game.getImages().getDigdugStandLeft();
                if(facing == Direction.RIGHT) img = game.getImages().getDigdugStandRight();
                game.getSounds().stopMoving();
            }
            try {
                Thread.currentThread().sleep(Values.movementSleepTime / 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isPumping && facing == Direction.UP) setAnimation(game.getImages().getDigdugPumpUp(), 50, true);
            else if (isPumping && facing == Direction.DOWN)
                setAnimation(game.getImages().getDigdugPumpDown(), 50, true);
            else if (isPumping && facing == Direction.LEFT)
                setAnimation(game.getImages().getDigdugPumpLeft(), 50, true);
            else if (isPumping && facing == Direction.RIGHT)
                setAnimation(game.getImages().getDigdugPumpRight(), 50, true);
            else stopAnimation();
        }
    }

    public void attack() {
        if (pump == null) {
            game.getSounds().pumpFire();
            pump = new Pump(game);
            game.screen().addObject(pump);
            if (facing == Direction.UP) setAnimation(game.getImages().getDigdugAttackUp(), 100, false);
            if (facing == Direction.DOWN) setAnimation(game.getImages().getDigdugAttackDown(), 100, false);
            if (facing == Direction.LEFT) setAnimation(game.getImages().getDigdugAttackLeft(), 100, false);
            if (facing == Direction.RIGHT) setAnimation(game.getImages().getDigdugAttackRight(), 100, false);
            (new Thread(pump)).start();
        }
    }

    public void stopAttack(boolean keyReleased) {
        if (pump != null)
            game.screen().removeObject(pump);
        if (keyReleased)
            pump = null;
        game.getSounds().pumpClose();
        if (facing == Direction.UP) img = game.getImages().getDigdugStandUp();
        if (facing == Direction.DOWN) img = game.getImages().getDigdugStandDown();
        if (facing == Direction.LEFT) img = game.getImages().getDigdugStandLeft();
        if (facing == Direction.RIGHT) img = game.getImages().getDigdugStandRight();
        isPumping = false;
    }

    public void die() {
        stopAnimation();
        game.getSounds().lifeLost();
        isAlive = false;
        img = game.getImages().getDigdugDie()[0];
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        movingDirection = Direction.NONE;
        game.getInfo().loseLife();
        if (game.getInfo().getLives() > 0)
            game.startLevel(game.getInfo().getLevel());
    }

    public boolean canMoveUp() {
        return i > 0 && !game.screen().hasRock(i - 1, j);
    }

    public boolean canMoveDown() {
        return i < Values.rowCount - 1 && !game.screen().hasRock(i + 1, j);
    }

    public boolean canMoveLeft() {
        return j > 0 && !game.screen().hasRock(i, j - 1);
    }

    public boolean canMoveRight() {
        return j < Values.columnCount - 1 && !game.screen().hasRock(i, j + 1);
    }

    public void up() {
        if (canMoveUp()) {
            game.getSounds().moving();
            stopAnimation();
            if (game.screen().hasGround(i - 1, j))
                game.getInfo().addScore(10);
            game.screen().removeGround(i, j, Direction.DOWN);
            isMoving = true;
            setAnimation(game.getImages().getDigdugMoveUp(), Values.movementSleepTime, true);
            int dy = (game.screenWidth() / Values.rowCount) / 10;
            for (int t = 0; t < 10; t++) {
                y -= dy;
                try {
                    Thread.currentThread().sleep(Values.movementSleepTime / 4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i--;
            isMoving = false;
            stopAnimation();
            img = game.getImages().getDigdugStandUp();
            game.screen().removeGround(i, j, Direction.UP);
            facing = Direction.UP;
        }
    }

    public void down() {
        if (canMoveDown()) {
            game.getSounds().moving();
            stopAnimation();
            if (game.screen().hasGround(i + 1, j))
                game.getInfo().addScore(10);
            game.screen().removeGround(i, j, Direction.UP);
            setAnimation(game.getImages().getDigdugMoveDown(), Values.movementSleepTime, true);
            isMoving = true;
            int dy = (game.screenWidth() / Values.rowCount) / 10;
            for (int t = 0; t < 10; t++) {
                y += dy;
                try {
                    Thread.currentThread().sleep(Values.movementSleepTime / 4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i++;
            stopAnimation();
            img = game.getImages().getDigdugStandDown();
            isMoving = false;
            game.screen().removeGround(i, j, Direction.DOWN);
            facing = Direction.DOWN;
        }
    }

    public void left() {
        if (canMoveLeft()) {
            game.getSounds().moving();
            stopAnimation();
            if (game.screen().hasGround(i, j - 1))
                game.getInfo().addScore(10);
            game.screen().removeGround(i, j, Direction.RIGHT);
            setAnimation(game.getImages().getDigdugMoveLeft(), Values.movementSleepTime, true);
            isMoving = true;
            int dx = (game.screenWidth() / Values.columnCount) / 10;
            for (int t = 0; t < 10; t++) {
                x -= dx;
                try {
                    Thread.currentThread().sleep(Values.movementSleepTime / 4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isMoving = false;
            stopAnimation();
            img = game.getImages().getDigdugStandLeft();
            j--;
            game.screen().removeGround(i, j, Direction.LEFT);
            facing = Direction.LEFT;
        }
    }

    public void right() {
        if (canMoveRight()) {
            game.getSounds().moving();
            stopAnimation();
            if (game.screen().hasGround(i, j + 1))
                game.getInfo().addScore(10);
            game.screen().removeGround(i, j, Direction.LEFT);
            setAnimation(game.getImages().getDigdugMoveRight(), Values.movementSleepTime, true);
            isMoving = true;
            int dx = (game.screenWidth() / Values.columnCount) / 10;
            for (int t = 0; t < 10; t++) {
                x += dx;
                try {
                    Thread.currentThread().sleep(Values.movementSleepTime / 4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isMoving = false;
            stopAnimation();
            img = game.getImages().getDigdugStandRight();
            j++;
            game.screen().removeGround(i, j, Direction.RIGHT);
            facing = Direction.RIGHT;
        }
    }

    public int getPumpLength() {
        if (pump != null)
            return pump.getLength();
        else
            return 0;
    }

}
