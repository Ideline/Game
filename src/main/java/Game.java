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
    private static EasyEnemy e1, e2 = null;
    private static MediumEnemy e3, e4 = null;
    private static HardEnemy e5, e6 = null;
    private static Terminal terminal = null;
    private static long startTime = 0;
    private static int globalDelay = 400;
    private static String[] map = null;
    private static int mapRowLength = 0;
    private static int mapPaddingX = 0;
    private static int mapPaddingY = 0;
    private static int level = 1;

    public static void main(String[] args) throws InterruptedException  {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(140, 60));
        defaultTerminalFactory.setTerminalEmulatorTitle("Smurf 1.0");

        try {
            terminal = defaultTerminalFactory.createTerminal();
            terminal.setCursorVisible(false);

            if(!drawMap())
                throw new Exception("Kunde inte skapa banan.");

            switch (level){
                case 1:
                    e5 = new HardEnemy(terminal, map, mapRowLength, mapPaddingX, mapPaddingY, level, "e5");
                    e5.start();
                    /*
                    e1 = new EasyEnemy(terminal, map, mapRowLength, mapPaddingX, mapPaddingY, level, "e1");
                    e3 = new MediumEnemy(terminal, map, mapRowLength, mapPaddingX, mapPaddingY, level, "e3");
                    e1.start();
                    e3.start();*/
                    break;
                case 2:
                    e1 = new EasyEnemy(terminal, map, mapRowLength, mapPaddingX, mapPaddingY, level, "e1");
                    e3 = new MediumEnemy(terminal, map, mapRowLength, mapPaddingX, mapPaddingY, level, "e3");
                    e5 = new HardEnemy(terminal, map, mapRowLength, mapPaddingX, mapPaddingY, level, "e5");
                    break;
                case 3:
                    e1 = new EasyEnemy(terminal, map, mapRowLength, mapPaddingX, mapPaddingY, level, "e1");
                    e2 = new EasyEnemy(terminal, map, mapRowLength, mapPaddingX, mapPaddingY, level, "e2");
                    e3 = new MediumEnemy(terminal, map, mapRowLength, mapPaddingX, mapPaddingY, level, "e3");
                    e4 = new MediumEnemy(terminal, map, mapRowLength, mapPaddingX, mapPaddingY, level, "e4");
                    e5 = new HardEnemy(terminal, map, mapRowLength, mapPaddingX, mapPaddingY, level, "e5");
                    break;
            }

            p = new Player(terminal, map, mapRowLength, mapPaddingX, mapPaddingY);
            p.initPlayer();

            //Inne i denna loopen ska all logik och uppdateringar ske
            while(gameRunning) {
                handleInput();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if(terminal != null) {
                try {
                    terminal.close();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void handleInput() {
        try {
            KeyStroke ks = terminal.pollInput();
            if(ks == null)
                return;

            //Todo: Kanske endast ska implementeras för rörelser...
            if(System.currentTimeMillis() - startTime < globalDelay)
                return;

            KeyType kt = ks.getKeyType();

            //Skippa hanteringen av tecken
            if(kt == KeyType.Character)
                return;

            switch(kt)
            {
                case ArrowUp: {
                    p.MoveUp();
                    break;
                }
                case ArrowDown: {
                    p.MoveDown();
                    break;
                }
                case ArrowLeft: {
                    p.MoveLeft();
                    break;
                }
                case ArrowRight: {
                    p.MoveRight();
                    break;
                }
                case Escape: {
                    gameRunning = false;
                    break;
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        startTime = System.currentTimeMillis();
    }

    public void collisionCheck() {

    }

    public static boolean drawMap() {

        String path = Paths.get(".").toAbsolutePath().normalize().toString();

        try {
            String tempMap = new String(Files.readAllBytes(Paths.get(path + "/maps/2.map")));
            mapRowLength = tempMap.indexOf("\r\n");

            map = tempMap.replace("\r\n", "").split("");

            if(map.length % mapRowLength != 0) {
                throw new Exception("Fel antal tecken i filen.");
            }

            mapPaddingX = (terminal.getTerminalSize().getColumns() - mapRowLength) / 2;
            mapPaddingY = (terminal.getTerminalSize().getRows() - map.length / mapRowLength) / 2;

            for(int i = 0; i < map.length; i++){

                int y = (i > mapRowLength-1 ? (i / mapRowLength) : 0) + mapPaddingY;
                int x = (i > 0 ? (i % mapRowLength) : 0) + mapPaddingX;
                terminal.setCursorPosition(x, y);

                switch(map[i]) {
                    case "0":
                        terminal.setForegroundColor(TextColor.ANSI.WHITE);
                        //terminal.putCharacter('▪');
                        terminal.putCharacter(' ');
                        break;
                    case "1":
                        terminal.setForegroundColor(TextColor.ANSI.BLUE);
                        terminal.putCharacter('║');
                        break;
                    case "2":
                        terminal.setForegroundColor(TextColor.ANSI.BLUE);
                        terminal.putCharacter('═');
                        break;
                    case "4":
                        terminal.setForegroundColor(TextColor.ANSI.BLUE);
                        terminal.putCharacter('╗');
                        break;
                    case "6":
                        terminal.setForegroundColor(TextColor.ANSI.BLUE);
                        terminal.putCharacter('╝');
                        break;
                    case "5":
                        terminal.setForegroundColor(TextColor.ANSI.BLUE);
                        terminal.putCharacter('╚');
                        break;
                    case "3":
                        terminal.setForegroundColor(TextColor.ANSI.BLUE);
                        terminal.putCharacter('╔');
                        break;
                    case "*":
                        terminal.setForegroundColor(TextColor.ANSI.WHITE);
                        //terminal.setBackgroundColor(TextColor.ANSI.RED);
                        terminal.putCharacter('•');
                        //terminal.setBackgroundColor(TextColor.ANSI.DEFAULT);
                        break;
                    case ".":
                        terminal.setForegroundColor(TextColor.ANSI.WHITE);
                        terminal.putCharacter('•');
                        break;
                }
            }
            terminal.flush();
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}