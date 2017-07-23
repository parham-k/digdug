package digdug.sounds;

import sun.applet.AppletAudioClip;

public class SoundManager {

    private AppletAudioClip enemyHit, moving, enemyPop, extraLife, dragonFire, lifeLost, pumpFire, pumpClose, rock;

    public SoundManager() {
        enemyHit = new AppletAudioClip(getClass().getResource("enemy_hit.wav"));
        moving = new AppletAudioClip(getClass().getResource("enemy_moving.wav"));
        enemyPop = new AppletAudioClip(getClass().getResource("enemy_pop.wav"));
        extraLife = new AppletAudioClip(getClass().getResource("extra_life.wav"));
        dragonFire = new AppletAudioClip(getClass().getResource("fireball.wav"));
        lifeLost = new AppletAudioClip(getClass().getResource("life_lost.wav"));
        pumpClose = new AppletAudioClip(getClass().getResource("pump.wav"));
        pumpFire = new AppletAudioClip(getClass().getResource("pump_fire.wav"));
        rock = new AppletAudioClip(getClass().getResource("rock.wav"));
    }

    private void playSound(AppletAudioClip sound) {
        Thread t = new Thread() {
            public void run() {
                try {
                    sound.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    public void enemyHit() {
        playSound(enemyHit);
    }

    public void moving() {
        try {
            moving.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopMoving(){
        moving.stop();
    }

    public void enemyPop() {
        playSound(enemyPop);
    }

    public void extraLife() {
        playSound(extraLife);
    }

    public void dragonFire() {
        playSound(dragonFire);
    }

    public void lifeLost() {
        playSound(lifeLost);
    }

    public void pumpFire() {
        playSound(pumpFire);
    }

    public void pumpClose() {
        playSound(pumpClose);
    }

    public void rock() {
        playSound(rock);
    }

}
