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
    public static boolean reverse = false;
    public static boolean cherryMode = false;
    public static boolean wallWalker = false;
    public static boolean speedBuff = false;

    public void setGlobalDelay(int globalDelay) {
        this.globalDelay = globalDelay;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void init() throws Exception {
        for (int j = 0; j < mapRowHeight; j++) {
            for (int i = 0; i < mapRowLength; i++) {
                try {
                    if (map[i][j] == ('P')) {
                        x = i + mapPaddingX;
                        y = j + mapPaddingY;
                        int bajs = 0;
                    }
                }
                catch (Exception e){
                    int bajs = 0;
                    e.printStackTrace();
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
                    if (reverse) MoveDown();
                    else MoveUp();
                    break;
                }
                case ArrowDown: {
                    if (reverse) MoveUp();
                    else MoveDown();
                    break;
                }
                case ArrowLeft: {
                    if (reverse) MoveRight();
                    else MoveLeft();
                    break;
                }
                case ArrowRight: {
                    if (reverse) MoveLeft();
                    else MoveRight();
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
        gotSpecial(x, y - 1);
        if (y - 1 - mapPaddingY <= mapRowHeight && y - 1 - mapPaddingY >= 0) {
            if (isPortal(x, y - 1)) {
                moveToPortal(x, y - 1);
            } else if (isMovePossible(x, y - 1)) {
                resetPlayer();
                y--;
                setCharacter();
            }
        } else moveToPortal(x, y - 1);
    }

    public void MoveDown() throws Exception {
        gotSpecial(x, y + 1);
        if (y + 1 - mapPaddingY <= mapRowHeight && y + 1 - mapPaddingY >= 0) {
            if (isPortal(x, y + 1)) {
                moveToPortal(x, y + 1);
            } else if (isMovePossible(x, y + 1)) {
                resetPlayer();
                y++;
                setCharacter();
            }
        } else moveToPortal(x, y + 1);
    }

    public void MoveLeft() throws Exception {
        gotSpecial(x - 2, y);
        if (isPortal(x - 2, y)) {
            moveToPortal(x - 2, y);
        } else if (isMovePossible(x - 2, y)) {
            resetPlayer();
            x = x - 2;
            setCharacter();
        }
    }

    public void MoveRight() throws Exception {
        gotSpecial(x + 2, y);
        if (isPortal(x + 2, y)) {
            moveToPortal(x + 2, y);
        } else if (isMovePossible(x + 2, y)) {
            resetPlayer();
            x = x + 2;
            setCharacter();
        }
    }

    private void resetPlayer() {
        try {
            TerminalPosition cellToModify = new TerminalPosition(x, y);
            char c = ' ';
            TextColor tc = TextColor.ANSI.DEFAULT;
            switch (map[x - mapPaddingX][y - mapPaddingY]) {
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
                default:
                    TextCharacter characterInBackBuffer = screen.getBackCharacter(cellToModify);
                    characterInBackBuffer = characterInBackBuffer.withCharacter(' ');
                    screen.setCharacter(cellToModify, characterInBackBuffer);
                    break;
            }

            TextCharacter textC = new TextCharacter(c, tc, TextColor.ANSI.DEFAULT);
            screen.setCharacter(cellToModify, textC);

            if (coinMap != null && coinMap[x - mapPaddingX][y - mapPaddingY] == '*') {
                coinMap[x - mapPaddingX][y - mapPaddingY] = ' ';
                Game.map.addCoin();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gotSpecial(int moveX, int moveY) throws Exception {
        TextCharacter tc = Game.getScreen().getFrontCharacter(moveX, moveY);
        char c = tc.getCharacter();
        switch (c) {
            case 'S':
                speedBuff = true;
                Buffs b = new Buffs();
                b.setRunSpeed(true);
                b.start();
                break;
            case 'R':
                reverse = !reverse;
                if (reverse) {
                    Buffs b2 = new Buffs();
                    b2.setReverse(true);
                    b2.start();
                }
                break;
            case 'C':
                cherryMode = true;
                Buffs b3 = new Buffs();
                b3.setCherryMode(true);
                b3.start();
                break;
            case 'W':
                wallWalker = true;
                Buffs b4 = new Buffs();
                b4.setWallWalkerMode(true);
                b4.start();
                break;
            case '$':
                Game.stats.setBonusPoints(Game.stats.getBonusPoints() + 400);
                break;
        }
    }

    private void setCharacter() throws Exception {
        TextColor color = TextColor.ANSI.YELLOW;
        if(cherryMode) color = TextColor.ANSI.RED;
        else if(wallWalker) color = TextColor.ANSI.CYAN;
        else if(reverse) color = TextColor.ANSI.MAGENTA;
        else if(speedBuff) color = TextColor.ANSI.BLUE;
        Map.printToScreen(x, y, '☻', color);

        if (Game.map.isCoinMode() && !Game.map.anyCoinsLeft()) {  // NYTT
            Game.gameState = GameState.GAME_WON;
            Game.setGameRunning(false);
        }
    }

    private boolean isPortal(int x, int y) {
        if (x - mapPaddingX < mapRowLength && x - mapPaddingX >= 0 && y - mapPaddingY < mapRowHeight && y - mapPaddingY >= 0) {
            int index1 = (x - mapPaddingX);
            int index2 = (y - mapPaddingY);
            char c = map[index1][index2];
            if (c == ('Q')) return true;
            else return false;
        } else {
            try {
                moveToOtherPortal();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void moveToPortal(int moveX, int moveY) throws Exception {
        for (int j = 0; j < mapRowHeight; j++) {
            for (int i = 0; i < mapRowLength; i++) {
                if (map[i][j] == ('Q') && (i + mapPaddingX != moveX || j + mapPaddingY != moveY)) {
                    resetPlayer();
                    x = i + mapPaddingX;
                    y = j + mapPaddingY;
                    setCharacter();
                    return;
                }
            }
        }
    }

    private void moveToOtherPortal() throws Exception {
        for (int j = 0; j < mapRowHeight; j++) {
            for (int i = 0; i < mapRowLength; i++) {
                if (map[i][j] == ('Q') && (i + mapPaddingX != x || j + mapPaddingY != y)) {
                    resetPlayer();
                    x = i + mapPaddingX;
                    y = j + mapPaddingY;
                    setCharacter();
                    return;
                }
            }
        }
    }

    private boolean isMovePossible(int x, int y) {
        int index1 = (x - mapPaddingX);
        int index2 = (y - mapPaddingY);

        char c = map[index1][index2];

        if (wallWalker) {
            if (c == (' ') || c == ('.') || c == ('A') || c == ('B') || c == ('C') || c == ('D') || c == ('Q') || c == ('P') || c == ('E') || c == (',') || c == ('0') || c == ('1') || c == ('2') || c == ('3') || c == ('4') || c == ('5') || c == ('6')) {
                return true;
            }
        } else if (c == (' ') || c == ('.') || c == ('A') || c == ('B') || c == ('C') || c == ('D') || c == ('Q') || c == ('P') || c == ('E') || c == (','))
            return true;
        return false;
    }
}
