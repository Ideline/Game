import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.Random;

public class Enemy implements Runnable {

    public Enemy() {
    }

    public Enemy(Character[][] map, Character[][] coinMap, int mapRowLength, int mapRowHeight, int mapPaddingX, int mapPaddingY, String threadName) {
        this.map = map;
        this.coinMap = coinMap;
        this.mapRowLength = mapRowLength;
        this.mapRowHeight = mapRowHeight;
        this.mapPaddingX = mapPaddingX;
        this.mapPaddingY = mapPaddingY;
        this.isRunning = true;
        this.threadName = threadName;
    }

    private int x, y;
    private Character[][] map;
    private Character[][]coinMap;
    private int mapPaddingX, mapPaddingY, mapRowLength, mapRowHeight;
    private boolean coordinateContainsCrum = false;
    protected boolean isRunning;
    protected Thread t;
    protected String threadName;

    synchronized public int getX() {
        return x;
    }

    synchronized public void setX(int x) {
        this.x = x;
    }

    synchronized public int getY() {
        return y;
    }

    synchronized public void setY(int y) {
        this.y = y;
    }

    public String getThreadName() {
        return threadName;
    }

    public void initEnemy() throws Exception {
        for (int j = 0; j < mapRowHeight; j++) {
            for (int i = 0; i < mapRowLength; i++) {
                if (map[i][j] ==('E')) {
                    x = i + mapPaddingX;
                    y = j + mapPaddingY;
                    setCharacter();
                    break;
                }
            }
        }
    }

    private void setCharacter() throws Exception {
        TextColor tc = TextColor.ANSI.WHITE;
        switch (this.getClass().getName()) {
            case "EasyEnemy":
                tc = TextColor.ANSI.WHITE;
                break;
            case "MediumEnemy":
                tc = TextColor.ANSI.GREEN;
                break;
            case "HardEnemy":
                tc = TextColor.ANSI.RED;
                break;
        }
        caughtPlayer();
        Map.printToScreen(x, y, 'Ω', tc);
    }

    private void movement() {
    }

    synchronized protected int randomizer() {
        Random rand = new Random();
        int direction = rand.nextInt(4) + 1;
        return direction;
    }

//    private String getCoordinateObject(int x, int y) {
//        int index = (x - mapPaddingX) + (y - mapPaddingY) * mapRowLength;
//        return map[index];
//    }

    synchronized protected boolean move(int gcd, int direction) {
        return move(gcd, direction, false);
    }

    synchronized protected boolean move(int gcd, int direction, boolean privileged) {
        try {
            switch (direction) {
                case 1: { // upp
                    if (isMovePossible(x, y - 1, privileged)) {
                        Thread.sleep(gcd);
                        caughtPlayer();
                        resetEnemy();
                        y--;
                        setCharacter();
                        if (isCrossing()) {
                            return false;
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
                case 2: { // ner
                    if (isMovePossible(x, y + 1, privileged)) {
                        Thread.sleep(gcd);
                        caughtPlayer();
                        resetEnemy();
                        y++;
                        setCharacter();
                        if (isCrossing()) {
                            return false;
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
                case 3: { // vänster
                    if (isMovePossible(x - 2, y, privileged)) {
                        Thread.sleep(gcd);
                        caughtPlayer();
                        resetEnemy();
                        x = x - 2;
                        setCharacter();
                        if (isCrossing()) {
                            return false;
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
                case 4: { // höger
                    if (isMovePossible(x + 2, y, privileged)) {
                        Thread.sleep(gcd);
                        caughtPlayer();
                        resetEnemy();
                        x = x + 2;
                        setCharacter();
                        if (isCrossing()) {
                            return false;
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
                default:
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void resetEnemy() throws Exception {
        char c = ' ';
        if(coinMap != null && Game.map.isCoinMode()) { // NYTT
            c = (coinMap[x-mapPaddingX][y-mapPaddingY] == '*' ? '•' : ' ');
        }
        Map.printToScreen(x, y, c, TextColor.ANSI.DEFAULT);
    }

    private boolean isMovePossible(int x, int y) {
        return isMovePossible(x, y, false);
    }

    private boolean isMovePossible(int x, int y, boolean privileged) {
        int index1 = (x - mapPaddingX);
        int index2 = (y - mapPaddingY);
        char c = map[index1][index2];
        if (c == ('P') || c == ('E') || c == (' ') || c == ('.') || c == (',') || c == ('^') || (c == ('-') && privileged))
            return true;
        return false;
    }

    private boolean isCrossing() {
        int index1 = (x - mapPaddingX);
        int index2 = (y - mapPaddingY);
        char c = map[index1][index2];
        if (c == ('.')) {
            return true;
        }
        return false;
    }

    public boolean isExit() {
        int index1 = (x - mapPaddingX);
        int index2 = (y - mapPaddingY);
        char c = map[index1][index2];
        if (c == (',')) {
            return true;
        }
        return false;
    }

    public int exitNest(int gcd) {
        return exitNest(gcd, false);
    }

    public int exitNest(int gcd, boolean hardMode) {
        int move2 = 0;
        while (!isExit()) {
            move(gcd, 3, true);
        }
        for (int i = 0; i < 3; i++) {
            move(gcd, 1, true);
        }
        if (!hardMode) {
            Random r = new Random();
            move2 = r.nextInt(2) + 3;
            move(gcd, move2);
        } else {
            move2 = huntPlayer().getDirection();
        }
        return move2;
    }

    public Coordinates findPlayer() {
        Coordinates xy = new Coordinates(Game.player.getX(), Game.player.getY());
        return xy;
    }

    public DirectionResult huntPlayer() {
        DirectionResult dr = new DirectionResult();
        Coordinates c = findPlayer();
        String directionS = "";
        int direction = 0;
        int nr = 0;
        if (c.getX() < x) {
            // gå väst
            directionS = directionS + "w";
        } else if (c.getX() == x) {
            // gå nord eller syd
            directionS = directionS + "ns";
        } else if (c.getX() > x) {
            // gå öst
            directionS = directionS + "e";
        }
        if (c.getY() < y) {
            // gå norr
            directionS = directionS + "n";
        } else if (c.getY() == y) {
            // gå öst eller väst
            directionS = directionS + "ew";
        } else if (c.getY() > y) {
            // gå söder
            directionS = directionS + "s";
        }

        switch (directionS) {
            case "wn":
                if (isMovePossible(x, y - 1) && isMovePossible(x - 2, y)) {
                    nr = randomizer();
                    if (nr == 1 || nr == 2) {
                        direction = 1;
                    } else
                        direction = 3;
                } else if (isMovePossible(x, y - 1)) {
                    direction = 1;
                } else if (isMovePossible(x - 2, y)) {
                    direction = 3;
                } else {
                    direction = randomizer();
                }
                break;
            case "wew":
                direction = isMovePossible(x - 2, y) ? 3 : randomizer();
                break;
            case "ws":
                if (isMovePossible(x, y + 1) && isMovePossible(x - 2, y)) {
                    nr = randomizer();
                    if (nr == 1 || nr == 2) {
                        direction = 2;
                    } else
                        direction = 3;
                } else if (isMovePossible(x, y + 1)) {
                    direction = 2;
                } else if (isMovePossible(x - 2, y)) {
                    direction = 3;
                } else {
                    direction = randomizer();
                }
                break;
            case "nsn":
                direction = isMovePossible(x, y - 1) ? 1 : randomizer();
                break;
            case "nsew":
                // gameOver
                break;
            case "nss":
                direction = isMovePossible(x, y + 1) ? 2 : randomizer();
                break;
            case "en":
                if (isMovePossible(x, y - 1) && isMovePossible(x + 2, y)) {
                    nr = randomizer();
                    if (nr == 1 || nr == 2) {
                        direction = 1;
                    } else
                        direction = 4;
                } else if (isMovePossible(x, y - 1)) {
                    direction = 1;
                } else if (isMovePossible(x + 2, y)) {
                    direction = 4;
                } else {
                    direction = randomizer();
                }
            case "eew":
                direction = isMovePossible(x + 2, y) ? 4 : randomizer();
                break;
            case "es":
                if (isMovePossible(x + 2, y) && isMovePossible(x, y + 1)) {
                    nr = randomizer();
                    if (nr == 1 || nr == 2) {
                        direction = 4;
                    } else
                        direction = 2;
                } else if (isMovePossible(x + 2, y)) {
                    direction = 4;
                } else if (isMovePossible(x, y + 1)) {
                    direction = 2;
                } else {
                    direction = randomizer();
                }
                break;
        }

        dr.setDidMove(move(400, direction));
        dr.setDirection(direction);

        return dr;
    }

    public void caughtPlayer(){

        Coordinates c = findPlayer();
        int playerX = c.getX();
        int playerY = c.getY();

        if(x == playerX && y == playerY && Game.gameState != GameState.GAME_OVER) {

            Game.gameState = GameState.GAME_OVER;
            Game.setGameRunning(false);
            isRunning = false;
        }
    }

    public void run() {

    }

    public void start() {

    }
}
