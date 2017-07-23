package digdug;

import digdug.drawables.DigDug;

import java.io.*;

public class HighscoreManager {

    public int getHighscore() {
        int score = 0;
        File f = new File(getClass().getResource("") + "highscore.txt");
        if (f.exists()) {
            FileReader input = null;
            try {
                input = new FileReader(f);
                score = input.read();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (input != null)
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
        return score;
    }

    public void saveHighscore(int score) {
        File f = new File("highscore.txt");
        if(!f.exists())
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        FileWriter output = null;
        try {
            output = new FileWriter(f);
            output.write(score);
            output.flush();
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

}
