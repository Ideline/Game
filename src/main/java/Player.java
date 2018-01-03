import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.Terminal;

public class Player {
    public Player(boolean gameRunning) {
        this.terminal = Game.getTerminal();
        this.map = Map.getMap();
        this.coinMap = Map.getCoinMap();
        this.mapRowLength = Map.getMapRowLength();
        this.mapPaddingX = Map.getMapPaddingX();
        this.mapPaddingY = Map.getMapPaddingY();
        this.mapRowHeight = Map.getMapRowHeight();
        this.gameRunning = gameRunning;
    }

    private Terminal terminal;
    private int x, y;
    private Character[][] map;
    private Character[][] coinMap;
    private int mapPaddingX, mapPaddingY, mapRowLength, mapRowHeight;
    private long startTime = 0;
    private int globalDelay = 200;
    private boolean gameRunning;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void initPlayer() {
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
            KeyStroke ks = terminal.pollInput();
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
                    gameRunning = false;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        startTime = System.currentTimeMillis();
    }


    public void MoveUp() {
        if (isMovePossible(x, y - 1)) {
            resetPlayer();
            y--;
            setCharacter();
        }
    }

    public void MoveDown() {
        if (isMovePossible(x, y + 1)) {
            resetPlayer();
            y++;
            setCharacter();
        }
    }

    public void MoveLeft() {
        if (isMovePossible(x - 1, y)) {
            resetPlayer();
            x = x - 2;
            setCharacter();
        }
    }

    public void MoveRight() {
        if (isMovePossible(x + 1, y)) {
            resetPlayer();
            x = x + 2;
            setCharacter();
        }
    }

    synchronized private void resetPlayer() {
        try {
            //Markera att vi har varit här
//            int index = (x - mapPaddingX) + (y - mapPaddingY) * mapRowLength;
//            String s = map[index];
//            map[index] = (s.equals(".") || s.equals("!") ? "!" : "^");
            terminal.setCursorPosition(x, y);
            terminal.putCharacter(' ');
            if(coinMap[x-mapPaddingX][y-mapPaddingY] == '*'){
                coinMap[x-mapPaddingX][y-mapPaddingY] = ' ';
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCharacter() {
        try {
            terminal.setCursorPosition(x, y);
            terminal.setForegroundColor(TextColor.ANSI.YELLOW);
            terminal.putCharacter('☻');
            terminal.flush();
        } catch (Exception e) {
            e.printStackTrace();
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
