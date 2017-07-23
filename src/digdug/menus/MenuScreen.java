package digdug.menus;

import digdug.values.Values;

import javax.swing.*;
import java.awt.*;

public abstract class MenuScreen {

    JApplet parent;
    public boolean isWaiting;
    private String[] lines;

    public MenuScreen(JApplet parent) {
        this.parent = parent;
        isWaiting = true;
    }

    public void setLines(String[] lines) {
        this.lines = lines;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, parent.getWidth(), parent.getHeight());

        int x, w, h = parent.getHeight() / Values.rowCount, y = h;
        g.setFont(new Font("Consolas", Font.BOLD, h));
        g.setColor(Color.GRAY);
        for (int i = 0; i < lines.length; i++) {
            w = g.getFontMetrics().stringWidth(lines[i]);
            x = parent.getWidth() / 2 - w / 2;
            y += 3 * h / 2;
            g.drawString(lines[i], x, y);
        }
    }

}
