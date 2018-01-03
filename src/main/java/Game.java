import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;


public class Game {

    private static boolean gameRunning = true;
    public static Player p = null;
    private static EasyEnemy e1, e2, e3 = null;
    private static MediumEnemy e4, e5, e6 = null;
    private static HardEnemy e7, e8, e9 = null;
    private static Terminal terminal = null;
    private static int level = 1;

    synchronized public static Terminal getTerminal() {
        return terminal;
    }

    public static void main(String[] args) throws InterruptedException {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(140, 60));
        defaultTerminalFactory.setTerminalEmulatorTitle("Smurf 1.0");

        try {
            terminal = defaultTerminalFactory.createTerminal();
            terminal.setCursorVisible(false);

            Map.init();
            if (!Map.drawMap())
                throw new Exception("Kunde inte skapa banan.");

            switch (level) {
                case 1:
                    e1 = new EasyEnemy(level, "e1");
                    e2 = new EasyEnemy(level, "e2");
                    e3 = new EasyEnemy(level, "e3");
//                    e4 = new MediumEnemy(level, "e4");
//                    e5 = new MediumEnemy(level, "e5");
//                    e6 = new MediumEnemy(level, "e6");
//                    e7 = new HardEnemy(level, "e7");
//                    e8 = new HardEnemy(level, "e8");
//                    e9 = new HardEnemy(level, "e9");
                    e1.start(0);
                    e2.start(15000);
                    e3.start(30000);
                    break;
//                case 2:
//                    e1 = new EasyEnemy(terminal, map, mapRowLength, mapRowHeight, mapPaddingX, mapPaddingY, level, "e1");
//                    e3 = new MediumEnemy(terminal, map, mapRowLength, mapRowHeight, mapPaddingX, mapPaddingY, level, "e3");
//                    e5 = new HardEnemy(terminal, map, mapRowLength, mapRowHeight, mapPaddingX, mapPaddingY, level, "e5");
//                    break;
//                case 3:
//                    e1 = new EasyEnemy(terminal, map, mapRowLength, mapRowHeight, mapPaddingX, mapPaddingY, level, "e1");
//                    e2 = new EasyEnemy(terminal, map, mapRowLength, mapRowHeight, mapPaddingX, mapPaddingY, level, "e2");
//                    e3 = new MediumEnemy(terminal, map, mapRowLength, mapRowHeight, mapPaddingX, mapPaddingY, level, "e3");
//                    e4 = new MediumEnemy(terminal, map, mapRowLength, mapRowHeight, mapPaddingX, mapPaddingY, level, "e4");
//                    e5 = new HardEnemy(terminal, map, mapRowLength, mapRowHeight, mapPaddingX, mapPaddingY, level, "e5");
//                    break;
            }

            //SkeletonMap sm = new SkeletonMap(terminal, mapRowLength);
            //sm.start();

            p = new Player(gameRunning);
            p.initPlayer();

            //Inne i denna loopen ska all logik och uppdateringar ske
            while (gameRunning) {
                p.handleInput();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (terminal != null) {
                try {
                    terminal.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void collisionCheck() {

    }
}