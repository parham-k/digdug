package digdug.images;

import digdug.values.Direction;

import javax.swing.*;
import java.awt.*;

public class ImageManager {

    private JApplet parent;
    private Image[] rock, fruit;
    private Image[] dragonUp, dragonDown, dragonLeft, dragonRight, dragonKill, dragonGhost;
    private Image dragonFireRight, dragonFireLeft;
    private Image[] redMonsterUp, redMonsterDown, redMonsterLeft, redMonsterRight, redMonsterKill, redMonsterGhost;
    private Image[] digdugAttackUp, digdugAttackDown, digdugAttackLeft, digdugAttackRight;
    private Image[] digdugDie;
    private Image[] digdugMoveUp, digdugMoveDown, digdugMoveLeft, digdugMoveRight;
    private Image[] digdugPumpUp, digdugPumpDown, digdugPumpLeft, digdugPumpRight;
    private Image digdugStandUp, digdugStandDown, digdugStandLeft, digdugStandRight;
    private Image[] ground;
    private Image pumpUp, pumpDown, pumpLeft, pumpRight;

    public ImageManager(JApplet parent) {
        this.parent = parent;
        initRock();
        initFruit();
        initDragon();
        initGround();
        initRedMonster();
        initDigDug();
        initPump();
    }

    public void initRock() {
        rock = new Image[4];
        for (int i = 0; i < 4; i++)
            rock[i] = getImage("rock" + i);
    }

    public void initFruit() {
        fruit = new Image[5];
        for (int i = 0; i < 5; i++)
            fruit[i] = getImage("fruit" + i);
    }

    public void initDragon() {
        dragonUp = new Image[2];
        dragonUp[0] = getImage("dragonUp0");
        dragonUp[1] = getImage("dragonUp1");
        dragonDown = new Image[2];
        dragonDown[0] = getImage("dragonDown0");
        dragonDown[1] = getImage("dragonDown1");
        dragonLeft = new Image[2];
        dragonLeft[0] = getImage("dragonLeft0");
        dragonLeft[1] = getImage("dragonLeft1");
        dragonRight = new Image[2];
        dragonRight[0] = getImage("dragonRight0");
        dragonRight[1] = getImage("dragonRight1");
        dragonFireRight = getImage("dragonFireRight");
        dragonFireLeft = getImage("dragonFireLeft");
        dragonKill = new Image[4];
        dragonKill[0] = getImage("dragonKill0");
        dragonKill[1] = getImage("dragonKill1");
        dragonKill[2] = getImage("dragonKill2");
        dragonKill[3] = getImage("dragonKill3");
        dragonGhost = new Image[2];
        dragonGhost[0] = getImage("dragonGhost0");
        dragonGhost[1] = getImage("dragonGhost1");
    }

    public void initGround() {
        ground = new Image[4];
        for (int i = 0; i < 4; i++)
            ground[i] = getImage("ground" + i);
    }

    public void initRedMonster() {
        redMonsterUp = new Image[2];
        redMonsterUp[0] = getImage("redMonsterUp0");
        redMonsterUp[1] = getImage("redMonsterUp1");
        redMonsterDown = new Image[2];
        redMonsterDown[0] = getImage("redMonsterDown0");
        redMonsterDown[1] = getImage("redMonsterDown1");
        redMonsterLeft = new Image[2];
        redMonsterLeft[0] = getImage("redMonsterLeft0");
        redMonsterLeft[1] = getImage("redMonsterLeft1");
        redMonsterRight = new Image[2];
        redMonsterRight[0] = getImage("redMonsterRight0");
        redMonsterRight[1] = getImage("redMonsterRight1");
        redMonsterKill = new Image[4];
        redMonsterKill[0] = getImage("redMonsterKill0");
        redMonsterKill[1] = getImage("redMonsterKill1");
        redMonsterKill[2] = getImage("redMonsterKill2");
        redMonsterKill[3] = getImage("redMonsterKill3");
        redMonsterGhost = new Image[2];
        redMonsterGhost[0] = getImage("redMonsterGhost0");
        redMonsterGhost[1] = getImage("redMonsterGhost1");
    }

    public void initDigDug() {
        digdugAttackUp = new Image[2];
        digdugAttackUp[0] = getImage("digdugAttackUp0");
        digdugAttackUp[1] = getImage("digdugAttackUp1");
        digdugAttackDown = new Image[2];
        digdugAttackDown[0] = getImage("digdugAttackDown0");
        digdugAttackDown[1] = getImage("digdugAttackDown1");
        digdugAttackLeft = new Image[2];
        digdugAttackLeft[0] = getImage("digdugAttackLeft0");
        digdugAttackLeft[1] = getImage("digdugAttackLeft1");
        digdugAttackRight = new Image[2];
        digdugAttackRight[0] = getImage("digdugAttackRight0");
        digdugAttackRight[1] = getImage("digdugAttackRight1");

        digdugMoveUp = new Image[2];
        digdugMoveUp[0] = getImage("digdugMoveUp0");
        digdugMoveUp[1] = getImage("digdugMoveUp1");
        digdugMoveDown = new Image[2];
        digdugMoveDown[0] = getImage("digdugMoveDown0");
        digdugMoveDown[1] = getImage("digdugMoveDown1");
        digdugMoveLeft = new Image[2];
        digdugMoveLeft[0] = getImage("digdugMoveLeft0");
        digdugMoveLeft[1] = getImage("digdugMoveLeft1");
        digdugMoveRight = new Image[2];
        digdugMoveRight[0] = getImage("digdugMoveRight0");
        digdugMoveRight[1] = getImage("digdugMoveRight1");

        digdugPumpUp = new Image[2];
        digdugPumpUp[0] = getImage("digdugPumpUp0");
        digdugPumpUp[1] = getImage("digdugPumpUp1");
        digdugPumpDown = new Image[2];
        digdugPumpDown[0] = getImage("digdugPumpDown0");
        digdugPumpDown[1] = getImage("digdugPumpDown1");
        digdugPumpLeft = new Image[2];
        digdugPumpLeft[0] = getImage("digdugPumpLeft0");
        digdugPumpLeft[1] = getImage("digdugPumpLeft1");
        digdugPumpRight = new Image[2];
        digdugPumpRight[0] = getImage("digdugPumpRight0");
        digdugPumpRight[1] = getImage("digdugPumpRight1");

        digdugDie = new Image[3];
        for (int i = 0; i < 3; i++)
            digdugDie[i] = getImage("digdugDie" + i);

        digdugStandUp = getImage("digdugStandUp");
        digdugStandDown = getImage("digdugStandDown");
        digdugStandLeft = getImage("digdugStandLeft");
        digdugStandRight = getImage("digdugStandRight");
    }

    public void initPump() {
        pumpUp = getImage("pumpUp");
        pumpDown = getImage("pumpDown");
        pumpLeft = getImage("pumpLeft");
        pumpRight = getImage("pumpRight");
    }

    public Image getImage(String name) {
        return parent.getImage(getClass().getResource(name + ".png"));
    }

    public Image[] getRock() {
        return rock;
    }

    public Image getFruit(int n) {
        return fruit[n];
    }

    public Image[] getDragonUp() {
        return dragonUp;
    }

    public Image[] getDragonDown() {
        return dragonDown;
    }

    public Image[] getDragonLeft() {
        return dragonLeft;
    }

    public Image[] getDragonRight() {
        return dragonRight;
    }

    public Image getDragonFire(Direction dir) {
        if (dir == Direction.RIGHT)
            return dragonFireRight;
        if (dir == Direction.LEFT)
            return dragonFireLeft;
        return null;
    }

    public Image[] getDragonKill() {
        return dragonKill;
    }

    public Image[] getDragonGhost() {
        return dragonGhost;
    }

    public Image[] getRedMonsterGhost() {
        return redMonsterGhost;
    }

    public Image[] getRedMonsterUp() {
        return redMonsterUp;
    }

    public Image[] getRedMonsterDown() {
        return redMonsterDown;
    }

    public Image[] getRedMonsterLeft() {
        return redMonsterLeft;
    }

    public Image[] getRedMonsterRight() {
        return redMonsterRight;
    }

    public Image[] getRedMonsterKill() {
        return redMonsterKill;
    }

    public Image[] getGround() {
        return ground;
    }

    public Image[] getDigdugAttackUp() {
        return digdugAttackUp;
    }

    public Image[] getDigdugAttackDown() {
        return digdugAttackDown;
    }

    public Image[] getDigdugAttackLeft() {
        return digdugAttackLeft;
    }

    public Image[] getDigdugAttackRight() {
        return digdugAttackRight;
    }

    public Image[] getDigdugDie() {
        return digdugDie;
    }

    public Image[] getDigdugMoveUp() {
        return digdugMoveUp;
    }

    public Image[] getDigdugMoveDown() {
        return digdugMoveDown;
    }

    public Image[] getDigdugMoveLeft() {
        return digdugMoveLeft;
    }

    public Image[] getDigdugMoveRight() {
        return digdugMoveRight;
    }

    public Image[] getDigdugPumpUp() {
        return digdugPumpUp;
    }

    public Image[] getDigdugPumpDown() {
        return digdugPumpDown;
    }

    public Image[] getDigdugPumpLeft() {
        return digdugPumpLeft;
    }

    public Image[] getDigdugPumpRight() {
        return digdugPumpRight;
    }

    public Image getDigdugStandUp() {
        return digdugStandUp;
    }

    public Image getDigdugStandDown() {
        return digdugStandDown;
    }

    public Image getDigdugStandLeft() {
        return digdugStandLeft;
    }

    public Image getDigdugStandRight() {
        return digdugStandRight;
    }

    public Image getPump(Direction dir) {
        if (dir == Direction.UP) return pumpUp;
        if (dir == Direction.DOWN) return pumpDown;
        if (dir == Direction.LEFT) return pumpLeft;
        if (dir == Direction.RIGHT) return pumpRight;
        return pumpRight;
    }
}
