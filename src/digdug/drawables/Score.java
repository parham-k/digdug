package digdug.drawables;

import digdug.GameManager;
import digdug.values.Values;

import java.awt.*;

public class Score extends ScreenObject implements Runnable {

    private int x, y, score;

    public Score(GameManager game, int x, int y, int score) {
        super(game, null, 0, 0);
        this.x = x;
        this.y = y;
        this.score = score;
    }

    public void draw(Graphics g) {
        String s = Integer.toString(score);
        int w = g.getFontMetrics().stringWidth(s), h = (game.screenHeight() / Values.rowCount) / 2;
        g.setFont(new Font("Consolas", Font.BOLD, h));
        g.setColor(Color.WHITE);
        g.drawString(s, x + 5, y + 2 * h / 3);
    }

    public void run() {
        try {
            Thread.currentThread().sleep(Values.showScoreAddedTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        game.screen().removeObject(this);
    }

}
