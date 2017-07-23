package digdug.drawables.monsters;

import digdug.GameManager;
import digdug.values.Direction;

public class Dragon extends Monster {

    private Direction dir;

    public Dragon(GameManager game, int i, int j) {
        super(game, game.getImages().getDragonRight()[0], game.getImages().getDragonKill(), i, j);
        setMoveAnimations(game.getImages().getDragonUp(), game.getImages().getDragonDown(),
                game.getImages().getDragonLeft(), game.getImages().getDragonRight());
        setGhostAnimation(game.getImages().getDragonGhost());
        setDieAnimation(game.getImages().getDragonKill());
        dir = Direction.RIGHT;
    }

    public void run() {
        boolean canContinueMovement = false;
        while (health > 0 && isRunning) {
            if (!isExploding) {
                health = 3;
                if ((dir == Direction.LEFT || dir == Direction.RIGHT) && (int) (Math.random() * 20) == 10) {
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    game.getSounds().dragonFire();
                    DragonFire fire = new DragonFire(game, dir, i, j);
                    game.screen().addObject(fire);
                    Thread t = new Thread(fire);
                    t.start();
                } else {
                    if (dir == Direction.UP) canContinueMovement = canMoveUp();
                    if (dir == Direction.DOWN) canContinueMovement = canMoveDown();
                    if (dir == Direction.LEFT) canContinueMovement = canMoveLeft();
                    if (dir == Direction.RIGHT) canContinueMovement = canMoveRight();
                    if (canContinueMovement) {
                        stopAnimation();
                        if (dir == Direction.UP) up();
                        if (dir == Direction.DOWN) down();
                        if (dir == Direction.LEFT) left();
                        if (dir == Direction.RIGHT) right();
                    } else if ((int) (Math.random() * 20) == 10)
                        ghostMove();
                    else {
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
                }
            } else if (!isExploding && isLastMonster)
                escape();
            else {
                while (isExploding && game.player().isPumping && health > 0) {
                    health--;
                    try {
                        Thread.currentThread().sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!game.player().isPumping)
                isExploding = false;
        }
        if (isExploding || health <= 0)
            die();
    }
}