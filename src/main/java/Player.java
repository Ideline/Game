import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

public class Player {
    public Player() {
        this.terminal = Game.getTerminal();
        this.map = Map.getMap();
        this.mapRowLength = Map.getMapRowLength();
        this.mapPaddingX = Map.getMapPaddingX();
        this.mapPaddingY = Map.getMapPaddingY();
        this.mapRowHeight = Map.getMapRowHeight();
    }

    private Terminal terminal;
    private int x, y;
    private Character[][] map;
    private int mapPaddingX, mapPaddingY, mapRowLength, mapRowHeight;

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

    private void resetPlayer() {
        try {
            //Markera att vi har varit här
//            int index = (x - mapPaddingX) + (y - mapPaddingY) * mapRowLength;
//            String s = map[index];
//            map[index] = (s.equals(".") || s.equals("!") ? "!" : "^");
            terminal.setCursorPosition(x, y);
            terminal.putCharacter(' ');
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
