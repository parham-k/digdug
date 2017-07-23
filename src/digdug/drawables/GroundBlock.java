package digdug.drawables;

import digdug.GameManager;
import digdug.values.Direction;
import digdug.values.Values;

import java.awt.*;

public class GroundBlock extends ScreenObject {

    public boolean hasGround, hasRock, hasUp, hasDown, hasLeft, hasRight;

    public GroundBlock(GameManager game, int i, int j) {
        super(game, game.getImages().getGround()[i / (Values.rowCount / 4)], i, j);
        hasGround = true;
        hasUp = true;
        hasDown = true;
        hasLeft = true;
        hasRight = true;
    }

    public void draw(Graphics g) {
        if (hasGround) {
            w = game.screenWidth() / Values.columnCount;
            h = game.screenHeight() / Values.rowCount;
            x = j * w;
            y = i * h;
            g.drawImage(img, x, y, w, h, game.getParent());
        } else {
            int sx1, sx2, sy1, sy2;
            if (hasLeft) {
                sx1 = 0;
                sy1 = 0;
                sx2 = sx1 + img.getWidth(game.getParent()) / 5;
                sy2 = img.getHeight(game.getParent());
                g.drawImage(img, x, y, x + w / 5, y + h, sx1, sy1, sx2, sy2, game.getParent());
            }
            if (hasRight) {
                sx1 = 4 * img.getWidth(game.getParent()) / 5;
                sy1 = 0;
                sx2 = sx1 + img.getWidth(game.getParent()) / 5;
                sy2 = img.getHeight(game.getParent());
                g.drawImage(img, x + 4 * w / 5, y, x + w, y + h, sx1, sy1, sx2, sy2, game.getParent());
            }
            if (hasUp) {
                sx1 = 0;
                sy1 = 0;
                sx2 = img.getWidth(game.getParent());
                sy2 = img.getHeight(game.getParent()) / 5;
                g.drawImage(img, x, y, x + w, y + h / 5, sx1, sy1, sx2, sy2, game.getParent());
            }
            if (hasDown) {
                sx1 = 0;
                sy1 = 4 * img.getHeight(game.getParent()) / 5;
                sx2 = img.getWidth(game.getParent());
                sy2 = img.getHeight(game.getParent());
                g.drawImage(img, x, y + 4 * h / 5, x + w, y + h, sx1, sy1, sx2, sy2, game.getParent());
            }
        }
    }

    public void dig(Direction side) {
        hasGround = false;
        if (side == Direction.DOWN) hasUp = false;
        if (side == Direction.UP) hasDown = false;
        if (side == Direction.RIGHT) hasLeft = false;
        if (side == Direction.LEFT) hasRight = false;
    }

    public boolean hasSide(Direction dir) {
        if (dir == Direction.UP && hasUp) return true;
        if (dir == Direction.DOWN && hasDown) return true;
        if (dir == Direction.LEFT && hasLeft) return true;
        if (dir == Direction.RIGHT && hasRight) return true;
        return false;
    }

}
