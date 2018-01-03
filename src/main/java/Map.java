import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Map {

    public Map(){

    }

    private static Terminal terminal;
    private static Character[][] map, coinMap = null;
    private static int mapRowLength, mapRowHeight = 0;
    private static int mapPaddingX = 0;
    private static int mapPaddingY = 0;
    private static boolean coinMode = true;

    public static void init() {
        terminal = Game.getTerminal();
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
            String tempCoinMap = new String(Files.readAllBytes(Paths.get(path + "/maps/2coin.map")));
            mapRowLength = tempMap.indexOf("\r\n");
            tempMap = tempMap.replace("\r\n", "");
            tempCoinMap = tempCoinMap.replace("\r\n", "");
            mapRowHeight = tempMap.length() / mapRowLength;
            map = new Character[mapRowLength][mapRowHeight];
            coinMap = new Character[mapRowLength][mapRowHeight];
            for (int y = 0; y < mapRowHeight; y++) {
                for (int x = 0; x < mapRowLength; x++) {
                    int index = x + (y*mapRowLength);
                    map[x][y] = tempMap.charAt(index);
                    coinMap[x][y] = tempCoinMap.charAt(index);
                }
            }
//
            if (map.length % mapRowLength != 0) {
                throw new Exception("Fel antal tecken i filen.");
            }

            mapPaddingX = (terminal.getTerminalSize().getColumns() / 2) - (mapRowLength / 2);
            mapPaddingY = (terminal.getTerminalSize().getRows() / 2) - (mapRowHeight / 2);

            for (int y = 0; y < mapRowHeight; y++) {
                for (int x = 0; x < mapRowLength; x++) {

                    terminal.setCursorPosition(x + mapPaddingX, y + mapPaddingY);

                    switch (map[x][y]) {
                        case '0':
                            terminal.setForegroundColor(TextColor.ANSI.WHITE);
                            //terminal.putCharacter('▪');
                            terminal.putCharacter(' ');
                            break;
                        case '1':
                            terminal.setForegroundColor(TextColor.ANSI.BLUE);
                            terminal.putCharacter('║');
                            break;
                        case '2':
                            terminal.setForegroundColor(TextColor.ANSI.BLUE);
                            terminal.putCharacter('═');
                            break;
                        case '4':
                            terminal.setForegroundColor(TextColor.ANSI.BLUE);
                            terminal.putCharacter('╗');
                            break;
                        case '6':
                            terminal.setForegroundColor(TextColor.ANSI.BLUE);
                            terminal.putCharacter('╝');
                            break;
                        case '5':
                            terminal.setForegroundColor(TextColor.ANSI.BLUE);
                            terminal.putCharacter('╚');
                            break;
                        case '3':
                            terminal.setForegroundColor(TextColor.ANSI.BLUE);
                            terminal.putCharacter('╔');
                            break;
                        case '*':
                            terminal.setForegroundColor(TextColor.ANSI.WHITE);
                            //terminal.setBackgroundColor(TextColor.ANSI.RED);
                            terminal.putCharacter('•');
                            //terminal.setBackgroundColor(TextColor.ANSI.DEFAULT);
                            break;
                        case '.':
                            terminal.setForegroundColor(TextColor.ANSI.WHITE);
                            terminal.putCharacter(' ');
                            break;
                    }

                    if(coinMode) {
                        terminal.setCursorPosition(x + mapPaddingX, y + mapPaddingY);
                        switch (coinMap[x][y]) {
                            case '*':
                                terminal.setForegroundColor(TextColor.ANSI.WHITE);
                                terminal.putCharacter('•');
                                break;
                        }
                    }
                }
            }
            terminal.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
