import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Map {

    public Map(){

    }

    private static Screen screen;
    private static Character[][] map, coinMap = null;
    private static int mapRowLength, mapRowHeight = 0;
    private static int mapPaddingX = 0;
    private static int mapPaddingY = 0;
    private static boolean coinMode = true;

    public static void init(boolean coinMode) throws Exception {
        screen = Game.getScreen();
        if (!drawMap())
            throw new Exception("Kunde inte skapa banan.");
        if(coinMode) {
            if (!drawCoinMap())
                throw new Exception("Kunde inte skapa banan.");
        }
    }

    public static int getMapPaddingX() {
        return mapPaddingX;
    }

    public static int getMapPaddingY() {
        return mapPaddingY;
    }

    public static Character[][] getCoinMap() {
        return coinMap;
    }

    public static Character[][] getMap() {
        return map;
    }

    public static int getMapRowHeight() {
        return mapRowHeight;
    }

    public static int getMapRowLength() {
        return mapRowLength;
    }

    public static boolean drawMap() {

        String path = Paths.get(".").toAbsolutePath().normalize().toString();

        try {
            String tempMap = new String(Files.readAllBytes(Paths.get(path + "/maps/2.map")));
            mapRowLength = tempMap.indexOf("\r\n");
            tempMap = tempMap.replace("\r\n", "");
            mapRowHeight = tempMap.length() / mapRowLength;
            map = new Character[mapRowLength][mapRowHeight];
            for (int y = 0; y < mapRowHeight; y++) {
                for (int x = 0; x < mapRowLength; x++) {
                    int index = x + (y*mapRowLength);
                    map[x][y] = tempMap.charAt(index);
                }
            }

            if (map.length % mapRowLength != 0) {
                throw new Exception("Fel antal tecken i filen.");
            }

            mapPaddingX = (screen.getTerminalSize().getColumns() / 2) - (mapRowLength / 2);
            mapPaddingY = (screen.getTerminalSize().getRows() / 2) - (mapRowHeight / 2);

            TextColor tc = TextColor.ANSI.WHITE;
            char c = ' ';
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

    public static boolean drawCoinMap() {

        String path = Paths.get(".").toAbsolutePath().normalize().toString();

        try {
            String tempCoinMap = new String(Files.readAllBytes(Paths.get(path + "/maps/2coin.map")));
            mapRowLength = tempCoinMap.indexOf("\r\n");
            tempCoinMap = tempCoinMap.replace("\r\n", "");
            mapRowHeight = tempCoinMap.length() / mapRowLength;
            coinMap = new Character[mapRowLength][mapRowHeight];
            for (int y = 0; y < mapRowHeight; y++) {
                for (int x = 0; x < mapRowLength; x++) {
                    int index = x + (y*mapRowLength);
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

                    if(c != ' ') {
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

    public static boolean anyCoinsLeft(){

        int nrCoins = 0;
        for(int j = 0; j < mapRowHeight; j++) {
            for (int i = 0; i < mapRowLength; i++) {
                if (coinMap[i][j] == '*') {
                    nrCoins++;
                }
            }
        }
        if(nrCoins > 0){
            return true;
        }
        else{
            return false;
        }
    }
}
