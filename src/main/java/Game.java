import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;


public class Game {

    private static boolean gameRunning = true;
    public static Player p = null;
    private static EasyEnemy e1, e2, e3 = null;
    private static MediumEnemy e4, e5, e6 = null;
    private static HardEnemy e7, e8, e9 = null;
    private static Screen screen = null;
    private static int level = 1;

    public static Screen getScreen() {
        return screen;
    }

    public static void main(String[] args) throws InterruptedException {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(140, 60));
        defaultTerminalFactory.setTerminalEmulatorTitle("PacMan 1.0");

        try {
            Terminal terminal = defaultTerminalFactory.createTerminal();
            //terminal.setCursorVisible(false);
            screen = new TerminalScreen(terminal);
            screen.startScreen();
            screen.setCursorPosition(null);

            Map.init(true);

            p = new Player(gameRunning);
            p.initPlayer();

            switch (level) {
                case 1:
                    e1 = new EasyEnemy(level, "e1");
                    e2 = new EasyEnemy(level, "e2");
                    e3 = new EasyEnemy(level, "e3");
                    e4 = new MediumEnemy(level, "e4");
                    e5 = new MediumEnemy(level, "e5");
                    e6 = new MediumEnemy(level, "e6");
                    e7 = new HardEnemy(level, "e7");
                    e8 = new HardEnemy(level, "e8");
                    e9 = new HardEnemy(level, "e9");
                    e1.start(0);
                    e2.start(10000);
                    //e3.start(600);
                    e4.start(20000);
                    //e5.start(1200);
                    //e6.start(1500);
                    e7.start(30000);
                    //e8.start(2100);
                    //e9.start(2400);
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

            //Inne i denna loopen ska all logik och uppdateringar ske
            while (gameRunning) {
                p.handleInput();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (screen  != null) {
                try {
                    screen.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static boolean isGameRunning() {
        return gameRunning;
    }

    public static void setGameRunning(boolean onOff) {
        gameRunning = onOff;
    }

    public static void printToScreen(int x, int y, char c) throws Exception {
        printToScreen(x, y, c, null, true);
    }
    public static void printToScreen(int x, int y, char c, TextColor tc) throws Exception {
        printToScreen(x, y, c, tc, true);
    }
    public static void printToScreen(int x, int y, char c, TextColor tc, boolean refresh) throws Exception {
        TerminalPosition cellToModify = new TerminalPosition(x, y);
        TextCharacter characterInBackBuffer = screen.getBackCharacter(cellToModify);
        if(tc != null)
            characterInBackBuffer = characterInBackBuffer.withForegroundColor(tc);
        characterInBackBuffer = characterInBackBuffer.withCharacter(c);
        if(refresh) {
            synchronized (screen) {
                screen.setCharacter(cellToModify, characterInBackBuffer);
                screen.refresh();
            }
        }
    }
}