package digdug.menus;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends MenuScreen {

    private JApplet parent;

    public MainMenu(JApplet parent) {
        super(parent);
        String[] lines = {"Welcome to DigDug!", "To start, press enter",
                "To save the game, press 's'", "To load a saved game, press 'l'",
                "", "Warning:", "Don't resize the window while playing."};
        setLines(lines);
    }


}
