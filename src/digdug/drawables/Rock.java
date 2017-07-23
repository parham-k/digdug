package digdug.drawables;

import digdug.GameManager;
import digdug.values.Values;

public class Rock extends ScreenObject implements Runnable {

    private int hasKilled;
    public boolean isFalling, isFreeFalling;

    public Rock(GameManager game, int i, int j) {
        super(game, game.getImages().getRock()[0], i, j);
        hasKilled = 0;
        isRunning = false;
        isFreeFalling = false;
    }

    public void run() {
        if(!isFreeFalling) {
            isRunning = true;

            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            game.screen().removeRock(i, j);
            isFalling = true;

            while (isRunning && (i < Values.rowCount - 1 && !game.screen().hasGround(i + 1, j))
                    && (i < Values.rowCount)) {
                down();
            }

            isFalling = false;

            game.getSounds().rock();
            img = game.getImages().getRock()[2];
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            game.screen().removeObject(this);
            game.getInfo().addScore(x + w, y, getKillScore());
        }else {
            freefall();
        }
    }

    public void freefall() {
        isFalling = true;
        isMoving = true;
        game.screen().removeRock(i, j);
        System.out.println(y);
        System.out.println(h);
        System.out.println(game.screenHeight());
        while (y + h < game.screenHeight()) {
            y += (game.screenHeight() / Values.rowCount) / 5;
            try {
                Thread.currentThread().sleep(Values.movementSleepTime / 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        game.getSounds().rock();
        img = game.getImages().getRock()[2];
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        game.screen().removeObject(this);
    }


    public int getKillScore() {
        return hasKilled * 500;
    }

    public void down() {
        int dy = (game.screenWidth() / Values.rowCount) / 10;
        isMoving = true;
        for (int t = 0; t < 10; t++) {
            y += dy;
            try {
                Thread.currentThread().sleep(Values.movementSleepTime / 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        i++;
        isMoving = false;
    }

    public void kill() {
        hasKilled++;
    }

}
