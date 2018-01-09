import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

public class Player {
    public Player() {
        this.screen = Game.getScreen();
        this.map = Game.map.getMap();
        this.coinMap = Game.map.getCoinMap();
        this.mapRowLength = Game.map.getMapRowLength();
        this.mapPaddingX = Game.map.getMapPaddingX();
        this.mapPaddingY = Game.map.getMapPaddingY();
        this.mapRowHeight = Game.map.getMapRowHeight();
    }

    private Screen screen;
    private int x, y;
    private Character[][] map;
    private Character[][] coinMap;
    private int mapPaddingX, mapPaddingY, mapRowLength, mapRowHeight;
    private long startTime = 0;
    private int globalDelay = 200;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void init() throws Exception {
        for(int j = 0; j < mapRowHeight; j++) {
            for (int i = 0; i < mapRowLength; i++) {
                if (map[i][j] == ('P')) {
                    x = i + mapPaddingX;
                    y = j + mapPaddingY;

                }
            }
        }
        setCharacter();
    }

    public void handleInput() {
        try {
            KeyStroke ks = screen.pollInput();
            if (ks == null)
                return;

            //Todo: Kanske endast ska implementeras för rörelser...
            if (System.currentTimeMillis() - startTime < globalDelay)
                return;

            KeyType kt = ks.getKeyType();

            //Skippa hanteringen av tecken
            if (kt == KeyType.Character)
                return;

            switch (kt) {
                case ArrowUp: {
                    MoveUp();
                    break;
                }
                case ArrowDown: {
                    MoveDown();
                    break;
                }
                case ArrowLeft: {
                    MoveLeft();
                    break;
                }
                case ArrowRight: {
                    MoveRight();
                    break;
                }
                case Escape: {
                    Game.stats.resetScore(); // NYTT
                    Game.gameState = GameState.MENU;
                    Game.setGameRunning(false);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        startTime = System.currentTimeMillis();
    }


    public void MoveUp() throws Exception {
        if (isMovePossible(x, y - 1)) {
            resetPlayer();
            y--;
            setCharacter();
        }
    }

    public void MoveDown() throws Exception {
        if (isMovePossible(x, y + 1)) {
            resetPlayer();
            y++;
            setCharacter();
        }
    }

    public void MoveLeft() throws Exception {
        if (isMovePossible(x - 1, y)) {
            resetPlayer();
            x = x - 2;
            setCharacter();
        }
    }

    public void MoveRight() throws Exception {
        if (isMovePossible(x + 1, y)) {
            resetPlayer();
            x = x + 2;
            setCharacter();
        }
    }

    private void resetPlayer() {
        try {
            TerminalPosition cellToModify = new TerminalPosition(x, y);
            TextCharacter characterInBackBuffer  = screen.getBackCharacter(cellToModify);
            characterInBackBuffer = characterInBackBuffer.withCharacter(' ');
            screen.setCharacter(cellToModify, characterInBackBuffer);

            if(coinMap != null && coinMap[x-mapPaddingX][y-mapPaddingY] == '*'){
                coinMap[x-mapPaddingX][y-mapPaddingY] = ' ';
                Game.map.addCoin();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCharacter() throws Exception {
        Map.printToScreen(x, y, '☻', TextColor.ANSI.YELLOW);

        if(Game.map.isCoinMode() && !Game.map.anyCoinsLeft()) {  // NYTT
            Game.gameState = GameState.GAME_WON;
            Game.setGameRunning(false);
        }
    }

    private boolean isMovePossible(int x, int y) {
        int index1 = (x - mapPaddingX);
        int index2 = (y - mapPaddingY);

        char c = map[index1][index2];
        if (c == (' ') || c == ('.'))
            return true;
        return false;
    }
}
