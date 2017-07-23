package digdug.menus;

import digdug.HighscoreManager;

import javax.swing.*;

public class GameOver extends MenuScreen {

    public GameOver(JApplet parent, int score) {
        super(parent);
        String[] lines = {"Game Over!", "Your score:", score + "", "High score:", (new HighscoreManager()).getHighscore() + ""};
        setLines(lines);
    }

}
