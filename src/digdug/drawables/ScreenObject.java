package digdug.drawables;

import digdug.GameManager;
import digdug.values.Values;

import java.awt.*;
import java.io.Serializable;

public abstract class ScreenObject implements Serializable {

    protected GameManager game;
    public int i, j;
    public int x, y, w, h;
    protected Image img;
    public boolean isMoving, isRunning, animationPlaying;
    private Thread animation;
    private Image[] currentAnimation;

    public ScreenObject(GameManager game, Image img, int i, int j) {
        this.game = game;
        this.i = i;
        this.j = j;
        this.img = img;
        w = game.screenWidth() / Values.columnCount;
        h = game.screenHeight() / Values.rowCount;
        x = j * w;
        y = i * h;
        isRunning = true;
    }

    public void draw(Graphics g) {
        w = game.screenWidth() / Values.columnCount;
        h = game.screenHeight() / Values.rowCount;
        if (!isMoving) {
            x = j * w;
            y = i * h;
        }
        g.drawImage(img, x, y, w, h, game.getParent());
    }

    public void setAnimation(Image[] images, int sleepTime, boolean loop) {
        stopAnimation();
        currentAnimation = images;
        animation = new Thread(new Runnable() {
            @Override
            public void run() {
                int frame = 0;
                do {
                    img = currentAnimation[frame++ % images.length];
                    try {
                        Thread.currentThread().sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (loop && animationPlaying);
            }
        });
        animationPlaying = true;
        animation.start();
    }

    public void stopAnimation() {
        animationPlaying = false;
        animation = null;
    }

    public void stopRunning(){
        isRunning = false;
    }

}
