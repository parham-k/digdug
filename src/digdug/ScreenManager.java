package digdug;

import digdug.drawables.*;
import digdug.drawables.monsters.Dragon;
import digdug.drawables.monsters.DragonFire;
import digdug.drawables.monsters.Monster;
import digdug.drawables.monsters.RedMonster;
import digdug.values.Direction;
import digdug.values.Values;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class ScreenManager implements Serializable {

    private GroundBlock[][] ground;
    private ArrayList<ScreenObject> objects;
    private GameManager game;
    private int rocksRemoved;

    public ScreenManager(GameManager game) {
        this.game = game;
        objects = new ArrayList<>();
    }

    public void startLevel(int lvl) {
        ground = new GroundBlock[Values.rowCount][Values.columnCount];

        //stop all running threads
        for (int i = 0; i < objects.size(); i++)
            objects.get(i).stopRunning();

        objects.clear();

        //create ground
        for (int j = 0; j < Values.columnCount; j++) {
            ground[0][j] = new GroundBlock(game, 0, j);
            ground[0][j].dig(Direction.UP);
            ground[0][j].dig(Direction.DOWN);
            ground[0][j].dig(Direction.LEFT);
            ground[0][j].dig(Direction.RIGHT);
        }
        for (int i = 1; i < Values.rowCount; i++)
            for (int j = 0; j < Values.columnCount; j++)
                ground[i][j] = new GroundBlock(game, i, j);

        int a, b;
        game.getInfo().setMonsters(2 * lvl + 2);

        //red monster generation
        for (int i = 0; i < 2 + lvl; i++) {
            a = (int) (Math.random() * (Values.rowCount - 3) + 2);
            b = (int) (Math.random() * (Values.columnCount - 3) + 2);
            boolean clearVertical = new Random().nextBoolean();
            if (clearVertical) {
                ground[a][b].dig(Direction.UP);
                ground[a][b].dig(Direction.DOWN);
                ground[a - 1][b].dig(Direction.UP);
                ground[a + 1][b].dig(Direction.DOWN);
            } else {
                ground[a][b].dig(Direction.LEFT);
                ground[a][b].dig(Direction.RIGHT);
                ground[a][b - 1].dig(Direction.LEFT);
                ground[a][b + 1].dig(Direction.RIGHT);
            }
            RedMonster r = new RedMonster(game, a, b);
            Thread t = new Thread(r);
            t.start();
            objects.add(r);
        }

        //dragon generation
        for (int i = 0; i < lvl; i++) {
            a = (int) (Math.random() * (Values.rowCount - 3) + 2);
            b = (int) (Math.random() * (Values.columnCount - 3) + 2);
            boolean clearVertical = new Random().nextBoolean();
            if (clearVertical) {
                ground[a][b].dig(Direction.UP);
                ground[a][b].dig(Direction.DOWN);
                ground[a - 1][b].dig(Direction.UP);
                ground[a + 1][b].dig(Direction.DOWN);
            } else {
                ground[a][b].dig(Direction.LEFT);
                ground[a][b].dig(Direction.RIGHT);
                ground[a][b - 1].dig(Direction.LEFT);
                ground[a][b + 1].dig(Direction.RIGHT);
            }
            Dragon d = new Dragon(game, a, b);
            Thread t = new Thread(d);
            t.start();
            objects.add(d);
        }

        //rock generation
        rocksRemoved = 0;
        for (int i = 0; i < lvl; i++) {
            do {
                a = (int) (Math.random() * (Values.rowCount - 2) + 1);
                b = (int) (Math.random() * Values.columnCount);
            } while (!hasGround(a, b));
            Rock r = new Rock(game, a, b);
            objects.add(r);
            ground[a][b].hasRock = true;
        }
    }

    public void addObject(ScreenObject obj) {
        objects.add(obj);
    }

    public void removeObject(ScreenObject obj) {
        objects.remove(obj);
    }

    public boolean hasGround(int i, int j) {
        return i >= 0 && i < Values.rowCount && j >= 0 && j < Values.columnCount && ground != null && ground[i][j].hasGround;
    }

    public void removeGround(int i, int j, Direction dir) {
        ground[i][j].dig(dir);
    }

    public boolean hasWall(int i, int j, Direction dir) {
        return ground != null && ground[i][j].hasSide(dir);
    }

    public boolean hasRock(int i, int j) {
        return ground != null && ground[i][j].hasRock;
    }

    public void removeRock(int i, int j) {
        ground[i][j].hasRock = false;
        rocksRemoved++;
        if (rocksRemoved >= 2)
            generateFruit();
    }

    public void paintScreen(Graphics g) {
        Image offImg = game.getParent().createImage(game.getParent().getWidth(), game.getParent().getHeight());
        Graphics offG = offImg.getGraphics();
        offG.setColor(Color.BLACK);
        offG.fillRect(0, 0, game.getParent().getWidth(), game.getParent().getHeight());

        game.getInfo().draw(offG);

        for (int i = 0; i < Values.rowCount; i++)
            for (int j = 0; j < Values.columnCount; j++)
                if (ground != null && ground[i][j] != null)
                    ground[i][j].draw(offG);

        for (int i = 0; i < objects.size(); i++)
            objects.get(i).draw(offG);

        game.player().draw(offG);

        g.drawImage(offImg, 0, 0, game.getParent());
    }

    public void checkConflict() {
        ScreenObject o, o1, o2;

        //monster hitting player or player collecting fruit
        for (int i = 0; i < objects.size(); i++) {
            o = objects.get(i);
            if ((o instanceof DragonFire || (o instanceof Monster && !((Monster) o).isExploding)) && collided(game.player(), o)
                    && o.i == game.player().i && o.j == game.player().j && game.player().isAlive) {
                game.player().die();
                if (o instanceof DragonFire) {
                    ((DragonFire) o).hit();
                }
            } else if (o instanceof Fruit && collided(game.player(), o)) {
                game.getSounds().extraLife();
                game.getInfo().addScore(o.x + o.w, o.y, ((Fruit) o).getScore());
                game.getInfo().addLife();
                removeObject(o);
            }
        }

        //drop rocks
        for (int i = 0; i < objects.size(); i++) {
            o = objects.get(i);
            if (o instanceof Rock && !((Rock) o).isFalling && !((Rock)o).isFreeFalling && !hasGround(o.i + 1, o.j) && !(game.player().i == o.i + 1 && game.player().j == o.j)) {
                (new Thread((Rock) o)).start();
                ((Rock) o).isFalling = true;
            }
        }

        //rock hitting monster or rock hitting player or pump hitting monster
        for (int i = 0; i < objects.size(); i++) {
            o1 = objects.get(i);
            for (int j = 0; j < objects.size(); j++) {
                o2 = objects.get(j);
                if (o1 instanceof Rock && ((Rock) o1).isFalling && o1.i <= o2.i && o1.j == o2.j) {
                    o2 = objects.get(j);
                    if (o2 instanceof Monster && collided(o1, o2)) {
                        ((Monster) o2).die();
                        ((Rock) o1).kill();
                    }
                    if (collided(o1, game.player()) && game.player().i >= o1.i && game.player().j == o1.j)
                        game.player().die();
                } else if (o1 instanceof Pump && o2 instanceof Monster && !((Monster) o2).isGhost && pumpCollided(o2)) {
                    game.getSounds().enemyHit();
                    ((Monster) o2).isExploding = true;
                    ((Monster) o2).isMoving = false;
                    game.player().isPumping = true;
                    ((Pump) o1).isGrowing = false;
                }
            }
        }
    }

    public boolean collided(ScreenObject o1, ScreenObject o2) {
        return (o1.x >= o2.x && o1.x <= o2.x + o2.w
                && o1.y >= o2.y && o1.y <= o2.y + o2.h)
                || (o2.x >= o1.x && o2.x <= o1.x + o1.w
                && o2.y >= o1.y && o2.y <= o1.y + o1.h);
    }

    public boolean pumpCollided(ScreenObject o) {
        int l = game.player().getPumpLength() + 1;
        return game.player().getPumpLength() > 0 && ((game.player().facing == Direction.UP && game.player().j == o.j && game.player().i > o.i && game.player().i - o.i <= l)
                || (game.player().facing == Direction.DOWN && game.player().j == o.j && game.player().i < o.i && o.i - game.player().i <= l)
                || (game.player().facing == Direction.LEFT && game.player().i == o.i && game.player().j > o.j && game.player().j - o.j <= l)
                || (game.player().facing == Direction.RIGHT && game.player().i == o.i && game.player().j < o.j && o.j - game.player().j <= l));
    }

    public void generateFruit() {
        int a, b;
        do {
            a = (int) (Math.random() * (Values.rowCount - 2) + 1);
            b = (int) (Math.random() * Values.columnCount);
        } while (hasGround(a, b));
        Fruit f = new Fruit(game, (int) (Math.random() * Values.fruitTypes), a, b);
        addObject(f);
        (new Thread(f)).start();
    }

    public void findLastMonster() {
        if (game.getInfo().getMonstersLeft() == 1) {
            ScreenObject o;
            for (int i = 0; i < objects.size(); i++) {
                o = objects.get(i);
                if (o instanceof Monster)
                    ((Monster) o).isLastMonster = true;
            }
        }
    }

    public void throwRocks() {
        ScreenObject o;
        for (int i = 0; i < objects.size(); i++) {
            o = objects.get(i);
            if (o instanceof Rock) {
                ((Rock) o).isFreeFalling = true;
                (new Thread((Rock)o)).start();
            }
        }
        for (int i = 1; i < Values.rowCount; i++) {
            for (int j = 0; j < Values.columnCount; j++) {
                if (game.player().i != i || game.player().j != j)
                    ground[i][j] = new GroundBlock(game, i, j);
            }
        }
    }

}
