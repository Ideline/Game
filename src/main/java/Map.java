import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.StyleSet;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class Map {

    public Map() {
    }

    private Character[][] map, coinMap = null;
    private int mapRowLength, mapRowHeight = 0;
    private int mapPaddingX = 0;
    private int mapPaddingY = 0;
    private boolean coinMode = true;
    private int nrCoinsStart = 0; // NYTT
    private int nrCoinsTaken = 0; // NYTT
    private int mapRotate = 1;
    private ArrayList<Coordinates> portalCoordinates = new ArrayList<Coordinates>();
    public static long buffStartTime;

    public ArrayList<Coordinates> getPortalCoordinates() {
        return portalCoordinates;
    }

    public void setMapRotate(int mapRotate) {
        this.mapRotate = mapRotate;
    }

    public int getMapRotate() {
        return mapRotate;
    }

    public int getNrCoinsStart() { // NYTT
        for (int y = 0; y < mapRowHeight; y++) {
            for (int x = 0; x < mapRowLength; x++) {
                if (coinMap[x][y] == '*') nrCoinsStart++;
            }
        }
        return nrCoinsStart;
    }

    public int getNrCoinsTaken() { // NYTT
        return nrCoinsTaken;
    }

    public void setNrCoinsTaken(int nrCoinsTaken) {
        this.nrCoinsTaken = nrCoinsTaken;
    }

    public boolean isCoinmode() { // NYTT
        return coinMode;
    }

    public void addCoin() { // NYTT
        nrCoinsTaken++;
    }

    public void init() throws Exception {
        if (!drawMap())
            throw new Exception("Kunde inte skapa banan.");
        if (coinMode && !drawCoinMap()) {
            throw new Exception("Kunde inte skapa banan.");
        }
        for (int j = 0; j < mapRowHeight; j++) {
            for (int i = 0; i < mapRowLength; i++) {
                if (map[i][j] == ('Q')) {
                    portalCoordinates.add(new Coordinates(i + mapPaddingX, j + mapPaddingY));
                }
            }
        }
    }

    public void setCoinMode(boolean coinMode) {
        this.coinMode = coinMode;
    }

    public boolean isCoinMode() {
        return coinMode;
    }

    public int getMapPaddingX() {
        return mapPaddingX;
    }

    public int getMapPaddingY() {
        return mapPaddingY;
    }

    public Character[][] getCoinMap() {
        return coinMap;
    }

    public Character[][] getMap() {
        return map;
    }

    public int getMapRowHeight() {
        return mapRowHeight;
    }

    public int getMapRowLength() {
        return mapRowLength;
    }

    public boolean drawMap() {

        String path = Paths.get(".").toAbsolutePath().normalize().toString();
        Screen screen = Game.getScreen();

        try {
            String tempMap = new String(Files.readAllBytes(Paths.get(path + "/maps/map" + mapRotate + "/" + mapRotate + ".map"))); // NYTT
            mapRowLength = tempMap.indexOf("\r\n");
            tempMap = tempMap.replace("\r\n", "");
            mapRowHeight = tempMap.length() / mapRowLength;
            map = new Character[mapRowLength][mapRowHeight];
            for (int y = 0; y < mapRowHeight; y++) {
                for (int x = 0; x < mapRowLength; x++) {
                    int index = x + (y * mapRowLength);
                    map[x][y] = tempMap.charAt(index);
                }
            }

            if (map.length % mapRowLength != 0) {
                throw new Exception("Fel antal tecken i filen.");
            }

            mapPaddingX = (screen.getTerminalSize().getColumns() / 2) - (mapRowLength / 2);
            mapPaddingY = (screen.getTerminalSize().getRows() / 2) - (mapRowHeight / 2);

            char c;
            TextColor tc;
            for (int y = 0; y < mapRowHeight; y++) {
                for (int x = 0; x < mapRowLength; x++) {
                    switch (map[x][y]) {
                        case '0':
                            tc = TextColor.ANSI.WHITE;
                            c = ' ';
                            break;
                        case '1':
                            tc = TextColor.ANSI.BLUE;
                            c = '║';
                            break;
                        case '2':
                            tc = TextColor.ANSI.BLUE;
                            c = '═';
                            break;
                        case '4':
                            tc = TextColor.ANSI.BLUE;
                            c = '╗';
                            break;
                        case '6':
                            tc = TextColor.ANSI.BLUE;
                            c = '╝';
                            break;
                        case '5':
                            tc = TextColor.ANSI.BLUE;
                            c = '╚';
                            break;
                        case '3':
                            tc = TextColor.ANSI.BLUE;
                            c = '╔';
                            break;
                        case '*':
                            tc = TextColor.ANSI.WHITE;
                            c = '•';
                            break;
                        case '.':
                            tc = TextColor.ANSI.WHITE;
                            c = ' ';
                            break;
                        default:
                            tc = TextColor.ANSI.WHITE;
                            c = ' ';
                            break;
                    }

                    TerminalPosition cellToModify = new TerminalPosition(x + mapPaddingX, y + mapPaddingY);
                    TextCharacter characterInBackBuffer = screen.getBackCharacter(cellToModify);
                    characterInBackBuffer = characterInBackBuffer.withForegroundColor(tc);
                    characterInBackBuffer = characterInBackBuffer.withCharacter(c);
                    synchronized (screen) {
                        screen.setCharacter(cellToModify, characterInBackBuffer);
                    }
                }
            }

            synchronized (screen) {
                screen.refresh();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean drawCoinMap() {

        String path = Paths.get(".").toAbsolutePath().normalize().toString();
        Screen screen = Game.getScreen();

        try {
            String tempCoinMap = new String(Files.readAllBytes(Paths.get(path + "/maps/map" + mapRotate + "/" + mapRotate + "coin.map"))); // NYTT
            mapRowLength = tempCoinMap.indexOf("\r\n");
            tempCoinMap = tempCoinMap.replace("\r\n", "");
            mapRowHeight = tempCoinMap.length() / mapRowLength;
            coinMap = new Character[mapRowLength][mapRowHeight];
            for (int y = 0; y < mapRowHeight; y++) {
                for (int x = 0; x < mapRowLength; x++) {
                    int index = x + (y * mapRowLength);
                    coinMap[x][y] = tempCoinMap.charAt(index);
                }
            }

            if (coinMap.length % mapRowLength != 0) {
                throw new Exception("Fel antal tecken i filen.");
            }

            mapPaddingX = (screen.getTerminalSize().getColumns() / 2) - (mapRowLength / 2);
            mapPaddingY = (screen.getTerminalSize().getRows() / 2) - (mapRowHeight / 2);

            TextColor tc = TextColor.ANSI.WHITE;
            char c = ' ';
            for (int y = 0; y < mapRowHeight; y++) {
                for (int x = 0; x < mapRowLength; x++) {

                    switch (coinMap[x][y]) {
                        case '*':
                            tc = TextColor.ANSI.WHITE;
                            c = '•';
                            break;
                        default:
                            tc = TextColor.ANSI.WHITE;
                            c = ' ';
                            break;
                    }

                    if (c != ' ') {
                        TerminalPosition cellToModify = new TerminalPosition(x + mapPaddingX, y + mapPaddingY);
                        TextCharacter characterInBackBuffer = screen.getBackCharacter(cellToModify);
                        characterInBackBuffer = characterInBackBuffer.withForegroundColor(tc);
                        characterInBackBuffer = characterInBackBuffer.withCharacter(c);
                        synchronized (screen) {
                            screen.setCharacter(cellToModify, characterInBackBuffer);
                        }
                    }
                }
            }
            synchronized (screen) {
                screen.refresh();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static void drawMapStats() throws Exception { // NYTT
        Screen screen = Game.getScreen();
        Game.stats.setMapScore(); // NYTT
        //https://github.com/mabe02/lanterna/blob/master/docs/tutorial/Tutorial03.md
        String timeLabel = Statistics.formateTime(Game.stats.getMapTime()); // NYTT
        String pointsLabel = "" + Game.stats.getMapScore(); // NYTT
        TerminalPosition labelBoxTopLeft = new TerminalPosition(1, 0);
        TerminalSize labelBoxSize = new TerminalSize(timeLabel.length() + 2 + pointsLabel.length(), 1); // NYTT
        TextGraphics textGraphics = screen.newTextGraphics();

        //http://mabe02.github.io/lanterna/apidocs/3.0/com/googlecode/lanterna/graphics/StyleSet.html // NYTT
        textGraphics.setStyleFrom(new StyleSet.Set().enableModifiers(SGR.BOLD).setForegroundColor(TextColor.ANSI.RED));
        textGraphics.fillRectangle(labelBoxTopLeft, labelBoxSize, ' ');

        //String buffName = Game.player.buffName;

        //if (buffName == null) {
            if (Game.map.isCoinMode()) { // NYTT
                textGraphics.putString(labelBoxTopLeft.withRelative(1, 1), "time: " + timeLabel + " score: " + pointsLabel); // NYTT
            } else textGraphics.putString(labelBoxTopLeft.withRelative(1, 1), "time: " + timeLabel);
        //}
        //else {
          //  String timeRemaining = Statistics.formateTime(Game.player.buffTimeLeft(buffStartTime));
          //  textGraphics.putString(labelBoxTopLeft.withRelative(1, 1), "time: " + timeLabel + " score: " + pointsLabel + " " + buffName + ": " + timeRemaining + "s");
        //}

        synchronized (screen) {
            screen.refresh();
        }
    }

    public boolean anyCoinsLeft() {

        int nrCoins = 0;
        for (int j = 0; j < mapRowHeight; j++) {
            for (int i = 0; i < mapRowLength; i++) {
                if (coinMap[i][j] == '*') {
                    nrCoins++;
                }
            }
        }
        if (nrCoins > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void printToScreen(int x, int y, char c) throws Exception {
        printToScreen(x, y, c, null, true);
    }

    public static void printToScreen(int x, int y, char c, TextColor tc) throws Exception {
        printToScreen(x, y, c, tc, true);
    }

    public static void printToScreen(int x, int y, char c, TextColor tc, boolean refresh) throws Exception {
        Screen screen = Game.getScreen();
        TerminalPosition cellToModify = new TerminalPosition(x, y);
        TextCharacter characterInBackBuffer = screen.getBackCharacter(cellToModify);
        if (tc != null)
            characterInBackBuffer = characterInBackBuffer.withForegroundColor(tc);
        characterInBackBuffer = characterInBackBuffer.withCharacter(c);
        if (refresh) {
            synchronized (screen) {
                screen.setCharacter(cellToModify, characterInBackBuffer);
                screen.refresh();
            }
        }
    }
}
