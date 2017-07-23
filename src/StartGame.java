import digdug.GameManager;
import digdug.menus.GameOver;
import digdug.menus.MainMenu;
import digdug.values.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StartGame extends JApplet implements KeyListener {

    private GameManager game;
    private MainMenu firstScreen;

    public static void main(String[] args) {
        StartGame game = new StartGame();
        JFrame frame = new JFrame("Digdug by Parham :)");
        frame.add(game);
        frame.pack();
        frame.setVisible(true);
        frame.addKeyListener(game);
        game.init();
    }

    public void init() {
        setSize(1000, 600);
        firstScreen = new MainMenu(this);
        firstScreen.isWaiting = true;
        setFocusable(true);
        addKeyListener(this);
    }

    public void paint(Graphics g) {
        if (firstScreen.isWaiting)
            firstScreen.draw(g);
        else if (!game.isRunning && game.getInfo().getLives() <= 0) {
            try {
                Thread.currentThread().sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GameOver gameOverScreen = new GameOver(this, game.getInfo().getScore());
            game = null;
            gameOverScreen.isWaiting = true;
            gameOverScreen.draw(g);
        } else
            game.screen().paintScreen(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && firstScreen.isWaiting) {
            firstScreen.isWaiting = false;
            game = new GameManager(this);
            game.start();
        } else if (game != null && game.isRunning && game.player().movingDirection == Direction.NONE) {
            if (e.getKeyCode() == KeyEvent.VK_UP) game.player().movingDirection = Direction.UP;
            else if (e.getKeyCode() == KeyEvent.VK_DOWN) game.player().movingDirection = Direction.DOWN;
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT) game.player().movingDirection = Direction.RIGHT;
            else if (e.getKeyCode() == KeyEvent.VK_LEFT) game.player().movingDirection = Direction.LEFT;
            else if (e.getKeyCode() == KeyEvent.VK_SPACE) game.player().attack();
        }
        if (game != null && e.getKeyCode() == KeyEvent.VK_S)
            game.saveGame();
        if (game != null && e.getKeyCode() == KeyEvent.VK_L)
            game.loadGame();
        if (game != null && e.getKeyCode() == KeyEvent.VK_F)
            game.screen().throwRocks();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_UP ||
                e.getKeyCode() == KeyEvent.VK_DOWN ||
                e.getKeyCode() == KeyEvent.VK_RIGHT ||
                e.getKeyCode() == KeyEvent.VK_LEFT) && game != null && game.isRunning)
            game.player().movingDirection = Direction.NONE;
        else if (game != null && game.isRunning && e.getKeyCode() == KeyEvent.VK_SPACE)
            game.player().stopAttack(true);
    }
}
