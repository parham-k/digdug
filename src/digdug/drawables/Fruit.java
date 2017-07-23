package digdug.drawables;

import digdug.GameManager;

public class Fruit extends ScreenObject implements Runnable {

    private int score;
    private long generationTime;

    public Fruit(GameManager game, int fruitN, int i, int j) {
        super(game, game.getImages().getFruit(fruitN), i, j);
        score = (fruitN + 1) * 250;
        generationTime = System.currentTimeMillis();
    }

    public void run() {
        while (isRunning && System.currentTimeMillis() - generationTime < 10000) {
        }
        game.screen().removeObject(this);
    }

    public int getScore() {
        return score;
    }

}
