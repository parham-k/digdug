package digdug.drawables;

import digdug.GameManager;
import digdug.values.Direction;
import digdug.values.Values;

import java.awt.*;

public class Pump extends ScreenObject implements Runnable {

    private int bigSide, smallSide;
    public boolean isGrowing;

    public Pump(GameManager game) {
        super(game, game.getImages().getPump(game.player().facing), game.player().i, game.player().j);
        bigSide = 0;
        isGrowing = true;
    }

    public void run() {
        int maxBigSide = game.screenHeight() / Values.columnCount * 2;
        while (isRunning && bigSide <= maxBigSide) {
            if(isGrowing)
                bigSide += maxBigSide / 10;
            try {
                Thread.currentThread().sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!game.player().isPumping)
            game.player().stopAttack(false);
    }

    public void draw(Graphics g) {
        x = 0;
        y = 0;
        w = 0;
        h = 0;
        img = game.getImages().getPump(game.player().facing);
        smallSide = 2 * (game.screenHeight() / Values.columnCount) / 5;
        if (game.player().facing == Direction.UP) {
            if (game.screen().hasWall(i - 1, j, Direction.DOWN)) bigSide = 0;
            else if (!game.screen().hasGround(i - 1, j) && game.screen().hasWall(i - 2, j, Direction.DOWN))
                bigSide /= 2;
            w = smallSide;
            h = bigSide;
            x = game.player().x + game.player().w / 2 - w / 2;
            y = game.player().y - h;
        } else if (game.player().facing == Direction.DOWN) {
            if (game.screen().hasWall(i + 1, j, Direction.UP)) bigSide = 0;
            else if (!game.screen().hasGround(i + 1, j) && game.screen().hasWall(i + 2, j, Direction.UP)) bigSide /= 2;
            w = smallSide;
            h = bigSide;
            x = game.player().x + game.player().w / 2 - w / 2;
            y = game.player().y + game.player().h;
        } else if (game.player().facing == Direction.LEFT) {
            if (game.screen().hasWall(i, j - 1, Direction.RIGHT)) bigSide = 0;
            else if (!game.screen().hasGround(i, j - 1) && game.screen().hasWall(i, j - 2, Direction.RIGHT))
                bigSide /= 2;
            h = smallSide;
            w = bigSide;
            y = game.player().y + game.player().h / 2 - h / 2;
            x = game.player().x - w;
        } else if (game.player().facing == Direction.RIGHT) {
            if (game.screen().hasWall(i, j + 1, Direction.LEFT)) bigSide = 0;
            else if (!game.screen().hasGround(i, j + 1) && game.screen().hasWall(i, j + 2, Direction.LEFT))
                bigSide /= 2;
            h = smallSide;
            w = bigSide;
            y = game.player().y + game.player().h / 2 - h / 2;
            x = game.player().x + game.player().w;
        }
        g.drawImage(img, x, y, w, h, game.getParent());
    }

    public int getLength() {
        return Math.max(w, h) / (game.screenWidth() / Values.columnCount);
    }

}
