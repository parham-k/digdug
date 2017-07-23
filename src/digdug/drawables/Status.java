package digdug.drawables;

import digdug.GameManager;
import digdug.values.Values;

import java.awt.*;

public class Status {

    private GameManager game;
    private int score, lives, highScore, level, monstersLeft;

    public Status(GameManager game) {
        this.game = game;
        lives = 3;
    }

    public void draw(Graphics g) {
        int x = game.screenWidth();
        int w = game.getParent().getWidth() - game.screenWidth();
        String s;
        int y = game.screenHeight() / Values.rowCount;
        g.setColor(Color.GRAY);
        g.fillRect(x, 0, w, game.screenHeight());

        int h = game.screenHeight() / Values.rowCount;
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.BOLD, h));

        g.drawString("Score: " + score, x + 10, y);
        y += h;
        g.drawString("Level: " + level, x + 10, y);
        y += h;
        g.drawString("Lives left: " + lives, x + 10, y);
        y += h;
        g.drawString("Monsters left: " + monstersLeft, x + 10, y);
        y += h;
        g.drawString("High score: " + highScore, x + 10, y);
    }

    public void addScore(int n) {
        score += n;
    }

    public void addScore(int x, int y, int n) {
        Score s = new Score(game, x, y, n);
        game.screen().addObject(s);
        (new Thread(s)).start();
        score += n;
    }

    public int getScore() {
        return score;
    }

    public void addLife() {
        lives++;
    }

    public void loseLife() {
        lives--;
        if (lives <= 0) {
            game.getParent().repaint();
            game.isRunning = false;
        }
    }

    public void setLevel(int lvl) {
        level = lvl;
    }

    public int getLevel() {
        return level;
    }

    public void loseMonster() {
        monstersLeft--;
        if (monstersLeft == 1)
            game.screen().findLastMonster();
        else if (monstersLeft == 0) {
            setMonsters(2 * (level + 1) + 2);
            game.startLevel(level + 1);
        }
    }

    public void setMonsters(int n) {
        monstersLeft = n;
    }

    public int getLives() {
        return lives;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getMonstersLeft() {
        return monstersLeft;
    }
}
