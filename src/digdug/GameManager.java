package digdug;

import digdug.drawables.DigDug;
import digdug.drawables.Status;
import digdug.images.ImageManager;
import digdug.sounds.SoundManager;
import digdug.values.Values;

import javax.swing.*;
import java.io.*;

public class GameManager extends Thread {

    private JApplet parent;
    private ScreenManager screen;
    private ImageManager images;
    private SoundManager sounds;
    private DigDug player;
    public boolean isRunning;
    private Status info;

    public GameManager(JApplet parent) {
        this.parent = parent;
        images = new ImageManager(parent);
        sounds = new SoundManager();
        screen = new ScreenManager(this);
        info = new Status(this);
        info.setHighScore((new HighscoreManager()).getHighscore());
        startLevel(1);
        isRunning = true;
    }

    public int screenWidth() {
        return parent.getHeight();
    }

    public int screenHeight() {
        return parent.getHeight();
    }

    public JApplet getParent() {
        return parent;
    }

    public ScreenManager screen() {
        return screen;
    }

    public ImageManager getImages() {
        return images;
    }

    public SoundManager getSounds() {
        return sounds;
    }

    public Status getInfo() {
        return info;
    }

    public DigDug player() {
        return player;
    }

    public void startLevel(int lvl) {
        isRunning = false;
        if (player!=null)
        player.isRunning = false;
        player = new DigDug(this, 0, Values.columnCount / 2);
        new Thread(player).start();
        screen.startLevel(lvl);
        info.setLevel(lvl);
        try {
            Thread.currentThread().sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.gc();
        isRunning = true;
    }

    public void run() {
        while (true) {
            if (isRunning) {
                parent.repaint();
                screen().checkConflict();
            } else if (!isRunning && info.getLives() <= 0) {
                gameOver();
                break;
            }
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void gameOver() {
        if (info.getScore() > info.getHighScore())
            (new HighscoreManager()).saveHighscore(info.getScore());
    }

    //// TODO: 2016-06-20 save and load

    public void saveGame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (fileChooser.showSaveDialog(parent) == JFileChooser.CANCEL_OPTION)
            return;

        ObjectOutputStream output = null;
        File file = fileChooser.getSelectedFile();
        try {
            output = new ObjectOutputStream(new FileOutputStream(file));
            output.writeObject(player);
            output.writeObject(info);
            output.writeObject(screen);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null)
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public void loadGame() {
        isRunning = false;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (fileChooser.showOpenDialog(parent) == JFileChooser.CANCEL_OPTION) {
            isRunning = true;
            return;
        }

        ObjectInputStream input = null;
        File file = fileChooser.getSelectedFile();
        try {
            input = new ObjectInputStream(new FileInputStream(file));
            player = (DigDug) input.readObject();
            info = (Status) input.readObject();
            screen = (ScreenManager) input.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (input != null)
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            isRunning = true;
        }
    }

}
