package digdug.drawables.monsters;

import digdug.GameManager;
import digdug.drawables.ScreenObject;
import digdug.values.Direction;
import digdug.values.Values;

import java.awt.*;
import java.util.ArrayList;

public abstract class Monster extends ScreenObject implements Runnable {

    int health;
    public boolean isExploding, isLastMonster, isGhost;
    private Image[] dieAnimation, moveRightAnimaiton, moveLeftAnimaiton,
            moveUpAnimaiton, moveDownAnimaiton, ghostAnimation;
    ArrayList<Direction> possibleMoves;
    private Direction dir;

    public Monster(GameManager game, Image img, Image[] dieAnimation, int i, int j) {
        super(game, img, i, j);
        health = 3;
        this.dieAnimation = dieAnimation;
        possibleMoves = new ArrayList<>();
    }

    public void setMoveAnimations(Image[] moveUpAnimaiton, Image[] moveDownAnimaiton, Image[] moveLeftAnimaiton, Image[] moveRightAnimaiton) {
        this.moveUpAnimaiton = moveUpAnimaiton;
        this.moveDownAnimaiton = moveDownAnimaiton;
        this.moveLeftAnimaiton = moveLeftAnimaiton;
        this.moveRightAnimaiton = moveRightAnimaiton;
    }

    public void draw(Graphics g) {
        if (isExploding && health == 3)
            img = dieAnimation[0];
        else if (isExploding && health == 2)
            img = dieAnimation[1];
        else if (isExploding && health == 1)
            img = dieAnimation[2];
        else if (isExploding && health == 0)
            img = dieAnimation[3];
        super.draw(g);
    }

    public void run() {
        boolean canContinueMovement = false;
        while (health > 0 && isRunning) {
            if (!isExploding && !isLastMonster) {
                health = 3;
                if (dir == Direction.UP) canContinueMovement = canMoveUp();
                if (dir == Direction.DOWN) canContinueMovement = canMoveDown();
                if (dir == Direction.LEFT) canContinueMovement = canMoveLeft();
                if (dir == Direction.RIGHT) canContinueMovement = canMoveRight();
                if (canContinueMovement) canContinueMovement = possibleMoves.equals(getPossibleMoves());
                if (canContinueMovement) {
                    if (dir == Direction.UP) up();
                    if (dir == Direction.DOWN) down();
                    if (dir == Direction.LEFT) left();
                    if (dir == Direction.RIGHT) right();
                } else if (possibleMoves.size() <= 1 && (int) (Math.random() * 20) == 10) {
                    ghostMove();
                    updatePossibleMoves();
                } else {
                    updatePossibleMoves();
                    int move = (int) (Math.random() * possibleMoves.size());
                    if (possibleMoves.size() > 0)
                        dir = possibleMoves.get(move);
                    stopAnimation();
                    if (dir == Direction.UP) up();
                    if (dir == Direction.DOWN) down();
                    if (dir == Direction.LEFT) left();
                    if (dir == Direction.RIGHT) right();
                }
                stopAnimation();
                try {
                    Thread.currentThread().sleep(Values.movementSleepTime / 4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (!isExploding && isLastMonster) {
                escape();
            } else {
                while (isExploding && game.player().isPumping && health > 0) {
                    isMoving = false;
                    health--;
                    try {
                        Thread.currentThread().sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!game.player().isPumping) {
                isExploding = false;
            }
        }

        if (isExploding || health <= 0)
            die();

    }

    //// FIXME: 2016-06-20 optimize ghost move
    public void ghostMove() {
        isMoving = true;
        int a = game.player().i, b = game.player().j;
        int dx = (game.player().x - x) / 100;
        int dy = (game.player().y - y) / 100;
        setAnimation(ghostAnimation, 500, true);
        for (int t = 0; t < 100; t++) {
            x += dx;
            y += dy;
            try {
                Thread.currentThread().sleep(Values.ghostSleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        i = a;
        j = b;
        x = j * w;
        y = i * h;
    }

    public void die() {
        game.getSounds().enemyPop();
        game.getInfo().addScore(x + w, y, (i / 4 + 1) * 250);
        game.getInfo().loseMonster();
        game.screen().removeObject(this);
    }

    public void updatePossibleMoves() {
        possibleMoves.clear();
        possibleMoves = getPossibleMoves();
    }

    //// TODO: 2016-06-20 make monsters smarter
    public ArrayList<Direction> getPossibleMoves() {
        ArrayList<Direction> result = new ArrayList<>();
        if (canMoveUp()) result.add(Direction.UP);
        if (canMoveDown()) result.add(Direction.DOWN);
        if (canMoveRight()) result.add(Direction.RIGHT);
        if (canMoveLeft()) result.add(Direction.LEFT);
        if (game.player().i == i && game.player().j > j && canMoveRight()) result.add(Direction.RIGHT);
        if (game.player().i == i && game.player().j < j && canMoveLeft()) result.add(Direction.LEFT);
        if (game.player().j == j && game.player().i > i && canMoveUp()) result.add(Direction.UP);
        if (game.player().j == j && game.player().i < i && canMoveDown()) result.add(Direction.DOWN);
        return result;
    }

    public void escape() {
        while (!isExploding && isLastMonster) {
            isMoving = true;
            stopAnimation();
            setAnimation(ghostAnimation, 500, true);
            while (i > 0 && !isExploding) {
                for (int t = 0; t < 10; t++) {
                    y -= (game.screenHeight() / Values.rowCount) / 10;
                    try {
                        Thread.currentThread().sleep(Values.ghostSleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                i--;
            }
            if (isExploding)
                isMoving = false;
            y = 0;
            while (j > 0 && !isExploding) left();
            game.screen().removeObject(this);
            game.getInfo().loseMonster();
            isLastMonster = false;
        }
    }

    public boolean canMoveUp() {
        return i > 0
                && !game.screen().hasWall(i - 1, j, Direction.DOWN)
                && !game.screen().hasWall(i, j, Direction.UP);
    }

    public boolean canMoveDown() {
        return i < Values.rowCount - 1
                && !game.screen().hasWall(i + 1, j, Direction.UP)
                && !game.screen().hasWall(i, j, Direction.DOWN);
    }

    public boolean canMoveLeft() {
        return j > 0
                && !game.screen().hasWall(i, j - 1, Direction.RIGHT)
                && !game.screen().hasWall(i, j, Direction.LEFT);
    }

    public boolean canMoveRight() {
        return j < Values.columnCount - 1
                && !game.screen().hasWall(i, j + 1, Direction.LEFT)
                && !game.screen().hasWall(i, j, Direction.RIGHT);
    }

    public void up() {
        if (canMoveUp()) {
            setAnimation(moveUpAnimaiton, Values.movementSleepTime, true);
            isMoving = true;
            int dy = (game.screenWidth() / Values.rowCount) / 10;
            for (int t = 0; t < 10; t++) {
                y -= dy;
                try {
                    Thread.currentThread().sleep(Values.movementSleepTime / 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isMoving = false;
            stopAnimation();
            i--;
        }
    }

    public void down() {
        if (canMoveDown()) {
            setAnimation(moveDownAnimaiton, Values.movementSleepTime, true);
            isMoving = true;
            int dy = (game.screenWidth() / Values.rowCount) / 10;
            for (int t = 0; t < 10; t++) {
                y += dy;
                try {
                    Thread.currentThread().sleep(Values.movementSleepTime / 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i++;
            isMoving = false;
            stopAnimation();
        }
    }

    public void left() {
        if (canMoveLeft()) {
            setAnimation(moveLeftAnimaiton, Values.movementSleepTime, true);
            isMoving = true;
            int dx = (game.screenWidth() / Values.columnCount) / 10;
            for (int t = 0; t < 10; t++) {
                x -= dx;
                try {
                    Thread.currentThread().sleep(Values.movementSleepTime / 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isMoving = false;
            j--;
            stopAnimation();
        }
    }

    public void right() {
        if (canMoveRight()) {
            setAnimation(moveRightAnimaiton, Values.movementSleepTime, true);
            isMoving = true;
            int dx = (game.screenWidth() / Values.columnCount) / 10;
            for (int t = 0; t < 10; t++) {
                x += dx;
                try {
                    Thread.currentThread().sleep(Values.movementSleepTime / 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isMoving = false;
            j++;
            stopAnimation();
        }
    }

    public void setGhostAnimation(Image[] ghostAnimation) {
        this.ghostAnimation = ghostAnimation;
    }

    public void setDieAnimation(Image[] dieAnimation) {
        this.dieAnimation = dieAnimation;
    }
}
