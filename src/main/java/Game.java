import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;

public class Game {

    private static boolean gameRunning = true;
    public static Player player = null;
    private static Screen screen = null;
    private static int level = 1;
    public static GameState gameState = GameState.ENTERNAME; // NYTT
    public static Map map = null;
    public static Enemies enemies = null;
    public static int totalBots = 0;
    public static long startTime;
    public static Statistics stats; // NYTT
    public static Highscore highscore; // NYTT
    public static Highscore timescore;
    public static WindowBasedTextGUI textGUI = null; // NYTT
    public static String playerName = "";
    public static Specials s;

    public static boolean isGameRunning() {
        return gameRunning;
    }

    public static Screen getScreen() {
        return screen;
    }

    public static void setLevel(int level) {
        Game.level = level;
    }

    public static int getLevel() {
        return level;
    }

    public static void main(String[] args) throws Exception {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(140, 52));
        defaultTerminalFactory.setTerminalEmulatorTitle("FfakMan 1.0");

        map = new Map();
        stats = new Statistics(); // NYTT
        highscore = new Highscore(); // NYTT
        timescore = new Highscore();

        try {
            Terminal terminal = defaultTerminalFactory.createTerminal();
            //terminal.setCursorVisible(false);
            //terminal.setBackgroundColor(new TextColor.RGB(255, 0, 255));
            screen = new TerminalScreen(terminal);
            textGUI = new MultiWindowTextGUI(screen); // NYTT
            screen.startScreen();

            while(gameState != GameState.EXIT) {
                switch(gameState) {
                    case ENTERNAME: // NYTT
                        Menu.enterPlayerName();
                        break;
                    case MENU:
                        Menu.createMenu();
                        break;
                    case CHOOSEMAP:
                        Menu.chooseMap();
                        break;
                    case MENU_DIFFICULTY:
                        Menu.levelDifficulty();
                        break;
                    case GAME:
                        Menu.gameOver = false;
                        gameRunning = true;
                        runGame();
                        break;
                    case GAME_WON:
                        Menu.finishedLevel();
                        break;
                    case GAME_OVER:
                        Menu.gameOver();
                        break;
                    case HIGHSCORE:
                        Menu.highscoreMenu();
                        break;
                    case TIMESCORE:
                        Menu.timescoreMenu();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (screen != null) {
                try {
                    screen.stopScreen();
                    screen.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void runGame() throws Exception {
        screen.clear();
        screen.setCursorPosition(null);

        startTime = System.currentTimeMillis();
        Game.highscore.setLevel(level);
        Game.highscore.setNr(Game.map.getMapRotate());
        Game.timescore.setLevel(level);
        Game.timescore.setNr(Game.map.getMapRotate());
        highscore.createHighscoreLists(); // NYTT
        timescore.createHighscoreLists();

        Game.map.init();

        player = new Player();
        player.init();

        enemies = new Enemies();
        Game.enemies.init(level);
        Game.enemies.create();
        if(Game.map.getMapRotate() == 5) {
            Game.enemies.createLeftEnemies();
        }

        s = new Specials("mainSpecial");
        s.createSpawnPoints();
        s.makeSpecial();
        s.start();

        while (gameRunning) {
            player.handleInput();
            map.drawMapStats(); // NYTT

            //kolla om en buff i listan har en timeLeft <= 0. Plocka i så fall bort den.
        }

        for(Enemy e : enemies.getEnemies()) {
            e.isRunning = false;
        }
    }

    public static void setGameRunning(boolean running) {
        gameRunning = running;
    }
}